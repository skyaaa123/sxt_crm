package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by xlf on 2018/4/24.
 */
@Controller
public class IndexController extends BaseController {

    @RequestMapping("index")
    public String index(){
        return "index";
    }
}
