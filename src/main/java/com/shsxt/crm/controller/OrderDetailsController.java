package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.query.OrderDetailsQuery;
import com.shsxt.crm.service.OrderDetailsService;
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
@RequestMapping("orderDetails")
public class OrderDetailsController extends BaseController {

    @Resource
    private OrderDetailsService orderDetailsService;



    @RequestMapping("queryOrderDetailsByOrderId")
    @ResponseBody
    public Map<String, Object> queryOrderDetailsByOrderId(
            OrderDetailsQuery orderDetailsQuery,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer rows) {
       orderDetailsQuery.setPageNum(page);
       orderDetailsQuery.setPageSize(rows);
       return orderDetailsService.queryForPage(orderDetailsQuery);
    }
}
