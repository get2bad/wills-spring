package com.wills.spring.resource;

import java.io.InputStream;

/**
 * @ClassName Resources
 * @Date 2021/8/13 11:15
 * @Author 王帅
 * @Version 1.0
 * @Description
 * 读取配置文件，返回流对象 inputStream
 */
public class Resources {

    // 获取配置文件的文件输入流
    public static InputStream getResourceAsStream(String path){
        InputStream stream = Resources.class.getClassLoader().getResourceAsStream(path);
        return stream;
    }

    public static String getResourceFilePath(String filename){
        String res = Resources.class.getClassLoader().getResource(filename).getPath();
        return res;
    }
}
