package com.suixingpay.service;

import com.suixingpay.entity.Manager;

/*
 *@Author 孙克强
 */
public interface LoginService {
    //鑫管家登录
    Manager selectManagerByNameAndPassword(Manager manager);
}
