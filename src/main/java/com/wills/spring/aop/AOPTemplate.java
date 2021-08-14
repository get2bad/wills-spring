package com.wills.spring.aop;

import java.lang.reflect.Method;

/**
 * @ClassName AOPTemplate
 * @Date 2021/8/14 11:28
 * @Author 王帅
 * @Version 1.0
 * @Description
 * AOP 的 模板类,当方法上声明 @AOP 注解后，会进行相关方法的插入
 */
public interface AOPTemplate {

    public void before() throws Exception;

    public void after() throws Exception;

    public void after_returning() throws Exception;

    public void after_throwing() throws Exception;

    public Object around(Method method,Object clazz,Object[] args) throws Exception;
}
