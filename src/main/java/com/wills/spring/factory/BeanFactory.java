package com.wills.spring.factory;

import com.wills.spring.annotation.AutoWired;
import com.wills.spring.resource.Resources;
import com.wills.spring.util.InterfaceUtil;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.*;

/**
 * @ClassName BeanFactory
 * @Date 2021/8/13 11:27
 * @Author 王帅
 * @Version 1.0
 * @Description
 * bean 工厂类
 * 1. 加载解析xml，读取xml中的bean信息，通过反射技术实例化bean对象，然后放入map待用，如果有需要，会自动返回其对象
 *
 * 2. 提供接口方法根据id 从 map中获取 bean (静态方法)
 */
public interface BeanFactory {


    public <T> T getBean(Class<T> clazz);

    public void processBeans();

}
