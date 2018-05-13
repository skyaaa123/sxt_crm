package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.service.CustomerServeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by lp on 2018/5/5.
 */
@Controller
public class ReportController extends BaseController {

    @Resource
    private CustomerServeService customerServeService;

    @RequestMapping("report/{id}")
    public String index(@PathVariable Integer id){
        if(id==2){
            return "customerService_fx";
        }else{
            return "error";
        }
    }

    @RequestMapping("report/queryServiceData")
    @ResponseBody
    public List<Map> queryServiceData(){
        return customerServeService.queryServiceData();
    }
}
