package com.ruoyi.process.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class BizVendorVo extends BizVendor {


    private String customerId;

    private Integer bought;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Integer getBought() {
        return bought;
    }

    public void setBought(Integer bought) {
        this.bought = bought;
    }
}
