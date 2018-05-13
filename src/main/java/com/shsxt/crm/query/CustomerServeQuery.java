package com.shsxt.crm.query;

import com.shsxt.base.BaseQuery;

/**
 * Created by lp on 2018/1/16.
 */
public class CustomerServeQuery extends BaseQuery {
    private String state;


    private String cusName;
    private String myd;
    private String time;

    private String assigner;

    public String getAssigner() {
        return assigner;
    }

    public void setAssigner(String assigner) {
        this.assigner = assigner;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getMyd() {
        return myd;
    }

    public void setMyd(String myd) {
        this.myd = myd;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
