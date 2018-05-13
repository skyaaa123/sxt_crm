package com.shsxt.crm.proxy;

import com.shsxt.crm.annotations.RequestPermission;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.utils.AssertUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by lp on 2018/5/3.
 */
@Component
@Aspect
public class PermisssionProxy {
    /* aop: 1.切入點 2.通知 */
    @Resource
    private HttpSession session; // 通过框架获取session
    // 定义切入点
    @Pointcut("@annotation(com.shsxt.crm.annotations.RequestPermission)")
    public void cut(){}
    // 定义通知
    @Around("cut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("权限拦截");

        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        RequestPermission annotation = method.getAnnotation(RequestPermission.class);
        if(null!=annotation){
            // 做处理
            List<String> permissions = (List<String>) session.getAttribute(CrmConstant.USER_PERMISSIONS);
            AssertUtil.isTrue(CollectionUtils.isEmpty(permissions),"没有权限");
            String aclValue = annotation.aclValue();
            System.out.println(aclValue);
            AssertUtil.isTrue(!permissions.contains(aclValue), "没有权限");
        }
        return pjp.proceed();// 很重要, 保证下一步操作继续进行
    }
}
