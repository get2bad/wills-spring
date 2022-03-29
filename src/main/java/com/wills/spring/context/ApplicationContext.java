package com.wills.spring.context;

import com.wills.spring.factory.BeanFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName ApplicationContext
 * @Date 2022/3/29 09:37
 * @Author 王帅
 * @Version 1.0
 * @Description
 */
public abstract class ApplicationContext implements BeanFactory {

    private String packagePath;
    private Map<String,Object> beans = new ConcurrentHashMap<>();

    public ApplicationContext(){}

    public ApplicationContext(String packagePath) {
        this.packagePath = packagePath;
    }

    @Override
    public void processBeans(){
    }
}
