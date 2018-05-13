package com.shsxt.crm.dao;

import com.shsxt.base.BaseDao;
import com.shsxt.crm.dto.ModuleDto;
import com.shsxt.crm.po.Role;
import com.shsxt.crm.po.UserRole;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xlf on 2018/4/28.
 */
@Repository
public interface RoleDao extends BaseDao<Role> {
    public List<Role> queryAllRoles();

    public List<UserRole> queryRolesByUserId(Integer userId);

    public Integer delRolesByUserId(Integer userId);

    public Integer saveBatchUserRoles(List<UserRole> userRoles);

    public List<ModuleDto> queryAllModuleByRoleId(Integer roleId);

    public Integer deleteRolesByRoleId(Integer roleId);

    public Integer queryRolesByRoleId(Integer roleId);

}
