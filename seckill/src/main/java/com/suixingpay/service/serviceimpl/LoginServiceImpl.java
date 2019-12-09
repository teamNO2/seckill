package com.suixingpay.service.serviceimpl;

import com.suixingpay.entity.Manager;
import com.suixingpay.repository.ManagerRepository;
import com.suixingpay.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 *@Author 孙克强
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private ManagerRepository managerRepository;

    @Override
    public Manager selectManagerByNameAndPassword(Manager manager) {
        return managerRepository.selectManagerByNameAndPassword(manager);
    }
}
