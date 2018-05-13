package com.shsxt.crm.utils;

import com.shsxt.crm.exceptions.ParamsException;
import com.shsxt.crm.po.User;

/**
 * Created by xlf on 2018/4/24.
 */
public class TestUtil {
    /* 判断如果满足条件就抛异常 */
    public static void isTrue(Boolean flag, String errorMsg){
        if(flag){
            throw new ParamsException(errorMsg);
        }
    }
    public static void main(String args[]){
        User user = null;
        TestUtil.isTrue(null==user, "用户不能为空");
//        if(null != user){
//
//        }
        System.out.println(user);
    }
}
