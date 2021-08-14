package com.wills.spring.example.dao.impl;

import com.wills.spring.example.dao.TransferDao;
import com.wills.spring.example.entity.User;
import com.wills.spring.util.DruidUtils;

import java.sql.*;

/**
 * @ClassName TransferDaoImpl
 * @Date 2021/8/13 13:10
 * @Author 王帅
 * @Version 1.0
 * @Description
 */
public class TransferDaoImpl implements TransferDao {


//    @Override
//    public void transfer(Integer fromId, Integer toId, Double transfer) throws Exception{
//        Double fromTotal = from.getTotal();
//        Double fromTransfer = from.getTansfer();
//        Double toTotal = to.getTotal();
//        toTotal += fromTransfer;
//        fromTotal -= fromTransfer;
//        from.setTotal(fromTotal);
//        to.setTotal(toTotal);
//        from.setTransferDate(new Date());
//        to.setTransferDate(new Date());
//        Singleton.transferInFile(from, to);
//    }

    @Override
    @SuppressWarnings(value = "all")
    public User getUserById(Integer id) {
        Connection connection = null;
        try {
            connection = DruidUtils.getCurrentThreadConn();
            String sql = "Select * from transfer where id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            User user = new User();
            while (set.next()){
                user.setId(set.getInt("id"));
                user.setName(set.getString("name"));
                user.setTotal(set.getInt("total"));
                user.setDate(set.getDate("date"));
            }
            statement.close();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void transfer(User from,User to) throws Exception{
        Integer fromId = from.getId();
        Integer fromTotal = from.getTotal();
        update(fromId, fromTotal);
        Integer toId = to.getId();
        Integer toTotal = to.getTotal();
        update(toId, toTotal);
    }

    @SuppressWarnings(value = "all")
    public void update(Integer id,Integer total){
        Connection connection = null;
        try {
            connection = DruidUtils.getCurrentThreadConn();
            String sql = "update transfer set total = ? , date = ? where id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, total);
            statement.setDate(2, new Date((new java.util.Date()).getTime()));
            statement.setInt(3, id);
            int effect = statement.executeUpdate();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
