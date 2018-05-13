package com.shsxt.crm.exceptions;

import com.alibaba.fastjson.JSON;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.model.ResultInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * Created by lp on 2018/1/8.
 */
@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView mv=getDefaultModelAndView(request,ex);

        if(ex instanceof  AuthException){
            AuthException ae= (AuthException) ex;
            mv.setViewName("au_error");
            mv.addObject("errorMsg",ae.getMsg());
            mv.addObject("errorCode",ae.getCode());
            return mv;
        }

        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod= (HandlerMethod) handler;
            Method method= handlerMethod.getMethod();
            ResponseBody responseBody= method.getAnnotation(ResponseBody.class);
            if(null!=responseBody){
                ResultInfo resultInfo=new ResultInfo();
                resultInfo.setCode(CrmConstant.OPS_FAILED_CODE);
                resultInfo.setMsg(CrmConstant.OPS_FAILED_MSG);
                if(ex instanceof  ParamsException){
                    ParamsException pe= (ParamsException) ex;
                    resultInfo.setCode(pe.getCode());
                    resultInfo.setMsg(pe.getMsg());
                }
                response.setCharacterEncoding("utf-8");
                response.setContentType("application/json;charset=utf-8");
                PrintWriter pw =null;
                try {
                    pw=response.getWriter();
                    pw.write(JSON.toJSONString(resultInfo));
                    pw.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(null!=pw){
                        pw.close();
                    }
                }
                return null;
            }else{
                if(ex instanceof  ParamsException){
                    ParamsException pe= (ParamsException) ex;
                    mv.addObject("errorMsg",pe.getMsg());
                    mv.addObject("errorCode",pe.getCode());
                }
                return mv;
            }
        }else{
            return mv;
        }
    }

    private ModelAndView getDefaultModelAndView(HttpServletRequest request, Exception ex) {
        ModelAndView mv=new ModelAndView();
        mv.setViewName("error");
        mv.addObject("ctx",request.getContextPath());
        mv.addObject("errorMsg", CrmConstant.OPS_FAILED_MSG);
        mv.addObject("errorCode",CrmConstant.OPS_FAILED_CODE);
        mv.addObject("uri",request.getRequestURI());// 记录请求路径
        return mv;
    }
}
