package com.suixingpay.service;

import com.suixingpay.entity.Silentuser;

public interface SilentuserService {
    /**
     * 柴宇航
     * 查询出所有的被抢到的沉默用户
     * @return
     */
    Silentuser selectSilentuser();
}
