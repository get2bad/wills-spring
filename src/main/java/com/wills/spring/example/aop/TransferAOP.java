package com.wills.spring.example.aop;

import com.wills.spring.aop.AOPTemplate;
import com.wills.spring.aop.SimpleAOPTemplate;

import java.lang.reflect.Method;

/**
 * @ClassName TransferAOP
 * @Date 2021/8/14 13:00
 * @Author 王帅
 * @Version 1.0
 * @Description
 * AOP 切面的实现类，配置在 @AOP(point={XX},resolveClass=XXX.class)上
 */
public class TransferAOP extends SimpleAOPTemplate {


    @Override
    public void before() throws Exception {
        System.out.println("转账执行前");
    }

    @Override
    public void after() throws Exception {
        System.out.println("转账执行后");
    }

    @Override
    public Object around(Method method, Object clazz, Object[] args) throws Exception {
        Object res = null;
        System.out.println("方法执行期间之前");
        res = method.invoke(clazz, args);
        System.out.println("方法执行期间之后");
        return res;
    }
}
