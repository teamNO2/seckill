package com.suixingpay.service.serviceimpl;

import com.suixingpay.repository.ManagerRepository;
import com.suixingpay.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;

public class ManagerServiceImpl implements ManagerService {
    @Autowired
    private ManagerRepository managerRepository;
}
