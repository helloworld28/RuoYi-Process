package com.ruoyi.process.order.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 订单对象 biz_order
 *
 * @author PowerJun Tech
 * @date 2021-05-21
 */
public class BizOrder extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 订单ID */
    private Long id;

    /** 订单ID */
    private String orderId;

    /** 客户ID */
    @Excel(name = "客户ID")
    private String customerId;

    /** 客户名称 */
    @Excel(name = "客户名称")
    private String customerName;

    /** 货号 */
    @Excel(name = "货号")
    private String itemNo;

    /** 色号 */
    @Excel(name = "色号")
    private String colorCode;

    /** 数量 */
    @Excel(name = "数量")
    private Integer quatity;

    /** 客户要求 */
    @Excel(name = "客户要求")
    private String specialDemands;

    /** 是否已买过 */
    @Excel(name = "是否已买过")
    private Integer purchased;

    /** 提货方式 */
    @Excel(name = "提货方式")
    private String pickUpWay;

    /** 提货日期 */
    @Excel(name = "提货日期")
    private String pickUpDate;

    /** 是否已付款 */
    @Excel(name = "是否已付款")
    private Integer paid;

    /** 优先级 */
    @Excel(name = "优先级")
    private String priority;

    /** 提交用户ID */
    @Excel(name = "提交用户ID")
    private String applyUserId;

    /** 提交用户名称 */
    @Excel(name = "提交用户名称")
    private String applyUserName;

    /** 流程ID */
    @Excel(name = "流程ID")
    private String instanceId;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }
    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getItemNo() {
        return itemNo;
    }
    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getColorCode() {
        return colorCode;
    }
    public void setQuatity(Integer quatity) {
        this.quatity = quatity;
    }

    public Integer getQuatity() {
        return quatity;
    }
    public void setSpecialDemands(String specialDemands) {
        this.specialDemands = specialDemands;
    }

    public String getSpecialDemands() {
        return specialDemands;
    }
    public void setPurchased(Integer purchased) {
        this.purchased = purchased;
    }

    public Integer getPurchased() {
        return purchased;
    }
    public void setPickUpWay(String pickUpWay) {
        this.pickUpWay = pickUpWay;
    }

    public String getPickUpWay() {
        return pickUpWay;
    }
    public void setPickUpDate(String pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public String getPickUpDate() {
        return pickUpDate;
    }
    public void setPaid(Integer paid) {
        this.paid = paid;
    }

    public Integer getPaid() {
        return paid;
    }
    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getPriority() {
        return priority;
    }
    public void setApplyUserId(String applyUserId) {
        this.applyUserId = applyUserId;
    }

    public String getApplyUserId() {
        return applyUserId;
    }
    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }

    public String getApplyUserName() {
        return applyUserName;
    }
    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getInstanceId() {
        return instanceId;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("orderId", getOrderId())
                .append("customerId", getCustomerId())
                .append("customerName", getCustomerName())
                .append("itemNo", getItemNo())
                .append("colorCode", getColorCode())
                .append("quatity", getQuatity())
                .append("specialDemands", getSpecialDemands())
                .append("purchased", getPurchased())
                .append("pickUpWay", getPickUpWay())
                .append("pickUpDate", getPickUpDate())
                .append("paid", getPaid())
                .append("priority", getPriority())
                .append("applyUserId", getApplyUserId())
                .append("applyUserName", getApplyUserName())
                .append("instanceId", getInstanceId())
                .append("status", getStatus())
                .append("createTime", getCreateTime())
                .append("createBy", getCreateBy())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
