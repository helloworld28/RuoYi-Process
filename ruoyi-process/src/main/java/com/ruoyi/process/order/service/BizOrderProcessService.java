package com.ruoyi.process.order.service;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.process.order.domain.BizOrder;
import com.ruoyi.process.order.domain.BizOrderVo;
import com.ruoyi.process.todoitem.service.IBizTodoItemService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;

@Service
public class BizOrderProcessService {

    private static final Logger logger = LoggerFactory.getLogger(BizOrderProcessService.class);

    private TaskService taskService;

    private RuntimeService runtimeService;

    private IdentityService identityService;

    private IBizOrderService bizOrderService;


    private IBizTodoItemService todoItemService;


    public BizOrderProcessService(TaskService taskService, RuntimeService runtimeService, IdentityService identityService, IBizOrderService bizOrderService, IBizTodoItemService todoItemService) {
        this.taskService = taskService;
        this.runtimeService = runtimeService;
        this.identityService = identityService;
        this.bizOrderService = bizOrderService;
        this.todoItemService = todoItemService;
    }

    @Transactional
    public void completeReports(BizOrderVo bizOrderVo, Map<String, Object> variables) {
        String comment = (String) variables.get("comment");
        if (StringUtils.isNotEmpty(comment)) {
            logger.info("{} 保存批注 {} {}", ShiroUtils.getLoginName(), bizOrderVo.getTaskId(), comment);
            identityService.setAuthenticatedUserId(ShiroUtils.getLoginName());
            taskService.addComment(bizOrderVo.getTaskId(), bizOrderVo.getInstanceId(), comment);
        }

        BizOrder order = bizOrderService.selectBizOrderById(bizOrderVo.getId());

        variables.put("applyUserId", order.getApplyUserId());
        logger.info("{} 签收并完成任务 {}", ShiroUtils.getLoginName(), bizOrderVo.getTaskId());
        taskService.claim(bizOrderVo.getTaskId(), ShiroUtils.getLoginName());
        taskService.complete(bizOrderVo.getTaskId(), variables);


        // 下一节点处理人待办事项
        logger.info("{} 通知下一节点处理人待办事项 {}", ShiroUtils.getLoginName(), bizOrderVo.getTaskId());
        todoItemService.updateToDoItemList(bizOrderVo.getTaskId(), ShiroUtils.getLoginName(), ShiroUtils.getSysUser().getUserName());
        todoItemService.insertTodoItem(bizOrderVo.getInstanceId(), "订单业务", comment, "leave");
    }

}
