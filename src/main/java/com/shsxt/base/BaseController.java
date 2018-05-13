package com.shsxt.base;

import com.shsxt.crm.model.ResultInfo;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by xlf on 2018/4/24.
 */
public class BaseController {

    @ModelAttribute
    public void preHandler(HttpServletRequest request){
        request.setAttribute("ctx",request.getContextPath());
    }

    public ResultInfo success(String msg, Object result){
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setMsg(msg);
        resultInfo.setResult(result);
        return resultInfo;
    }

    public ResultInfo success(Object result){
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setResult(result);
        return resultInfo;
    }

    public ResultInfo success(String msg){
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setMsg(msg);
        return resultInfo;
    }
}
