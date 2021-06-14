package com.ruoyi.process.order.service.impl;

import com.ruoyi.process.order.OrderStatus;
import com.ruoyi.process.order.domain.BizOrder;
import com.ruoyi.process.order.service.IBizOrderService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 订单流程成功处理
 * 1. 更新订单状态为已完成
 */
@Component
@Transactional
public class OrderProcessSuccessListener implements TaskListener {
    private static final Logger logger = LoggerFactory.getLogger(OrderProcessSuccessListener.class);

    @Autowired
    private IBizOrderService bizOrderService;

    @Override
    public void notify(DelegateTask delegateTask) {
        String orderId = delegateTask.getExecution().getProcessInstanceBusinessKey();

        BizOrder bizOrder = bizOrderService.selectBizOrderById(Long.valueOf(orderId));
        bizOrder.setStatus(OrderStatus.DONE.getValue());

        bizOrderService.updateBizOrder(bizOrder);
        logger.info("订单[{}]已成功完成", orderId);
    }
}
