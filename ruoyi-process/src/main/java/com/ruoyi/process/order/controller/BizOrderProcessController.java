package com.ruoyi.process.order.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.process.order.service.BizOrderProcessService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.ruoyi.common.core.domain.AjaxResult.success;

@Controller
@RequestMapping("process/orderProcess")
public class BizOrderProcessController {
    private static final Logger logger = LoggerFactory.getLogger(BizOrderProcessController.class);

    private BizOrderProcessService orderProcessService;


    public BizOrderProcessController(BizOrderProcessService orderProcessService) {
        this.orderProcessService = orderProcessService;
    }

    @RequiresPermissions("process:orderProcess:cancelReport")
    @PostMapping("/adjustOrder")
    @ResponseBody
    public AjaxResult adjustOrder( String instanceId) {
        logger.info("received adjust order request instanceId[{}]", instanceId);

        orderProcessService.sendAdjustOrderMsg(instanceId);

        return success();
    }


}
