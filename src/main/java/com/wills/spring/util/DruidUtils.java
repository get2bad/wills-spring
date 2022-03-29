package com.wills.spring.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;

/**
 * @ClassName DruidUtils
 * @Date 2021/8/13 16:31
 * @Author 王帅
 * @Version 1.0
 * @Description
 */
public class DruidUtils {

    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    public static Connection getCurrentThreadConn() throws Exception{
        Connection connection = threadLocal.get();
        if(connection == null || connection.isClosed()){
            // 配置 c3p0 连接池
            // TODO 后期会读取配置文件
            ComboPooledDataSource dataSource = new ComboPooledDataSource();
            dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
            dataSource.setUser("root");
            dataSource.setPassword("123456");
            dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8");
            connection = dataSource.getConnection();
            threadLocal.set(connection);
        }
        return connection;
    }
}
