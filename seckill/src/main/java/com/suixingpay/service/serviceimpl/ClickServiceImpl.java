package com.suixingpay.service.serviceimpl;

import com.suixingpay.entity.Manager;
import com.suixingpay.service.ClickService;
import com.suixingpay.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName ClickServiceImpl
 * @Description TODO
 * @Author ShiMengyao
 * @Date 2019 年 12 月 8 日 0008 17:32:25
 * @Version 1.0
 */
@Service
public class ClickServiceImpl implements ClickService {
    @Autowired
    private ManagerService managerService;
    @Override
    public Manager Click(Integer managerId) {
        return managerService.selectById(managerId);
    }

}

