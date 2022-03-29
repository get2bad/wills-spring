package com.wills.spring.example;

import com.wills.spring.annotation.ComponentScan;
import com.wills.spring.context.impl.AutoWiredApplicationContext;
import com.wills.spring.context.impl.XMLReaderApplicationContext;
import com.wills.spring.example.entity.HttpCode;
import com.wills.spring.example.entity.User;
import com.wills.spring.example.service.TransferService;
import com.wills.spring.example.service.impl.TransferServiceImpl;
import com.wills.spring.factory.BeanFactory;
import com.wills.spring.proxy.ProxyFactory;
import com.wills.spring.resource.Resources;
import com.wills.spring.util.ClassNameUtil;
import org.junit.Test;

import java.io.InputStream;
import java.net.URL;
import java.util.Locale;

/**
 * @ClassName Example
 * @Date 2021/8/13 10:01
 * @Author 王帅
 * @Version 1.0
 * @Description
 * 测试类
 */
@ComponentScan("com.wills.spring")
public class Example {

    @Test
    public void test() throws Exception{
        URL url = Example.class.getClassLoader().getResource("");
        System.out.println(url.getPath());
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
        TransferService service = new XMLReaderApplicationContext().getBean(TransferService.class);
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
        ProxyFactory factory = new XMLReaderApplicationContext().getBean(ProxyFactory.class);
        TransferService service = (TransferService) factory.getProxy(new XMLReaderApplicationContext().getBean(TransferService.class));
        User from = service.getUserById(1);
        User to = service.getUserById(2);
        from.setTotal(from.getTotal() - 20);
        to.setTotal(to.getTotal() + 20);
        HttpCode response = service.transfer(from, to);
        System.out.println(response);
    }

    @Test
    public void test4() throws Exception{
        AutoWiredApplicationContext context = new AutoWiredApplicationContext(Example.class);
        TransferServiceImpl transferService = context.getBean(TransferServiceImpl.class);
        User from = transferService.getUserById(1);
        User to = transferService.getUserById(2);
        from.setTotal(from.getTotal() - 20);
        to.setTotal(to.getTotal() + 20);
        transferService.transfer(from,to);

    }
}
