package com.wills.spring.example.service;

import com.wills.spring.example.entity.HttpCode;
import com.wills.spring.example.entity.User;

/**
 * @ClassName TransferService
 * @Date 2021/8/13 13:08
 * @Author 王帅
 * @Version 1.0
 * @Description
 */
public interface TransferService {

    public HttpCode transfer(User from, User to) throws Exception;
}
