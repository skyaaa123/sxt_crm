package com.shsxt.crm.query;

import com.shsxt.base.BaseQuery;

import java.util.Date;

/**
 * Created by lp on 2018/5/2.
 */
public class RoleQuery extends BaseQuery {
    private String roleName;
    private String createDate;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
