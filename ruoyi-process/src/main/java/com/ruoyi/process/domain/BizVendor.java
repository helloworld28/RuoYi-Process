package com.ruoyi.process.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 供应商信息对象 biz_vendor
 *
 * @author Xianlu Tech
 * @date 2021-09-11
 */
public class BizVendor extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 货号 */
    @Excel(name = "货号")
    private String itemNo;

    /** 色号 */
    @Excel(name = "色号")
    private String colorCode;

    /** 供应商 */
    @Excel(name = "供应商")
    private String vendorName;

    /** 供应商货号 */
    @Excel(name = "供应商货号")
    private String vendorItemNo;

    /** 供应商色号 */
    @Excel(name = "供应商色号")
    private String vendorColorCode;

    /** 供应商区域 */
    @Excel(name = "供应商区域")
    private String vendorRegion;

    /** 供应商价格 */
    @Excel(name = "供应商价格")
    private Integer vendorPrice;

    /** 缸号 */
    @Excel(name = "缸号")
    private String vendorCylinderNo;

    /** 色差 */
    @Excel(name = "色差")
    private String colorDifference;

    /** 供应商空差 */
    @Excel(name = "供应商空差")
    private String vendorGap;

    /** 优先级，越大优先级越高 */
    @Excel(name = "优先级，越大优先级越高")
    private Integer priority;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorName() {
        return vendorName;
    }
    public void setVendorItemNo(String vendorItemNo) {
        this.vendorItemNo = vendorItemNo;
    }

    public String getVendorItemNo() {
        return vendorItemNo;
    }
    public void setVendorColorCode(String vendorColorCode) {
        this.vendorColorCode = vendorColorCode;
    }

    public String getVendorColorCode() {
        return vendorColorCode;
    }
    public void setVendorRegion(String vendorRegion) {
        this.vendorRegion = vendorRegion;
    }

    public String getVendorRegion() {
        return vendorRegion;
    }
    public void setVendorPrice(Integer vendorPrice) {
        this.vendorPrice = vendorPrice;
    }

    public Integer getVendorPrice() {
        return vendorPrice;
    }
    public void setVendorCylinderNo(String vendorCylinderNo) {
        this.vendorCylinderNo = vendorCylinderNo;
    }

    public String getVendorCylinderNo() {
        return vendorCylinderNo;
    }
    public void setColorDifference(String colorDifference) {
        this.colorDifference = colorDifference;
    }

    public String getColorDifference() {
        return colorDifference;
    }
    public void setVendorGap(String vendorGap) {
        this.vendorGap = vendorGap;
    }

    public String getVendorGap() {
        return vendorGap;
    }
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("itemNo", getItemNo())
            .append("colorCode", getColorCode())
            .append("vendorName", getVendorName())
            .append("vendorItemNo", getVendorItemNo())
            .append("vendorColorCode", getVendorColorCode())
            .append("vendorRegion", getVendorRegion())
            .append("vendorPrice", getVendorPrice())
            .append("vendorCylinderNo", getVendorCylinderNo())
            .append("colorDifference", getColorDifference())
            .append("vendorGap", getVendorGap())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("priority", getPriority())
            .toString();
    }
}
