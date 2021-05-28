package com.ruoyi.process.order.service;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.process.order.domain.BizOrder;
import com.ruoyi.process.order.domain.BizOrderVo;
import com.ruoyi.process.todoitem.service.IBizTodoItemService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

@Service
public class BizOrderProcessService {

    private static final Logger logger = LoggerFactory.getLogger(BizOrderProcessService.class);

    private TaskService taskService;

    private RuntimeService runtimeService;

    private IdentityService identityService;

    private IBizOrderService bizOrderService;

    private IBizTodoItemService todoItemService;

    private HistoryService historyService;

    public BizOrderProcessService(TaskService taskService, RuntimeService runtimeService, IdentityService identityService, IBizOrderService bizOrderService, IBizTodoItemService todoItemService, HistoryService historyService) {
        this.taskService = taskService;
        this.runtimeService = runtimeService;
        this.identityService = identityService;
        this.bizOrderService = bizOrderService;
        this.todoItemService = todoItemService;
        this.historyService = historyService;
    }

    @Transactional
    public void completeReports(BizOrderVo bizOrderVo, Map<String, Object> variables) {
        String comment = (String) variables.get("comment");
        if (StringUtils.isNotEmpty(comment)) {
            logger.info("{} 保存批注 {} {}", ShiroUtils.getLoginName(), bizOrderVo.getTaskId(), comment);
            identityService.setAuthenticatedUserId(ShiroUtils.getLoginName());
            Comment comment1 = taskService.addComment(bizOrderVo.getTaskId(), bizOrderVo.getInstanceId(), comment);
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

    public void confirmProduct(BizOrderVo bizOrderVo, Map<String, Object> variables) {
        String comment = (String) variables.get("comment");
        if (StringUtils.isNotEmpty(comment)) {
            logger.info("{} 保存批注 {} {}", ShiroUtils.getLoginName(), bizOrderVo.getTaskId(), comment);
            identityService.setAuthenticatedUserId(ShiroUtils.getLoginName());
            Comment comment1 = taskService.addComment(bizOrderVo.getTaskId(), bizOrderVo.getInstanceId(), comment);
        }

        //获取上个任务的执行人，即上次的采购员，把任务返回给采购员
        BizOrderVo order = getBizOrderByTaskId(bizOrderVo.getTaskId());
        HistoricTaskInstance previousTask = findPreviousTask(order.getInstanceId());
        String buyer = previousTask.getAssignee();
        variables.put("buyer", previousTask.getAssignee());


        logger.info("{} 签收并完成任务 {}", ShiroUtils.getLoginName(), bizOrderVo.getTaskId());
        taskService.claim(bizOrderVo.getTaskId(), ShiroUtils.getLoginName());
        taskService.complete(bizOrderVo.getTaskId(), variables);

        // 下一节点处理人待办事项
        logger.info("{} 通知下一节点处理人待办事项 {}", ShiroUtils.getLoginName(), bizOrderVo.getTaskId());
        todoItemService.updateToDoItemList(bizOrderVo.getTaskId(), ShiroUtils.getLoginName(), ShiroUtils.getSysUser().getUserName());
        todoItemService.insertTodoItem(bizOrderVo.getInstanceId(), "订单业务", comment, "leave");
    }

    public Optional<Comment> getPreviousTaskComment(String processInstanceId) {
        HistoricTaskInstance previousTask = findPreviousTask(processInstanceId);
        List<Comment> taskComments = taskService.getTaskComments(previousTask.getId());
        return taskComments.isEmpty() ? Optional.empty() : Optional.of(taskComments.get(taskComments.size() - 1));
    }

    // Order tasks by end date and get the latest
    public HistoricTaskInstance findPreviousTask(String processInstanceId) {
        return historyService.createHistoricTaskInstanceQuery().
                processInstanceId(processInstanceId).orderByHistoricTaskInstanceEndTime().desc().list().get(0);
    }

    public BizOrderVo getBizOrderByTaskId(@PathVariable String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        BizOrder order = bizOrderService.selectBizOrderById(new Long(processInstance.getBusinessKey()));
        BizOrderVo bizOrderVo = new BizOrderVo(order);
        bizOrderVo.setTaskId(taskId);
        return bizOrderVo;

    }

}
