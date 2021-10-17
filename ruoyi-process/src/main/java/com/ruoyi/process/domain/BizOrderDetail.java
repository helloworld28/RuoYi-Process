package com.ruoyi.process.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 订单明细对象 biz_order_detail
 *
 * @author Xianlu Tech
 * @date 2021-06-19
 */
public class BizOrderDetail extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** null */
    private Long id;

    /** 订单号 */
    @Excel(name = "订单号")
    private String orderId;

    /** 货号 */
    @Excel(name = "货号")
    private String itemNo;

    /** 色号 */
    @Excel(name = "色号")
    private String colorCode;

    /** 长度 */
    @Excel(name = "长度")
    private String length;

    /** 是否买过 */
    @Excel(name = "是否买过")
    private Integer purchased;

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
    public void setLength(String length) {
        this.length = length;
    }

    public String getLength() {
        return length;
    }
    public void setPurchased(Integer purchased) {
        this.purchased = purchased;
    }

    public Integer getPurchased() {
        return purchased;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderId", getOrderId())
            .append("itemNo", getItemNo())
            .append("colorCode", getColorCode())
            .append("length", getLength())
            .append("purchased", getPurchased())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
