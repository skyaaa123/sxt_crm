package com.shsxt.crm.model;

/**
 * Created by lp on 2018/1/8.
 */
public class UserModel {
    private String userName;
    private String trueName;
    private String userIdStr;// id 加密串

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getUserIdStr() {
        return userIdStr;
    }

    public void setUserIdStr(String userIdStr) {
        this.userIdStr = userIdStr;
    }
}
