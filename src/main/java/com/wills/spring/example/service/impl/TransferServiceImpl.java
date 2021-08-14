package com.wills.spring.example.service.impl;

import com.wills.spring.annotation.AutoWired;
import com.wills.spring.annotation.Transaction;
import com.wills.spring.example.dao.TransferDao;
import com.wills.spring.example.entity.HttpCode;
import com.wills.spring.example.entity.User;
import com.wills.spring.example.service.TransferService;

/**
 * @ClassName TransferServiceImpl
 * @Date 2021/8/13 13:08
 * @Author 王帅
 * @Version 1.0
 * @Description
 */
public class TransferServiceImpl implements TransferService {

    @AutoWired
    private TransferDao transferDao;

    @Override
    public HttpCode transfer(User from,User to) throws Exception {
        try {
            System.out.println("正在执行转账");
            transferDao.transfer(from, to);
            return HttpCode.ok();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpCode.faild("未知错误,转账失败！");
    }

    @Override
    public User getUserById(Integer id) throws Exception {
        return transferDao.getUserById(id);
    }


    public void setTransferDao(TransferDao transferDao) {
        this.transferDao = transferDao;
    }
}
