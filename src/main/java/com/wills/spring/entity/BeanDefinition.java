package com.wills.spring.entity;

import com.wills.spring.annotation.Scope;

/**
 * @ClassName BeanDefinition
 * @Date 2022/3/29 11:45
 * @Author 王帅
 * @Version 1.0
 * @Description
 */
public class BeanDefinition {

    private String beanName;
    private Class clazz;
    private String scope;

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
