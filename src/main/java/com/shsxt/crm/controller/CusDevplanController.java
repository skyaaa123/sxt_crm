package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.po.CusDevplan;
import com.shsxt.crm.po.SaleChance;
import com.shsxt.crm.query.CusDevplanQuery;
import com.shsxt.crm.service.CusDevplanService;
import com.shsxt.crm.service.SaleChanceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by xlf on 2018/4/24.
 */
@Controller
@RequestMapping("cusDev")
public class CusDevplanController extends BaseController {

    @Resource
    private SaleChanceService saleChanceService;

    @Resource
    private CusDevplanService cusDevplanService;

    @RequestMapping("index/{sid}")
    public String index(@PathVariable Integer sid, Model model) {
        SaleChance saleChance = saleChanceService.queryById(sid);//
        model.addAttribute(saleChance);
        return "cus_dev_plan_detail";
    }

    @RequestMapping("queryCusDevplans")
    @ResponseBody
    public Map<String, Object> queryCusDevplans(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer rows,
            CusDevplanQuery cusDevplanQuery) {
        // 设置分页参数
        cusDevplanQuery.setPageSize(rows);
        cusDevplanQuery.setPageNum(page);
        return cusDevplanService.queryForPage(cusDevplanQuery);
    }


    @RequestMapping("addOrUpdateCusDevplan")
    @ResponseBody
    public ResultInfo addOrUpdateCusDevplan(CusDevplan cusDevplan) {
        cusDevplanService.addOrUpdateCusDevplan(cusDevplan);
        return success(CrmConstant.OPS_SUCCESS_MSG);
    }

    @RequestMapping("delCusDevplan")
    @ResponseBody
    public ResultInfo delCusDevplan(Integer[] ids) {
        cusDevplanService.deleteBatch(ids);
        return success(CrmConstant.OPS_SUCCESS_MSG);
    }


}
