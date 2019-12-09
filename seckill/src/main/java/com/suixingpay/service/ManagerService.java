package com.suixingpay.service;


import com.suixingpay.entity.Manager;

public interface ManagerService {
    //石梦瑶
    //根据管家id查询所有
    Manager selectById(int manageId);


    /**
     * @Description:抢到沉默用户，修改状态
     * @Param: [id]
     * @return: int
     * @Author: lichanghao
     * @Date: 2019/12/9
     */
    int updateManageByManageId(int id);
}
