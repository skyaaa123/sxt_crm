package com.shsxt.crm.service;

import com.shsxt.base.BaseService;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.dao.RoleDao;
import com.shsxt.crm.dao.UserDao;
import com.shsxt.crm.dto.ModuleDto;
import com.shsxt.crm.po.Role;
import com.shsxt.crm.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by xlf on 2018/4/28.
 */
@Service
public class RoleService extends BaseService<Role>{

    @Resource
    private RoleDao roleDao;


    public void deleteRoles(Integer[] ids){
        AssertUtil.isTrue(null==ids || ids.length==0, CrmConstant.OPS_FAILED_MSG);
        /***
         * 删除角色: 同时要删除用户已经分配的该角色关联信息
         * */
        // 删除
        for (Integer roleId:ids){
            Integer total = roleDao.queryRolesByRoleId(roleId);
            if(total>0){
                AssertUtil.isTrue(roleDao.deleteRolesByRoleId(roleId)<total, CrmConstant.OPS_FAILED_MSG);
            }
        }
        //删除角色
        AssertUtil.isTrue(roleDao.deleteBatch(ids)<ids.length, CrmConstant.OPS_FAILED_MSG);
    }


    public List<ModuleDto> queryAllModuleByRoleId(Integer roleId){
        AssertUtil.isTrue(null==roleId, CrmConstant.OPS_FAILED_MSG);
        return roleDao.queryAllModuleByRoleId(roleId);
    }

    public void addOrUpdateRole(Role role){
        /* 根据id区分添加或者跟新 */
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()), "角色名称为空");
        role.setUpdateDate(new Date());// 更新时间
        if(role.getId()==null){
            // 添加
            role.setCreateDate(new Date());
            role.setIsValid(1);
            AssertUtil.isTrue(roleDao.save(role)<1, CrmConstant.OPS_FAILED_MSG);
        }else{
            // 更新
            AssertUtil.isTrue(roleDao.update(role)<1, CrmConstant.OPS_FAILED_MSG);
        }
    }


    public List<Role> queryAllRoles(){
        return roleDao.queryAllRoles();
    }
}
