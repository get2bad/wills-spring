package com.wills.spring.example.dao;

import com.wills.spring.example.entity.HttpCode;
import com.wills.spring.example.entity.User;

/**
 * @ClassName TransferDao
 * @Date 2021/8/13 13:09
 * @Author 王帅
 * @Version 1.0
 * @Description
 */
public interface TransferDao {

    public User getUserById(Integer id);

    public void transfer(User from,User to) throws Exception;
}
