package com.wills.spring.util;

/**
 * @ClassName ClassNameUtil
 * @Date 2022/3/29 15:46
 * @Author 王帅
 * @Version 1.0
 * @Description
 */
public class ClassNameUtil {

    private ClassNameUtil(){}


    public static String toLowerFirst(String className){
        String lower = className.substring(0, 1).toLowerCase();
        String end = className.substring(1);
        return lower + end;
    }
}
