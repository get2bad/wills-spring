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
public class BeanFactory {

    private static Map<String,Object> map = new HashMap<>();

    // 静态方法，在创建对象的时候自动调用
    static{
        // 创建 SAXReader 解析器
        SAXReader reader = new SAXReader();
        // 拿到 bean 的配置文件
        InputStream stream = Resources.getResourceAsStream("wills-bean.xml");
        try{
            Document document = reader.read(stream);
            Element root = document.getRootElement();
            List<Element> nodes = root.selectNodes("//bean");
            // 实例化 bean 对象，然后将其放入 map 中
            System.out.println("=================================================");
            for (int i = 0; i < nodes.size(); i++) {
                Element node = nodes.get(i);
                String id = node.attributeValue("id");
                String clazzStr = node.attributeValue("class");
                Class<?> clazz = Class.forName(clazzStr);
                Object clazzObj = clazz.newInstance();
                Field[] fields = clazzObj.getClass().getDeclaredFields();
                for (Field field : fields) {
                    // AutoWired 注解支持，支持后，后面的property就可以不需要读取了
                    if(field.getAnnotation(AutoWired.class) != null){
                        // 进行字段自动注入(已完成) 支持 @AutoWired 自动注入
                        System.out.println("检测到有字段使用到了@AutoWired注解！" + field.getType().getName());
                        List<Class> list = InterfaceUtil.getAllInterfaceAchieveClass(field.getType());
                        for (Class classImpl : list) {
                            // 将值设置进去
                            Object instance = classImpl.newInstance();
                            map.put(field.getName(), instance);
                            field.setAccessible(true);
                            field.set(clazzObj, instance);
                        }
                    }
                }
                map.put(id, clazzObj);
            }
            System.out.println("自动注入完成！");
            System.out.println("=================================================");

            // 解析配置文件中的 <property></property> 标签
            List<Element> propertiesNodes = root.selectNodes("//property");
            for (int i = 0; i < propertiesNodes.size(); i++) {
                Element property = propertiesNodes.get(i);
                String name = property.attributeValue("name");
                String ref = property.attributeValue("ref");
                // 拿到父节点的id属性，这个id属性指代的就是目标 bean
                String parentId = property.getParent().attributeValue("id");
                // 从缓存 map 中拿到 目标 bean的对象，准备在后面进行 setXXX 方法的注入
                Object parentObj = map.get(parentId);
                // 拿到 method 方法集合,进行 setXXX 方法注入
                Method[] methods = parentObj.getClass().getMethods();
                for (int j = 0; j < methods.length; j++) {
                    Method method = methods[j];
                    // 如果是 set 就进行注入
                    if(("set" + name).equalsIgnoreCase(method.getName())){
                        // bean之间的依赖关系（注⼊bean）
                        Object refObj = map.get(ref);
                        method.invoke(parentObj, refObj);
                    }
                }
                // 维护依赖关系后重新将bean放⼊map中
                map.put(parentId, parentObj);
            }
//            System.out.println(map);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static Object getBean(String key){
        return map.get(key);
    }

}
