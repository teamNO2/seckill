package com.suixingpay.service.serviceimpl;

import com.suixingpay.entity.Manager;
import com.suixingpay.repository.ManagerRepository;
import com.suixingpay.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 *@Author sunkeqiang
 */
@Service
public class HelloServiceImpl implements HelloService {
    @Autowired
    private ManagerRepository managerRepository;

    //根据id查询鑫管家
    public Manager selectById(Integer id) {
        return managerRepository.selectById(String.valueOf(id));
    }
}
