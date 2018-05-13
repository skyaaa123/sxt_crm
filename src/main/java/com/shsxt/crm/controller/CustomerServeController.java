package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.po.CustomerServe;
import com.shsxt.crm.query.CustomerServeQuery;
import com.shsxt.crm.service.CustomerServeService;
import com.shsxt.crm.utils.LoginUserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by lp on 2018/5/5.
 */
@Controller
@RequestMapping("customerServe")
public class CustomerServeController extends BaseController {

    @Resource
    private CustomerServeService customerServeService;

    @RequestMapping("index/{state}")
    public String index(@PathVariable Integer state){
        if(state==1){
            return "customer_serve_create";
        }else if(state == 2){
            return "customer_serve_assign";
        }else if(state == 3){
            return "customer_serve_proce";
        }else if(state == 4){
            return "customer_serve_feed_back";
        }else if(state == 5){
            return "customer_serve_archive";
        }else{
            return  "error";
        }
    }
    //服务创建
    @RequestMapping("saveCustomerServe")
    @ResponseBody
    public ResultInfo saveCustomerServe(CustomerServe customerServe,HttpServletRequest request){
        Integer userId  = LoginUserUtil.releaseUserIdFromCookie(request);
        customerServeService.saveCustomerServe(customerServe,userId);
        return success("添加成功");
    }


    //服务分配时的分页查询,回显的是创建时的数据
    @RequestMapping("queryCustomerServesByParams")
    @ResponseBody
    public Map<String,Object> queryCustomerServesByParams(
            CustomerServeQuery customerServeQuery,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer rows, HttpServletRequest request
    ){
        String state = customerServeQuery.getState();
        if(state.equals("2")){
            Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
            customerServeQuery.setAssigner(userId+"");
        }
        customerServeQuery.setPageNum(page);
        customerServeQuery.setPageSize(rows);
        return customerServeService.queryForPage(customerServeQuery);
    }

    @RequestMapping("updateCustomerServe")
    @ResponseBody
    public ResultInfo updateCustomerServe(CustomerServe customerServe,
                                          HttpServletRequest request){
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        customerServeService.updateCustomerServe(customerServe, userId);
        return  success(CrmConstant.OPS_SUCCESS_MSG);
    }

}
