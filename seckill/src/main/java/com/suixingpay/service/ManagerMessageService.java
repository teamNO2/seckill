package com.suixingpay.service;

import com.suixingpay.entity.Silentuser;

import java.util.List;

public interface ManagerMessageService {

    //查询鑫管家抢到的用户通知
    List<Silentuser> searchAllUserInfo(Integer managerId);

    //查询抢到的沉默用户
    Silentuser searchUserInfo(Integer userId);

}
