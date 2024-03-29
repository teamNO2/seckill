package com.suixingpay.service;

import com.suixingpay.entity.Manager;
import com.suixingpay.entity.Silentuser;

import java.util.List;

public interface SilentuserService {
    /**
     * @Author:柴宇航
     * @查询参加活动的沉默用户
     * @return
     */
    List<Silentuser> selectSilentuser(String userProvince);

    /**
     * @Author:柴宇航
     * @查询出抢到沉默用户的鑫管家
     */
    List<Manager> selectManager(String manageProvince);
    /**
     * @Author:柴宇航
     * @根据user_id修改manag_id改成已被分配
     */
    int updateManagerId(int manageId,String userTime,int userId);

    /**
     * @Author:柴宇航
     * @根据user_id修改沉默用户为轮空用户
     */
    int updateSilentuserIsbyebye(int userId);
}
