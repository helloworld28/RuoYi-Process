package com.ruoyi.process.order.service.impl;

import cn.hutool.extra.pinyin.PinyinUtil;
import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.process.domain.BizOrderSeq;
import com.ruoyi.process.order.OrderStatus;
import com.ruoyi.process.order.domain.BizOrder;
import com.ruoyi.process.order.domain.BizOrderVo;
import com.ruoyi.process.order.mapper.BizOrderMapper;
import com.ruoyi.process.order.service.IBizOrderService;
import com.ruoyi.process.service.IBizOrderSeqService;
import com.ruoyi.process.todoitem.service.IBizTodoItemService;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.mapper.SysUserMapper;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.list.TreeList;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 订单Service业务层处理
 *
 * @author PowerJun Tech
 * @date 2021-05-20
 */
@Service
public class BizOrderServiceImpl implements IBizOrderService {
    private Logger logger = LoggerFactory.getLogger(BizOrderServiceImpl.class);
    @Autowired
    private BizOrderMapper bizOrderMapper;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private IBizTodoItemService todoItemService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private IBizOrderSeqService orderSeqService;


    /**
     * 查询订单
     *
     * @param id 订单ID
     * @return 订单
     */
    @Override
    public BizOrder selectBizOrderById(Long id) {
        return bizOrderMapper.selectBizOrderById(id);
    }

    /**
     * 查询订单列表
     *
     * @param bizOrder 订单
     * @return 订单
     */
    @DataScope(deptAlias = "d", userAlias = "u")
    @Override
    public List<BizOrder> selectBizOrderList(BizOrder bizOrder) {
        return bizOrderMapper.selectBizOrderList(bizOrder);
    }

    /**
     * 新增订单
     *
     * @param bizOrder 订单
     * @return 结果
     */
    @Override
    public int insertBizOrder(BizOrder bizOrder) {

        String loginName = ShiroUtils.getLoginName();
        bizOrder.setCreateBy(loginName);
        bizOrder.setApplyUserId(loginName);

        String userName = ShiroUtils.getSysUser().getUserName();
        bizOrder.setApplyUserName(userName);

        BizOrderSeq orderSeq = orderSeqService.selectBizOrderSeqByUserId(loginName);
        String preOrder = PinyinUtil.getFirstLetter(userName, "");
        String orderId = preOrder + orderSeq.getOrderSeq();
        orderSeq.setOrderSeq(orderSeq.getOrderSeq() + 1);
        orderSeqService.updateBizOrderSeq(orderSeq);

        bizOrder.setOrderId(orderId);

        return bizOrderMapper.insertBizOrder(bizOrder);
    }


    /**
     * 修改订单
     *
     * @param bizOrder 订单
     * @return 结果
     */
    @Override
    public int updateBizOrder(BizOrder bizOrder) {
        return bizOrderMapper.updateBizOrder(bizOrder);
    }

    /**
     * 删除订单对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBizOrderByIds(String ids) {
        return bizOrderMapper.deleteBizOrderByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除订单信息
     *
     * @param id 订单ID
     * @return 结果
     */
    @Override
    public int deleteBizOrderById(Long id) {
        return bizOrderMapper.deleteBizOrderById(id);
    }

    @Override
    public ProcessInstance submitApply(BizOrder entity, String applyUserId) {
        entity.setStatus(10);
        String businessKey = entity.getId().toString(); // 实体类 ID，作为流程的业务 key

        // 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
        identityService.setAuthenticatedUserId(applyUserId);

        ProcessInstance processInstance = runtimeService // 启动流程时设置业务 key
                .startProcessInstanceByKey("orderProcess", businessKey);
        String processInstanceId = processInstance.getId();
        entity.setInstanceId(processInstanceId); // 建立双向关系
        bizOrderMapper.updateBizOrder(entity);

        // 下一节点处理人待办事项
        todoItemService.insertTodoItem(processInstanceId, "订单任务", "na", "order");

        return processInstance;
    }

    public void claimOrder(BizOrderVo bizOrderVo, String user) {
        logger.info("{} claim the task {}", user, bizOrderVo.getTaskId());
        BizOrder order = selectBizOrderById(bizOrderVo.getId());
        taskService.claim(bizOrderVo.getTaskId(), ShiroUtils.getLoginName());

        HashMap<String, Object> variables = new HashMap<>();
        variables.put("buyer", ShiroUtils.getLoginName());
        taskService.complete(bizOrderVo.getTaskId(), variables);


        order.setStatus(OrderStatus.WAITING_REPORT.getValue());
        bizOrderMapper.updateBizOrder(order);

        todoItemService.updateToDoItemList(bizOrderVo.getTaskId(), ShiroUtils.getLoginName(), ShiroUtils.getSysUser().getUserName());

        // 下一节点处理人待办事项
        todoItemService.insertTodoItem(bizOrderVo.getInstanceId(), "订单任务", "NA", "order");
    }

    /**
     * 查询待办任务
     */
    @Transactional(readOnly = true)
    public List<BizOrderVo> findTodoTasks(BizOrderVo leave, String userId) {
        List<BizOrderVo> results = new ArrayList<>();


        // 根据当前人未签收的任务
        List<Task> unsignedTasks = taskService
                .createTaskQuery()
                .processDefinitionKey("orderProcess")
                .orderByTaskCreateTime()
                .desc()
                .taskCandidateUser(userId)
                .list();
        // 根据当前人的ID查询
        List<Task> todoList = taskService.createTaskQuery()
                .processDefinitionKey("orderProcess")
                .orderByTaskCreateTime()
                .desc()
                .taskAssignee(userId)
                .list();


        // 合并
        List<Task> tasks = new ArrayList<Task>(unsignedTasks.size() + todoList.size());
        tasks.addAll(unsignedTasks);
        tasks.addAll(todoList);

        // 根据流程的业务ID查询实体并关联
        for (Task task : tasks) {
            String processInstanceId = task.getProcessInstanceId();

            // 条件过滤 1
            if (StringUtils.isNotBlank(leave.getInstanceId()) && !leave.getInstanceId().equals(processInstanceId)) {
                continue;
            }

            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            String businessKey = processInstance.getBusinessKey();
            BizOrder order = bizOrderMapper.selectBizOrderById(new Long(businessKey));

            BizOrderVo bizOrderVo = new BizOrderVo();
            if (order != null) {
                BeanUtils.copyProperties(order, bizOrderVo);
            }

            bizOrderVo.setTaskId(task.getId());
            bizOrderVo.setTaskName(task.getName());

            results.add(bizOrderVo);
        }

        results = sortByUpdateTime(results);

        return results;
    }

    @NotNull
    private List<BizOrderVo> sortByUpdateTime(List<BizOrderVo> results) {
        results =  results.stream().sorted(new Comparator<BizOrderVo>() {
            @Override
            public int compare(BizOrderVo o1, BizOrderVo o2) {
                return o2.getUpdateTime().compareTo(o1.getUpdateTime());
            }
        }).collect(Collectors.toList());
        return results;
    }


}
