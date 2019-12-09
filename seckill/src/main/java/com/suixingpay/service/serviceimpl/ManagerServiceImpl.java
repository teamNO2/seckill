package com.suixingpay.service.serviceimpl;

import com.suixingpay.repository.ManagerRepository;
import com.suixingpay.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: ManagerServiceImpl
 * @Param:
 * @return:
 * @Author: lichanghao
 * @Date: 2019/12/8
 */
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    private ManagerRepository managerRepository;

    @Override
    public Manager selectById(int manageId) {
        return managerRepository.selectById(String.valueOf(manageId));
    }


    /**
     * @Description:根据id秒杀沉默用户
     * @Param: [id]
     * @return: int
     * @Author: lichanghao
     * @Date: 2019/12/9
     */
    @Override
    public int updateManageByManageId(int id) {
        return managerRepository.updateManageByManageId(id);
    }
}
