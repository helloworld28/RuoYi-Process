package com.ruoyi.process.order.domain;

import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.process.domain.BizOrderVendor;

import java.util.Date;
import java.util.List;

public class BizOrderVo extends BizOrder {
    /**
     * 申请人姓名
     */
    private String applyUserName;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 办理时间
     */
    private Date doneTime;

    /**
     * 创建人
     */
    private String createUserName;

    /**
     * 订单供应商信息
     */
    private List<BizOrderVendor> orderVendors;

    public BizOrderVo() {
    }

    public BizOrderVo(BizOrder bizOrder) {
        BeanUtils.copyProperties(bizOrder, this);
    }


    public String getApplyUserName() {
        return applyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Date getDoneTime() {
        return doneTime;
    }

    public void setDoneTime(Date doneTime) {
        this.doneTime = doneTime;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }


    public List<BizOrderVendor> getOrderVendors() {
        return orderVendors;
    }

    public void setOrderVendors(List<BizOrderVendor> orderVendors) {
        this.orderVendors = orderVendors;
    }
}
