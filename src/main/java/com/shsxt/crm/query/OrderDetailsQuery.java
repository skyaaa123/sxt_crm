package com.shsxt.crm.query;

import com.shsxt.base.BaseQuery;

/**
 * Created by lp on 2018/1/15.
 */
public class OrderDetailsQuery extends BaseQuery {
    private Integer orderId;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}
