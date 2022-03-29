package com.wills.spring.util;

import com.wills.spring.resource.Resources;

import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName BannerUtil
 * @Date 2022/3/29 14:46
 * @Author 王帅
 * @Version 1.0
 * @Description
 */
public class BannerUtil {

    private BannerUtil(){}

    public static void printBanner(){
        try {
            InputStream stream = Resources.getResourceAsStream("banner.txt");
            byte[] data = new byte[stream.available()];
            stream.read(data);
            System.out.println(new String(data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
