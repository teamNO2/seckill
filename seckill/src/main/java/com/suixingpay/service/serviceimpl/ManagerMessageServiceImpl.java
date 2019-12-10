package com.suixingpay.service.serviceimpl;

import com.suixingpay.entity.Silentuser;
import com.suixingpay.repository.ManagerRepository;
import com.suixingpay.repository.SilentuserRepository;
import com.suixingpay.service.ManagerMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 张佳鑫
 * 查询鑫管家抢到的用户通知和沉默用户
 */

@Service
public class ManagerMessageServiceImpl implements ManagerMessageService {

    @Autowired
    private SilentuserRepository silentuserRepository;

    @Autowired
    private ManagerRepository managerRepository;

    /**
     * 张佳鑫
     * 查询鑫管家抢到的所有用户通知
     */
    @Override
    public List<Silentuser> searchAllUserInfo(Integer managerId) {
        return silentuserRepository.selectByManagerId(String.valueOf(managerId));
    }

    /**
     * 张佳鑫
     * 查询鑫管家抢到的沉默用户详细
     */
    @Override
    public Silentuser searchUserInfo(Integer userId) {
        return silentuserRepository.selectById(String.valueOf(userId));
    }

    /**
     * 张佳鑫
     * 把鑫管家是否抢到沉默用户状态改变从1到0
     */
    @Override
    public int updateManageByManageIsgrab(Integer managerId) {
        return managerRepository.updateManageByManageId2(managerId);
    }
}
