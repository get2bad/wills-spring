package com.wills.spring.proxy;

import com.wills.spring.annotation.Transaction;
import com.wills.spring.transaction.TransactionManager;
import com.wills.spring.util.DruidUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

/**
 * @ClassName ProxyFactory
 * @Date 2021/8/14 09:48
 * @Author 王帅
 * @Version 1.0
 * @Description
 * 代理工厂
 */
public class ProxyFactory {

    public Object transactionManager(Object clazz){
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), clazz.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object res = null;
                // 获取是否有注解,有的话，
                // 在进行判断注解的 transactionManager 是否为 true，
                // 如果为true,就开启，没有错就进行提交
                Transaction annotation = method.getAnnotation(Transaction.class);
                // 判断是否为 null
                if(annotation != null){
                    // 如果为true就进行 autoCommit 开启
                    if(annotation.startTransaction()){
                        try{
                            // 开启事务
                            TransactionManager.beginTransaction();
                            // 调用目标方法
                            res = method.invoke(clazz, args);
                            // 然后进行提交
                            TransactionManager.commit();
                        }catch (Exception e){
                            e.printStackTrace();
                            // 回滚
                            TransactionManager.rollback();
                        }
                    }
                } else{
                    res = method.invoke(clazz, args);
                }

                return res;
            }
        });
    }
}
