package com.wills.spring.transaction;

import com.wills.spring.util.DruidUtils;

import java.sql.Connection;

/**
 * @ClassName TransactionManager
 * @Date 2021/8/14 09:36
 * @Author 王帅
 * @Version 1.0
 * @Description
 */
public class TransactionManager {

//    private DruidUtils druidUtils;
//
//    public void setDruidUtils(DruidUtils druidUtils) {
//        this.druidUtils = druidUtils;
//    }

    public static Connection beginTransaction() throws Exception{
        Connection conn = DruidUtils.getCurrentThreadConn();
        conn.setAutoCommit(false);
        return conn;
    }

    public static void commit() throws Exception{
        DruidUtils.getCurrentThreadConn().commit();
    }

    public static void rollback() throws Exception{
        DruidUtils.getCurrentThreadConn().rollback();
    }
}
