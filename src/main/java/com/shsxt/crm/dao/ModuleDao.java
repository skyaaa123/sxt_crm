package com.shsxt.crm.dao;

import com.shsxt.base.BaseDao;
import com.shsxt.crm.po.Module;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by lp on 2018/5/3.
 */
@Repository
public interface ModuleDao extends BaseDao<Module> {

    public Integer queryModuleByName(String moduleName);

    public Integer queryModuleByOptValue(String optValue);

    public List<Map> queryModulesByGrade(Integer grade);

    public List<String> queryAllModuleByOptValue(String optValue);

    public Integer deleteBatchByOptValue(String optValue);


}
