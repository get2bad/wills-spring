package com.wills.spring.example.service.impl;

import com.wills.spring.annotation.AutoWired;
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
    public HttpCode transfer(User from, User to) throws Exception {
        try {
            transferDao.transfer(from, to);
            return HttpCode.ok();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpCode.faild("未知错误,转账失败！");
    }

    public void setTransferDao(TransferDao transferDao) {
        this.transferDao = transferDao;
    }
}
