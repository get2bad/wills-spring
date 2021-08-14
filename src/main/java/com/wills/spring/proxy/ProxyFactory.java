package com.wills.spring.proxy;

import com.wills.spring.annotation.AOP;
import com.wills.spring.annotation.Transaction;
import com.wills.spring.aop.AOPTemplate;
import com.wills.spring.aop.aop_enum.AOPEnum;
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

    public Object getProxy(Object clazz){
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), clazz.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object res = null;
                Transaction transaction = null;
                AOP aop = null;
                Annotation[] annotations = method.getAnnotations();
                for (Annotation annotation : annotations) {
                    // 如果 当前遍历注解属于 事务 @Transaction 注解，就进行开启事务处理
                    if(annotation instanceof Transaction)
                        transaction = method.getAnnotation(Transaction.class);

                    // 如果当前 遍历的注解 属于 切面 @AOP 注解，就进行判断切面处理
                    if(annotation instanceof AOP)
                        aop = method.getAnnotation(AOP.class);
                }

                if(aop != null){
                    res = aop(aop,transaction,method,clazz,args);
                } else if(transaction != null) {
                    res = startTransaction(transaction, method, clazz, args);
                } else {
                    res = method.invoke(clazz, args);
                }

                return res;
            }
        });
    }



    public Object aop(AOP aop, Transaction transaction,Method method,Object clazz, Object[] args) throws Exception{
        // 返回值
        Object res = null;
        boolean trans = false;
        // 相关的切面标志符
        boolean before = false;
        boolean after = false;
        boolean after_returning = false;
        boolean after_throwing = false;
        boolean around = false;

        if(transaction != null && transaction.startTransaction()) trans = true;

        AOPEnum[] pointcuts = aop.pointcut();
        // 遍历所有切面，判断其类型
        for (AOPEnum pointcut : pointcuts) {
            if(pointcut == AOPEnum.BEFORE){
                before = true;
            }

            if(pointcut == AOPEnum.AFTER){
                after = true;
            }

            if(pointcut == AOPEnum.AFTER_RETURNING){
                after_returning = true;
            }

            if(pointcut == AOPEnum.AFTER_THROWING){
                after_throwing = true;
            }

            if(pointcut == AOPEnum.AROUND){
                around = true;
            }
        }

        // 获取目标实现类，然后进行 newInstance 操作
        Class<?> resolveClass = aop.resolveClass();
        AOPTemplate template = (AOPTemplate) resolveClass.newInstance();
        // 巧妙使用 try-catch-finally 来设计AOP
        try{
            if(before) template.before();
            // 调用目标方法，拿到返回值
            if(trans) TransactionManager.beginTransaction();
            if(around) {
                res = template.around(method, clazz, args);
            }else{
                res = method.invoke(clazz, args);
            }
            if(trans) TransactionManager.commit();
            if(after) template.after();
            return res;
        }catch (Exception e){
            if(after_throwing) template.after_throwing();
            if(trans) TransactionManager.rollback();
            throw e;
        }finally {
            if(after_returning) template.after_returning();
        }
    }

    /**
     * 事务处理方法，如果声明了 @Transaction 注解，就会进入其判断
     * @param annotation
     * @param method
     * @param clazz
     * @param args
     * @return
     * @throws Exception
     */
    public Object startTransaction(Transaction annotation,Method method,Object clazz, Object[] args) throws Exception{
        // 获取是否有注解,有的话，
        // 在进行判断注解的 transactionManager 是否为 true，
        // 如果为true,就开启，没有错就进行提交

        Object res = null;

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
//                    TransactionManager.commit();
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
}
