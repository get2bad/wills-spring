package com.wills.spring.example;

import com.wills.spring.example.entity.User;
import com.wills.spring.example.service.TransferService;
import com.wills.spring.example.service.impl.TransferServiceImpl;
import com.wills.spring.example.singleton.Singleton;
import com.wills.spring.factory.BeanFactory;
import com.wills.spring.resource.Resources;
import org.junit.Test;

import java.io.InputStream;
import java.util.Date;

/**
 * @ClassName Example
 * @Date 2021/8/13 10:01
 * @Author 王帅
 * @Version 1.0
 * @Description
 * 测试类
 */
public class Example {

    @Test
    public void test() throws Exception{

    }

    @Test
    public void test1() throws Exception{
        InputStream stream = Resources.getResourceAsStream("wills-bean.xml");
        byte[] data = new byte[1024];
        stream.read(data);
        System.out.println(new String(data).trim());
    }

    @Test
    public void test2() throws Exception{
//        BeanFactory factory = new BeanFactory();
        TransferService service = (TransferService)BeanFactory.getBean("transferService");
        User from = Singleton.getUserById(1);
        from.setTansfer(20d);
        User to = Singleton.getUserById(2);
        service.transfer(from, to);
    }
}
