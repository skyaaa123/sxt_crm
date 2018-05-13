package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.dto.UserDto;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.model.UserModel;
import com.shsxt.crm.po.User;
import com.shsxt.crm.query.UserQuery;
import com.shsxt.crm.service.PermissionService;
import com.shsxt.crm.service.UserService;
import com.shsxt.crm.utils.LoginUserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by xlf on 2018/4/24.
 */
@Controller
@RequestMapping("user")
public class UserController extends BaseController {

    @Resource
    private UserService userService;


    @RequestMapping("login")
    @ResponseBody
    public ResultInfo checkUserLogin(String userName, String userPwd, HttpSession session){
        UserModel userModel = userService.checkUserLogin(userName, userPwd);
        // 查询用户权限码,放入session
        List<String> permissions = userService.queryUserPermissions(userName);
        session.setAttribute(CrmConstant.USER_PERMISSIONS, permissions);
        System.err.println(permissions);
        return success("登陆成功",userModel);
    }
/*
    public ResultInfo checkUserLogin(String userName, String userPwd){
        ResultInfo resultInfo = new ResultInfo();
        UserModel userModel = null;
        try {
            userModel = userService.checkUserLogin(userName, userPwd);
            // 捕捉异常: 先小后大
        } catch (ParamsException e) {
            e.printStackTrace();
            resultInfo.setCode(e.getCode());
            resultInfo.setMsg(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setCode(300);
            resultInfo.setMsg("系统异常");
        }
        resultInfo.setResult(userModel);
        return resultInfo;
    }
    */

    /* 更新用户密码 */
    @RequestMapping("updateUserPwd")
    @ResponseBody
    public ResultInfo updateUserPwd(String oldPassword, String newPassword,
                                    String confirmPassword, HttpServletRequest request){
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        userService.updateUserPwd(userId, oldPassword, newPassword, confirmPassword);
        return success("用户密码更新成功");
    }

    @RequestMapping("index")
    public String index(){
        return "user";
    }

    @RequestMapping("addOrUpdateUser")
    @ResponseBody
    public ResultInfo addOrUpdateUser(UserDto user){
        userService.addOrUpdateUser(user);
        return success(CrmConstant.OPS_SUCCESS_MSG);
    }

    @RequestMapping("queryUsers")
    @ResponseBody
    public Map<String, Object> queryUsers(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer rows,
            UserQuery userQuery){
        userQuery.setPageNum(page);
        userQuery.setPageSize(rows);
        return userService.queryForPage(userQuery);
    }

    @RequestMapping("delUser")
    @ResponseBody
    public ResultInfo delUser(Integer[] ids){
        userService.deleteUserBatch(ids);
        return success(CrmConstant.OPS_SUCCESS_MSG);
    }

    @RequestMapping("queryCustomerManagers")
    @ResponseBody
    public List<Map> queryCustomerManagers(){
        return userService.queryCustomerManagers();
    }


}
