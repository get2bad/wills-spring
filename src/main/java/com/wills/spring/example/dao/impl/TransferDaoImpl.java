package com.wills.spring.example.dao.impl;

import com.wills.spring.example.dao.TransferDao;
import com.wills.spring.example.entity.User;
import com.wills.spring.example.singleton.Singleton;

import java.util.Date;

/**
 * @ClassName TransferDaoImpl
 * @Date 2021/8/13 13:10
 * @Author 王帅
 * @Version 1.0
 * @Description
 */
public class TransferDaoImpl implements TransferDao {


    @Override
    public void transfer(User from, User to) throws Exception{
        Double fromTotal = from.getTotal();
        Double fromTransfer = from.getTansfer();
        Double toTotal = to.getTotal();
        toTotal += fromTransfer;
        fromTotal -= fromTransfer;
        from.setTotal(fromTotal);
        to.setTotal(toTotal);
        from.setTransferDate(new Date());
        to.setTransferDate(new Date());
        Singleton.transferInFile(from, to);
    }
}
