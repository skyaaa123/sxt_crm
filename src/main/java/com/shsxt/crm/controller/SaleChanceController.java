package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.annotations.RequestPermission;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.po.SaleChance;
import com.shsxt.crm.query.SaleChanceQuery;
import com.shsxt.crm.service.SaleChanceService;
import com.shsxt.crm.utils.LoginUserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by xlf on 2018/4/24.
 */
@Controller
@RequestMapping("saleChance")
public class SaleChanceController extends BaseController {

    @Resource
    private SaleChanceService saleChanceService;

    @RequestMapping("index/{state}")
    public String index(@PathVariable Integer state) {
        if(state==0){
            return "sale_chance";
        }else if(state==1){
            return "cus_dev_plan";
        }else{
            return "error";
        }
    }

    @RequestPermission(aclValue="101001")
    @RequestMapping("querySaleChancesByParams")
    @ResponseBody
    public Map<String, Object> querySaleChancesByParams(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer rows,
            SaleChanceQuery saleChanceQuery) {
        // 获取分页参数,设置给查询用
        saleChanceQuery.setPageNum(page);
        saleChanceQuery.setPageSize(rows);
        return saleChanceService.queryForPage(saleChanceQuery);
    }

    @RequestMapping("addOrUpdateSaleChance")
    @ResponseBody
    public ResultInfo addOrUpdateSaleChance(SaleChance saleChance, HttpServletRequest request) {
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);// 获取用户id
        saleChanceService.addOrUpdateSaleChance(saleChance, userId);
        return success(CrmConstant.OPS_SUCCESS_MSG);
    }

    @RequestMapping("queryCustomerManagers")
    @ResponseBody
    public List<Map> queryCustomerManagers(){
        return saleChanceService.queryCustomerManagers();
    }

    @RequestMapping("deleteSaleChance")
    @ResponseBody
    public ResultInfo deleteSaleChance(Integer[] ids){
        saleChanceService.deleteSaleChance(ids);
        return success(CrmConstant.OPS_SUCCESS_MSG);
    }


}
