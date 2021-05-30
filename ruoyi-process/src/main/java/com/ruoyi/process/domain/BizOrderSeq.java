package com.ruoyi.process.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 订单序号对象 biz_order_seq
 *
 * @author Powerjun Tech
 * @date 2021-05-27
 */
public class BizOrderSeq extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** null */
    private Long id;

    /** 用户 */
    @Excel(name = "用户")
    private String userId;

    /** 订单序号 */
    @Excel(name = "订单序号")
    private Integer orderSeq;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
    public void setOrderSeq(Integer orderSeq) {
        this.orderSeq = orderSeq;
    }

    public Integer getOrderSeq() {
        return orderSeq;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("orderSeq", getOrderSeq())
            .toString();
    }
}
