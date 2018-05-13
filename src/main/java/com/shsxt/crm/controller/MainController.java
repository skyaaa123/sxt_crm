package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.po.User;
import com.shsxt.crm.service.UserService;
import com.shsxt.crm.utils.LoginUserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by xlf on 2018/4/24.
 */
@Controller
public class MainController extends BaseController {

    @Resource
    private UserService userService;

    @RequestMapping("main")
    public String index(HttpServletRequest request){
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);// 从cookie中获取id
        User user = userService.queryById(userId);// 通过id查询user
        request.getSession().setAttribute("user",user);// 把user放入session
        return "main";
    }
}
