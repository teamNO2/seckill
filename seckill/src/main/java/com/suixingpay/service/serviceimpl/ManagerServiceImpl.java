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
}
