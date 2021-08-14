package com.wills.spring.aop;

import java.lang.reflect.Method;

/**
 * @ClassName SimpleAOPTemplate
 * @Date 2021/8/14 13:01
 * @Author 王帅
 * @Version 1.0
 * @Description
 */
public abstract class SimpleAOPTemplate implements AOPTemplate {

    @Override
    public void before() throws Exception {

    }

    @Override
    public void after() throws Exception {

    }

    @Override
    public void after_returning() throws Exception {

    }

    @Override
    public void after_throwing() throws Exception {

    }

    @Override
    public Object around(Method method, Object clazz, Object[] args) throws Exception {
        return method.invoke(clazz, args);
    }
}