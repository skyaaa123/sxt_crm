package com.shsxt.crm.annotations;

import java.lang.annotation.*;

/**
 * Created by User on 2018/5/3.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestPermission {
    String aclValue() default "";
}
