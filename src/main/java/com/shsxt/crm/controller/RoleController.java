package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.dto.ModuleDto;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.po.Role;
import com.shsxt.crm.query.RoleQuery;
import com.shsxt.crm.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by xlf on 2018/4/28.
 */
@Controller
@RequestMapping("role")
public class RoleController extends BaseController{

    @Resource
    private RoleService roleService;

    @RequestMapping("index")
    public String index(){
        return "role";
    }


    @RequestMapping("queryRolesByParams")
    @ResponseBody
    public Map<String, Object> queryRolesByParams(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer rows,
            RoleQuery roleQuery){
        roleQuery.setPageSize(rows);
        roleQuery.setPageNum(page);
        return roleService.queryForPage(roleQuery);
    }


    @RequestMapping("addOrUpdateRole")
    @ResponseBody
    public ResultInfo addOrUpdateRole(Role role){
       roleService.addOrUpdateRole(role);
        return success(CrmConstant.OPS_SUCCESS_MSG);
    }


    @RequestMapping("queryAllRoles")
    @ResponseBody
    public List<Role> queryAllRoles(){
        return roleService.queryAllRoles();
    }

    @RequestMapping("queryAllModuleByRoleId")
    @ResponseBody
    public List<ModuleDto> queryAllModuleByRoleId(Integer roleId){
        return roleService.queryAllModuleByRoleId(roleId);
    }

    @RequestMapping("deleteRoles")
    @ResponseBody
    public ResultInfo deleteRoles(Integer[] ids){
        roleService.deleteRoles(ids);
        return  success(CrmConstant.OPS_SUCCESS_MSG);
    }
}
