package com.wills.spring.context.impl;

import com.wills.spring.annotation.AutoWired;
import com.wills.spring.annotation.Component;
import com.wills.spring.annotation.ComponentScan;
import com.wills.spring.annotation.Scope;
import com.wills.spring.context.ApplicationContext;
import com.wills.spring.entity.BeanDefinition;
import com.wills.spring.resource.Resources;
import com.wills.spring.util.BannerUtil;
import com.wills.spring.util.ClassNameUtil;
import com.wills.spring.util.InterfaceUtil;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName ApplicationContext
 * @Date 2022/3/29 09:37
 * @Author 王帅
 * @Version 1.0
 * @Description
 */
public class AutoWiredApplicationContext extends ApplicationContext {

    private static Logger log = Logger.getLogger(AutoWiredApplicationContext.class);

    private Class compoentClazz;
    private ClassLoader componentClaaLoader;
    private String packagePath;
    private String rootPath;
    private Map<String,BeanDefinition> beans = new ConcurrentHashMap<>();
    private Map<String,Object> singletonBeans = new ConcurrentHashMap<>();

    public AutoWiredApplicationContext(){}

    public AutoWiredApplicationContext(Class compoentClazz) {
        this.compoentClazz = compoentClazz;
        this.componentClaaLoader = compoentClazz.getClassLoader();
        // banner
        BannerUtil.printBanner();
        // scan ComponentScan annotation if exists
        if(compoentClazz.isAnnotationPresent(ComponentScan.class)){
            ComponentScan componentScan = (ComponentScan) compoentClazz.getAnnotation(ComponentScan.class);
            String value = componentScan.value();
            this.packagePath = value;
        }
        processBeans();
    }

    @Override
    public <T> T getBean(Class<T> clazz) {
        String key = clazz.getSimpleName();
        BeanDefinition definition = beans.get(key);
        if(definition == null){
            throw new NullPointerException("Class Not Found!");
        }else{
            String scope = definition.getScope();
            String beanName = definition.getBeanName();
            if(!"prototype".equals(scope)){
                // 就是单例
                if(singletonBeans.containsKey(beanName)){
                    return (T) singletonBeans.get(beanName);
                }
            }
            // 否则就创建
            return (T) createBean(definition);
        }
    }


    private Object createBean(BeanDefinition definition){
        boolean isSingleton = "singleton".equals(definition.getScope());
        Object res = null;
        Class clazz = definition.getClazz();
        Constructor[] constructors = clazz.getConstructors();
        for (int i = 0; i < constructors.length; i++) {
            try{
                Constructor constructor = constructors[i];
                int paramsCnt = constructor.getParameterCount();
                // 使用默认 0 参数的 构造方法创建对象
                if(paramsCnt == 0) {
                    res = constructor.newInstance();
                    Field[] fields = res.getClass().getDeclaredFields();
                    for (int j = 0; j < fields.length; j++) {
                        // 检查是否有 @AutoWired 标记，有的话要进行注入
                        Field field = fields[i];
                        if(field.isAnnotationPresent(AutoWired.class)){
                            List<Class> list = InterfaceUtil.getAllInterfaceAchieveClass(field.getType());
                            // 先 byType 然后 byName
                            for (int x = 0; x < list.size(); x++) {
                                Class aClass = list.get(x);
                                // 如果是 clazz 的实现类 或者名字中包含这个字眼
                                if(field.getType().isAssignableFrom(aClass) || aClass.getSimpleName().contains(field.getClass().getSimpleName())){
                                    // 进行属性注入
                                    Object instance = aClass.newInstance();
                                    singletonBeans.put(ClassNameUtil.toLowerFirst(field.getType().getSimpleName()),instance);
                                    field.setAccessible(true);
                                    field.set(res, instance);
                                }
                            }
                        }
                    }
                    break;
                }
                // TODO 否则就进行 多参数的 bean创建(可能存在循环依赖的问题)
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        // 单例对象 放入 Map 中
        if(isSingleton) singletonBeans.put(definition.getBeanName(),res);
        return res;
    }

    @Override
    public void processBeans(){
        String path = getPackagePath();
        List<File> allFiles = listAllFiles(new File(path));
        log.info(allFiles);
        for (File file : allFiles) {
            String classPackage = file.getPath().split(rootPath)[1].replace(File.separatorChar, '.');
            classPackage = classPackage.substring(0,classPackage.indexOf(".class"));
            try {
                Class<?> loadClass = componentClaaLoader.loadClass(classPackage);
                if(loadClass.isAnnotationPresent(Component.class)){
                    // 如果有 @Component 注解，就加入bean
                    log.info("loading class "+classPackage + " ....");
                    Component component = loadClass.getAnnotation(Component.class);
                    String componentName = component.value();
                    if(componentName.trim().length() == 0){
                        // 如果为空，就
                        String clazzName = file.getName().split(".class")[0];
                        componentName = clazzName;
                    }
                    BeanDefinition beanDefinition = new BeanDefinition();
                    beanDefinition.setClazz(loadClass);
                    beanDefinition.setBeanName(componentName);
                    // 判断是否是多例 还是 单例
                    if(loadClass.isAnnotationPresent(Scope.class)){
                        // 如果有 @Scope 注解，进而判断是否有 prototype 有的话，就 实行多例模式
                        Scope scope = loadClass.getAnnotation(Scope.class);
                        String value = scope.value();
                        if("prototype".equals(value)){
                            beanDefinition.setScope(value);
                        }else if("singleton".equals(value) || value.length() == 0){
                            beanDefinition.setScope("singleton");
                        }
                    }else {
                        beanDefinition.setScope("singleton");
                    }
                    beans.put(componentName,beanDefinition);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public List<File> listAllFiles(File file){
        List<File> res = new ArrayList<>();
        // 如果是目录，就进行递归
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for (File f : files) {
                res.addAll(listAllFiles(f));
            }
        }else if(file.getName().endsWith(".class")) res.add(file);
        return res;
    }

    private String getPackagePath(){
        // 否则获取当前类的同级目录
        URL url = compoentClazz.getClassLoader().getResource("");
        this.rootPath = url.getPath();
        String path = url.getPath();
        if(packagePath != null || packagePath.length() > 0){
            String currPath = packagePath.replace('.', File.separatorChar);
            path += currPath;
        }
        return path;
    }
}
