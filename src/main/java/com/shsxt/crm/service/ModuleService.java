package com.shsxt.crm.service;

import com.shsxt.base.BaseService;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.dao.ModuleDao;
import com.shsxt.crm.dao.PermissionDao;
import com.shsxt.crm.po.Module;
import com.shsxt.crm.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by xlf on 2018/5/3.
 */
@Service
public class ModuleService extends BaseService<Module> {

    @Resource
    private ModuleDao moduleDao;

    @Resource
    private PermissionDao permissionDao;

    public List<Map> queryModulesByGrade(Integer grade){
        return moduleDao.queryModulesByGrade(grade);
    }

    public void addOrUpdateModule(Module module){
        /***
         * 1. 做参数校验
         * 2. 通过id来进行更新和添加的区分
         * */
        checkModuleParams(module);

        //补全参数
        module.setUpdateDate(new Date());
        Integer moduleId = module.getId();
        if(moduleId!=null){
            // 更新
            AssertUtil.isTrue(moduleDao.queryById(moduleId)==null,"更新模块不存在");
            AssertUtil.isTrue(moduleDao.update(module)<1, CrmConstant.OPS_FAILED_MSG);
        }else{
            // 添加
            module.setCreateDate(new Date());
            module.setIsValid((byte) 1);
            AssertUtil.isTrue(moduleDao.save(module)<1, CrmConstant.OPS_FAILED_MSG);
        }
    }

    private void checkModuleParams(Module module) {
        Integer id = module.getId();// 模块ID
        String optValue = module.getOptValue();// 权限码
        String moduleName = module.getModuleName();//模块名称
        Integer grade = module.getGrade();// 菜单层级
        Integer parentId = module.getParentId();// 父模块ID

        AssertUtil.isTrue(StringUtils.isBlank(optValue), "权限码为空");
        AssertUtil.isTrue(StringUtils.isBlank(moduleName), "模块名称为空");
        // 只需在添加时判断
        if(id==null){
            AssertUtil.isTrue(moduleDao.queryModuleByOptValue(optValue)>0, "权限码已存在");
            AssertUtil.isTrue(moduleDao.queryModuleByName(moduleName)>0, "模块名称已存在");
        }

        AssertUtil.isTrue(null==grade, "菜单层级为空");
        /**
         * 0根级模块 1一级模块 2一级模块  (三种菜单)
         * 1. 如果0, 对父级模块id没有要求
         * 2. 如果是1, 父级必须为0
         * 3. 如果是2, 父级必须为1
         * */
        if(grade!=0){
            Module parentModule = moduleDao.queryById(parentId);// 查询出父模块
            Integer parentGrade = parentModule.getGrade();// 父模块grade
            AssertUtil.isTrue(grade!=0&&(grade-parentGrade)!=1,"模块层级不匹配");
        }
    }

    // 删除
    public void deleteModule(Integer moduleId){
        /****
         * 1. 先删除角色权限关联表的数据
         * 2. 再删除当前模块
         * */
        Module module = moduleDao.queryById(moduleId);
        String optValue = module.getOptValue();// 权限码值
        List<String> allModuleByOptValue = moduleDao.queryAllModuleByOptValue(optValue);
        System.out.println(allModuleByOptValue);
        //删除角色关联的模块
        permissionDao.deletePermissionsByAclValue(allModuleByOptValue);
        // 删除自己
        //AssertUtil.isTrue(moduleDao.delete(moduleId)<1, CrmConstant.OPS_FAILED_MSG);

        // 删除自己和子模块
        moduleDao.deleteBatchByOptValue(optValue);
    }

}
