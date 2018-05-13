package com.shsxt.crm.task;

import com.shsxt.crm.service.CustomerService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lp on 2018/5/4.
 */
@Component
public class CustomerLossTask {

    @Resource
    private CustomerService customerService;

    public void job1(){
        System.err.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        customerService.addLossCustomers();// 添加流失客户
    }
}
