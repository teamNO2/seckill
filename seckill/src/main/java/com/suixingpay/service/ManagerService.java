package com.suixingpay.service;


import com.suixingpay.entity.Manager;

public interface ManagerService {


    /**
     * @Description:抢到沉默用户，修改状态
     * @Param: [id]
     * @return: int
     * @Author: lichanghao
     * @Date: 2019/12/9
     */
    int updateManageByManageId(int id);
}
