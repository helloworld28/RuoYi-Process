package com.ruoyi.process.order.service.impl;

import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.process.order.domain.BizOrder;
import com.ruoyi.process.order.domain.BizOrderVo;
import com.ruoyi.process.order.mapper.BizOrderMapper;
import com.ruoyi.process.order.service.IBizOrderService;
import com.ruoyi.process.todoitem.service.IBizTodoItemService;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.mapper.SysUserMapper;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * 订单Service业务层处理
 *
 * @author PowerJun Tech
 * @date 2021-05-20
 */
@Service
public class BizOrderServiceImpl implements IBizOrderService {
    private Logger logger = LoggerFactory.getLogger(BizOrderServiceImpl.class);

    private BizOrderMapper bizOrderMapper;

    private IdentityService identityService;

    private RuntimeService runtimeService;

    private IBizTodoItemService todoItemService;

    private TaskService taskService;

    private SysUserMapper userMapper;


    public BizOrderServiceImpl(BizOrderMapper bizOrderMapper, IdentityService identityService, RuntimeService runtimeService, IBizTodoItemService todoItemService, TaskService taskService, SysUserMapper userMapper) {
        this.bizOrderMapper = bizOrderMapper;
        this.identityService = identityService;
        this.runtimeService = runtimeService;
        this.todoItemService = todoItemService;
        this.taskService = taskService;
        this.userMapper = userMapper;
    }

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


        order.setStatus(10);
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
        List<Task> tasks = new ArrayList<Task>();

        // 根据当前人的ID查询
        List<Task> todoList = taskService.createTaskQuery().processDefinitionKey("orderProcess").taskAssignee(userId).list();

        // 根据当前人未签收的任务
        List<Task> unsignedTasks = taskService.createTaskQuery().processDefinitionKey("orderProcess").taskCandidateUser(userId).list();

        // 合并
        tasks.addAll(todoList);
        tasks.addAll(unsignedTasks);

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
            BeanUtils.copyProperties(order, bizOrderVo);

            bizOrderVo.setTaskId(task.getId());
            bizOrderVo.setTaskName(task.getName());

            results.add(bizOrderVo);
        }
        return results;
    }


}
