package com.shsxt.crm.dao;

import com.shsxt.base.BaseDao;
import com.shsxt.crm.po.Permission;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lp on 2018/5/2.
 */
@Repository
public interface PermissionDao extends BaseDao<Permission>{
    public Integer delModulesByRoleId(Integer roleId);
    public Integer queryModulesByRoleId(Integer roleId);
    public List<String> queryPermissionsByUserId(Integer userId);
    public Integer deletePermissionsByAclValue(List<String> aclValues);
}
