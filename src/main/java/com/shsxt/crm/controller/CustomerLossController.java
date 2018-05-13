package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.query.CustomerLossQuery;
import com.shsxt.crm.service.CustomerLossService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by lp on 2018/1/15.
 */
@Controller
@RequestMapping("customerLoss")
public class CustomerLossController extends BaseController {
    @Resource
    private CustomerLossService customerLossService;

    @RequestMapping("index")
    public  String index(){
        return "customer_loss";
    }

    @RequestMapping("queryCustomerLossByParams")
    @ResponseBody
    public Map<String,Object> queryCustomerLossByParams(
            CustomerLossQuery customerLossQuery,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer rows){
        customerLossQuery.setPageNum(page);
        customerLossQuery.setPageSize(rows);
        return customerLossService.queryForPage(customerLossQuery);
    }
}
