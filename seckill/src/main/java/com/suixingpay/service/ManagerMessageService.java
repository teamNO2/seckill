package com.suixingpay.service;

import com.suixingpay.entity.Silentuser;

import java.util.List;

/**
 * 张佳鑫
 * 查询鑫管家抢到的用户通知和沉默用户
 */

public interface ManagerMessageService {

    /**
     * 张佳鑫
     * 查询鑫管家抢到的所有用户通知
     */
    List<Silentuser> searchAllUserInfo(Integer managerId);

    /**
     * 张佳鑫
     * 查询鑫管家抢到的沉默用户详细
     */
    Silentuser searchUserInfo(Integer userId);

    /**
     * 张佳鑫
     * 把鑫管家是否抢到沉默用户状态改变从1到0
     */
    int updateManageByManageIsgrab(Integer managerId);
}
