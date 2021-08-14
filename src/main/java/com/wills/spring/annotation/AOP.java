package com.wills.spring.annotation;


import com.wills.spring.aop.AOPTemplate;
import com.wills.spring.aop.aop_enum.AOPEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AOP {

    // 切面，可以有多个
    public AOPEnum[] pointcut();

    // 处理的切面实现类
    public Class<? extends AOPTemplate> resolveClass();
}


