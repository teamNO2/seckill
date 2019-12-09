package com.suixingpay.service.serviceimpl;

import com.suixingpay.entity.Silentuser;
import com.suixingpay.repository.SilentuserRepository;
import com.suixingpay.service.SilentuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SilentuserServiceImpl implements SilentuserService {
    @Autowired
    private SilentuserRepository silentuserRepository;

    /**
     * 柴宇航
     * 查询出所有被鑫管家抢到的沉默用户
     * @return
     */
    public Silentuser selectSilentuser(){
        return silentuserRepository.selectSilentuser();
    }

}
