package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.po.Permission;
import com.shsxt.crm.service.PermissionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lp on 2018/5/2.
 */
@Controller
@RequestMapping("permission")
public class PermissionController extends BaseController {

    @Resource
    private PermissionService permissionService;

    @RequestMapping("delModulesByRoleId")
    @ResponseBody
    public ResultInfo delModulesByRoleId(Integer roleId){
        permissionService.delModulesByRoleId(roleId);
        return success(CrmConstant.OPS_SUCCESS_MSG);
    }


    @RequestMapping("savePermission")
    @ResponseBody
    public ResultInfo savePermission(Integer roleId, Integer[] moduleIds){
        permissionService.savePermissionBatch(roleId, moduleIds);
        return success(CrmConstant.OPS_SUCCESS_MSG);
    }



}
