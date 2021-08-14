package com.wills.spring.example;

import com.wills.spring.example.entity.HttpCode;
import com.wills.spring.example.entity.User;
import com.wills.spring.example.service.TransferService;
import com.wills.spring.factory.BeanFactory;
import com.wills.spring.proxy.ProxyFactory;
import com.wills.spring.resource.Resources;
import org.junit.Test;

import java.io.InputStream;

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
        User from = service.getUserById(1);
        User to = service.getUserById(2);
        from.setTotal(from.getTotal() - 20);
        to.setTotal(to.getTotal() + 20);
        service.transfer(from,to);
    }

    /**
     * 测试 事务注解 以及 代理工厂 还有 @AOP 切面测试
     * @throws Exception
     */
    @Test
    public void test3() throws Exception{
        ProxyFactory factory = (ProxyFactory) BeanFactory.getBean("proxyFactory");
        TransferService service = (TransferService) factory.getProxy(BeanFactory.getBean("transferService"));
        User from = service.getUserById(1);
        User to = service.getUserById(2);
        from.setTotal(from.getTotal() - 20);
        to.setTotal(to.getTotal() + 20);
        HttpCode response = service.transfer(from, to);
        System.out.println(response);
    }
}
