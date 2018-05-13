package com.shsxt.crm.query;

import com.shsxt.base.BaseQuery;

/**
 * Created by lp on 2018/1/15.
 */
public class CustomerLossQuery extends BaseQuery {
    private String cusName;
    private String cusNo;
    private String time;

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getCusNo() {
        return cusNo;
    }

    public void setCusNo(String cusNo) {
        this.cusNo = cusNo;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
