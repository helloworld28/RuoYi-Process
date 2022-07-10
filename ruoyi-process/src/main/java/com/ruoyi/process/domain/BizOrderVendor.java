package com.ruoyi.process.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 订单供应商信息对象 biz_order_vendor
 *
 * @author Xianlu Tech
 * @date 2021-10-17
 */
public class BizOrderVendor extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** null */
    private Long id;

    /** 订单ID */
    @Excel(name = "订单ID")
    private String orderId;

    /** null */
    @Excel(name = "null")
    private Long vendorId;

    /** 色号 */
    @Excel(name = "色号")
    private String colorCode;

    /** 货号 */
    @Excel(name = "货号")
    private String itemNo;

    /** 供应商报数 */
    @Excel(name = "供应商报数")
    private Integer buyQuote;

    /** 出档口数量 */
    @Excel(name = "出档口数量")
    private Integer sellQuote;

    /** 客户ID */
    private String customerId;

    /** 单位 */
    @Excel(name = "单位")
    private Integer unit;

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
    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Long getVendorId() {
        return vendorId;
    }
    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getColorCode() {
        return colorCode;
    }
    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getItemNo() {
        return itemNo;
    }
    public void setBuyQuote(Integer buyQuote) {
        this.buyQuote = buyQuote;
    }

    public Integer getBuyQuote() {
        return buyQuote;
    }
    public void setSellQuote(Integer sellQuote) {
        this.sellQuote = sellQuote;
    }

    public Integer getSellQuote() {
        return sellQuote;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }
    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public Integer getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderId", getOrderId())
            .append("vendorId", getVendorId())
            .append("colorCode", getColorCode())
            .append("itemNo", getItemNo())
            .append("buyQuote", getBuyQuote())
            .append("sellQuote", getSellQuote())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("customerId", getCustomerId())
            .append("unit", getUnit())
            .toString();
    }
}
