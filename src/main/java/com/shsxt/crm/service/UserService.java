package com.shsxt.crm.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shsxt.base.BaseService;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.dao.PermissionDao;
import com.shsxt.crm.dao.RoleDao;
import com.shsxt.crm.dao.UserDao;
import com.shsxt.crm.dto.UserDto;
import com.shsxt.crm.model.UserModel;
import com.shsxt.crm.po.User;
import com.shsxt.crm.po.UserRole;
import com.shsxt.crm.query.UserQuery;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.utils.Md5Util;
import com.shsxt.crm.utils.UserIDBase64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by xlf on 2018/4/24.
 */
@Service
public class UserService extends BaseService<User> {

    @Resource
    private UserDao userDao;

    @Resource
    private RoleDao roleDao;

    @Resource
    private PermissionDao permissionDao;


    public void deleteUserBatch(Integer[] ids){
        /***
         * 删除用户和角色
         * */
        AssertUtil.isTrue(null==ids || ids.length==0,"未选择需要删除的用户");
        // 删除用户角色
        for (Integer userId:ids){
            List<UserRole> userRoles = roleDao.queryRolesByUserId(userId);
            Integer total = userRoles.size();//该用户有多少个角色
            if(total>0){
                AssertUtil.isTrue(roleDao.delRolesByUserId(userId)<total, CrmConstant.OPS_FAILED_MSG);
            }
        }
        //删除用户
        AssertUtil.isTrue(userDao.deleteBatch(ids)<ids.length, CrmConstant.OPS_FAILED_MSG);
    }


    public void addOrUpdateUser(UserDto user){
        /*UserDto中新添了roleName和roleIds两个参数，并且继承了user对象。
         可以直接用UserDto user相当于提升下作用域*/
        //后台参数非空验证
        checkUserParams(user.getUserName(), user.getEmail(), user.getPhone());
        //设置参数，补充的参数是以数据库为参考点，前台每天传的字段，自己手动补充
        user.setUpdateDate(new Date());
        User oldUser = userDao.queryUserByName(user.getUserName());
        if(user.getId()==null){
            // 添加,添加时要记得加用户名唯一验证
            AssertUtil.isTrue(null!=oldUser, "用户名已被注册");
            user.setIsValid(1);// 有效
            user.setCreateDate(new Date());//创建时间
            /* 规定用户的初始密码为: 123456 */
            user.setUserPwd(Md5Util.encode("123456"));// 密码加密
            AssertUtil.isTrue(userDao.save(user)<1, "用户添加失败");
        }else{
            // 修改，修改也要验证非重（修改的时候不允许把a的id传的是b的名字，必须保证修改的名字数据库能查到且唯一）
            AssertUtil.isTrue(user.getId()!=oldUser.getId(), "修改用户不存在或已删除");
            AssertUtil.isTrue(userDao.update(user)<1, "用户修改失败");
        }
        /* 当更新或者添加用户成功后,需要重新查找出真实用户的ID */
        User realUser = userDao.queryUserByName(user.getUserName());
        // 分配用户角色
        relationRoles(realUser.getId(),user.getRoleIds());
    }

    private void relationRoles(Integer userId, List<Integer> roleIds) {
        /****
         * 分配方式:
         * 如果没有角色,直接添加,
         * 有角色全部删除,再添加
         * */
        if(roleIds.size()>0){
            List<UserRole> userRoles = roleDao.queryRolesByUserId(userId);
            Integer total = userRoles.size();// 总数
            if (total>0){
                // 先删除,再添加
                AssertUtil.isTrue(roleDao.delRolesByUserId(userId)<total, CrmConstant.OPS_FAILED_MSG);
            }

//            int i = 1/0;

            // 批量添加
            List<UserRole> userRoleList = new ArrayList<>();
            for (Integer roleId : roleIds){
                userRoleList.add(new UserRole(userId, roleId,new Date(), new Date()));
            }

            AssertUtil.isTrue(roleDao.saveBatchUserRoles(userRoleList)<roleIds.size(),CrmConstant.OPS_FAILED_MSG);
        }
    }

    private void checkUserParams(String userName, String email, String phone) {
        AssertUtil.isTrue(StringUtils.isBlank(userName), "用户名为空");
        AssertUtil.isTrue(StringUtils.isBlank(email), "邮箱为空");
        AssertUtil.isTrue(StringUtils.isBlank(phone), "电话为空");
    }

    /* 修改密码 */
    public void updateUserPwd(Integer userId, String oldPassword,
                                String newPassword, String confirmPassword){
        // 检查参数
        checkUpdateOrAddUserParams(userId, oldPassword, newPassword, confirmPassword);
        // 设置更新参数
        User user = new User();
        user.setId(userId);
        user.setUserPwd(Md5Util.encode(newPassword));
        user.setUpdateDate(new Date());// 更新时间是当前时间
        AssertUtil.isTrue(userDao.updateUserPwdById(user)<1,"用户密码更新失败");
    }

    private void checkUpdateOrAddUserParams(Integer userId, String oldPassword,
                                            String newPassword, String confirmPassword) {
        User user = userDao.queryById(userId);
        AssertUtil.isTrue(null==userId || null==user, "用户不存在或已注销");
        AssertUtil.isTrue(StringUtils.isBlank(oldPassword), "原始密码为空");
        AssertUtil.isTrue(StringUtils.isBlank(newPassword), "新密码为空");
        AssertUtil.isTrue(!newPassword.equals(confirmPassword), "两次密码不一致");
        AssertUtil.isTrue(!user.getUserPwd().equals(Md5Util.encode(oldPassword)), "原始密码错误");

    }

    public UserModel checkUserLogin(String userName, String userPwd){
        // 检查参数
        checkUserLoginParams(userName, userPwd);
        // 数据查询,通过userName
        User user = userDao.queryUserByName(userName);
        // 判断用户是否存在
        AssertUtil.isTrue(null==user, "用户已注销或者不存在");
        // 判断密码是否正确
        userPwd = Md5Util.encode(userPwd);// 把密码加密进行匹配
        AssertUtil.isTrue(!userPwd.equals(user.getUserPwd()),"用户名或者密码错误");
        // 验证成功
        UserModel model = buildUserModel(user);
        //resultInfo.setMsg("登录成功");
        return model;
    }

    /***
     * 创建返回数据 UserModel
     * */
    private UserModel buildUserModel(User user) {
        UserModel model = new UserModel();
        model.setUserName(user.getUserName());
        model.setTrueName(user.getTrueName());
        model.setUserIdStr(UserIDBase64.encoderUserID(user.getId()));
        return model;
    }

    private void checkUserLoginParams(String userName, String userPwd) {
        AssertUtil.isTrue(StringUtils.isBlank(userName), "用户名为空");
        AssertUtil.isTrue(StringUtils.isBlank(userPwd), "密码为空");
    }

    public Map<String, Object> queryForPage(UserQuery userQuery) throws DataAccessException {
        PageHelper.startPage(userQuery.getPageNum(),userQuery.getPageSize());
        List<UserDto> entities=userDao.queryByParams(userQuery);
        PageInfo<UserDto> pageInfo=new PageInfo<UserDto>(entities);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("total",pageInfo.getTotal());
        map.put("rows",pageInfo.getList());
        return map;
    }


    // 通过用户名查询用户权限

    public List<String> queryUserPermissions(String userName){
        AssertUtil.isTrue(StringUtils.isBlank(userName), CrmConstant.OPS_FAILED_MSG);
        User user = userDao.queryUserByName(userName);
        AssertUtil.isTrue(null==user, CrmConstant.OPS_FAILED_MSG);
        return permissionDao.queryPermissionsByUserId(user.getId());
    }

    public List<Map> queryCustomerManagers(){
        return userDao.queryCustomerManagers();
    }
}
