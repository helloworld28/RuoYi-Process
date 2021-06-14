package com.ruoyi.process.order.service.impl;

import com.ruoyi.process.order.service.IBizOrderService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderStatusChangedListener implements TaskListener {
    private static final Logger logger = LoggerFactory.getLogger(OrderStatusChangedListener.class);

    @Autowired
    private IBizOrderService orderService;

    @Override
    public void notify(DelegateTask delegateTask) {

        String name = delegateTask.getName();

        logger.info("Task[{}] completed");
    }
}
