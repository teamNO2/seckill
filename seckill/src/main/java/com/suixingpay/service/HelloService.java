package com.suixingpay.service;

import com.suixingpay.entity.Manager;
import org.springframework.beans.factory.annotation.Autowired;

public interface HelloService {
    //根据id查询鑫管家
    Manager selectById(Integer id);
}
