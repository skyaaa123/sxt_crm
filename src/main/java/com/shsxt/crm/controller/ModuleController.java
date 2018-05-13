package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.po.Module;
import com.shsxt.crm.query.ModuleQuery;
import com.shsxt.crm.service.ModuleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by lp on 2018/5/3.
 */
@Controller
@RequestMapping("module")
public class ModuleController extends BaseController{

    @Resource
    private ModuleService moduleService;

    @RequestMapping("index")
    public String index(){
        return "module";
    }

    @RequestMapping("queryModulesByParams")
    @ResponseBody
    public Map<String, Object> queryModulesByParams(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer rows,
            ModuleQuery moduleQuery){
        moduleQuery.setPageSize(rows);
        moduleQuery.setPageNum(page);
        return moduleService.queryForPage(moduleQuery);

    }

    @RequestMapping("addOrUpdateModule")
    @ResponseBody
    public ResultInfo addOrUpdateModule(Module module){
        moduleService.addOrUpdateModule(module);
        return  success(CrmConstant.OPS_SUCCESS_MSG);
    }

    @RequestMapping("queryModulesByGrade")
    @ResponseBody
    public List<Map> queryModulesByGrade(Integer grade){
        return moduleService.queryModulesByGrade(grade);
    }

    @RequestMapping("deleteModule")
    @ResponseBody
    public ResultInfo deleteModule(Integer moduleId){
        moduleService.deleteModule(moduleId);
        return success(CrmConstant.OPS_SUCCESS_MSG);
    }
}
