package com.ruoyi.process.order.service;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.process.order.OrderStatus;
import com.ruoyi.process.order.domain.BizOrder;
import com.ruoyi.process.order.domain.BizOrderVo;
import com.ruoyi.process.todoitem.service.IBizTodoItemService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.shiro.crypto.hash.Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        order.setStatus(OrderStatus.WAITING_CONFIRM.getValue());
        bizOrderService.updateBizOrder(order);

        variables.put("applyUserId", order.getApplyUserId());
        logger.info("{} 签收并完成任务 {}", ShiroUtils.getLoginName(), bizOrderVo.getTaskId());
        completeTask(bizOrderVo, variables);

        // 下一节点处理人待办事项
        logger.info("{} 通知下一节点处理人待办事项 {}", ShiroUtils.getLoginName(), bizOrderVo.getTaskId());
        notifyNextActor(bizOrderVo, comment);
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
        order.setStatus(OrderStatus.WAITING_BUY_INSTOCK.getValue());
        bizOrderService.updateBizOrder(order);

        HistoricTaskInstance previousTask = findPreviousTask(order.getInstanceId());
        String buyer = previousTask.getAssignee();
        variables.put("buyer", previousTask.getAssignee());
        variables.put("paid", order.getPaid() == 1);


        logger.info("{} 签收并完成任务 {}", ShiroUtils.getLoginName(), bizOrderVo.getTaskId());
        completeTask(bizOrderVo, variables);

        // 下一节点处理人待办事项
        logger.info("{} 通知下一节点处理人待办事项 {}", ShiroUtils.getLoginName(), bizOrderVo.getTaskId());
        notifyNextActor(bizOrderVo, comment);
    }

    private void notifyNextActor(BizOrderVo bizOrderVo, String comment) {
        todoItemService.updateToDoItemList(bizOrderVo.getTaskId(), ShiroUtils.getLoginName(), ShiroUtils.getSysUser().getUserName());
        todoItemService.insertTodoItem(bizOrderVo.getInstanceId(), "订单业务", comment, "order");
    }

    private void completeTask(BizOrderVo bizOrderVo, Map<String, Object> variables) {
        taskService.claim(bizOrderVo.getTaskId(), ShiroUtils.getLoginName());
        taskService.complete(bizOrderVo.getTaskId(), variables);
    }

    public void confirmPaid(BizOrderVo bizOrderVo, Map<String, Object> variables) {
        BizOrderVo order = getBizOrderByTaskId(bizOrderVo.getTaskId());
        logger.info("更新订单[{}]为已付款", bizOrderVo.getOrderId());
        order.setPaid(1);
        bizOrderService.updateBizOrder(order);

        logger.info("完成确认收款任务[{}]", bizOrderVo.getTaskId());
        completeTask(bizOrderVo, variables);

        logger.info("通知下个节点任务");
        notifyNextActor(bizOrderVo, null);
    }

    public void commonConfirm(BizOrderVo bizOrderVo, Map<String, Object> variables) {
        BizOrderVo order = getBizOrderByTaskId(bizOrderVo.getTaskId());

        //1. 保存批注
        String comment = (String) variables.get("comment");
        if (StringUtils.isNotEmpty(comment)) {
            logger.info("{} 保存批注 {} {}", ShiroUtils.getLoginName(), bizOrderVo.getTaskId(), comment);
            identityService.setAuthenticatedUserId(ShiroUtils.getLoginName());
            Comment comment1 = taskService.addComment(bizOrderVo.getTaskId(), bizOrderVo.getInstanceId(), comment);
        }

        //2. 完成任务
        logger.info("完成任务[{}]", bizOrderVo.getTaskId());
        completeTask(bizOrderVo, variables);

        //3. 通知下个节点任务
        logger.info("通知下个节点任务");
        notifyNextActor(bizOrderVo, null);
    }

    public void sendAdjustOrderMsg(String instanceId) {
        logger.info("send adjustOrderMsg instanceId[{}]", instanceId);

        Execution execution = runtimeService.createExecutionQuery()
                .messageEventSubscriptionName("adjustOrderMsg")
                .processInstanceId(instanceId)
                .singleResult();
        HashMap<String, Object> msgMap = new HashMap<>();
        msgMap.put("test", "test");


        runtimeService.messageEventReceived("adjustOrderMsg", execution.getId(), msgMap);


        logger.info("update order status to adjusted report");
        updateOrderStatus(instanceId);
    }

    private void updateOrderStatus(String instanceId) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(instanceId).singleResult();
        BizOrder order = bizOrderService.selectBizOrderById(Long.parseLong(processInstance.getBusinessKey()));
        BizOrderVo bizOrderVo = new BizOrderVo(order);
        bizOrderVo.setStatus(OrderStatus.WAITING_ADJUST_REPORT.getValue());
        bizOrderService.updateBizOrder(order);
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

    public BizOrderVo getBizOrderByTaskId(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        BizOrder order = bizOrderService.selectBizOrderById(new Long(processInstance.getBusinessKey()));
        BizOrderVo bizOrderVo = new BizOrderVo(order);
        bizOrderVo.setTaskId(taskId);
        return bizOrderVo;

    }

    public Task getTaskByTaskId(String taskId) {
        return taskService.createTaskQuery().taskId(taskId).singleResult();
    }

}
