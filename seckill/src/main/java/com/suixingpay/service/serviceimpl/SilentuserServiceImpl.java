package com.suixingpay.service.serviceimpl;

import com.suixingpay.entity.Manager;
import com.suixingpay.entity.Silentuser;
import com.suixingpay.repository.SilentuserRepository;
import com.suixingpay.service.SilentuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
/**
 * @Author:柴宇航
 * @柴宇航私有模块
 */
public class SilentuserServiceImpl implements SilentuserService {
    @Autowired
    private SilentuserRepository silentuserRepository;
    /**
     * @Author:柴宇航
     * @查询出参与活动的沉默用户
     * @return
     */
    public List<Silentuser> selectSilentuser(String userProvince){
        return silentuserRepository.selectSilentuser(userProvince);
    }

    /**
     * @Author:柴宇航
     * @查询出抢到沉默用户的鑫管家
     * @return
     */
    public List<Manager> selectManager(String manageProvince){
        return silentuserRepository.selectManager(manageProvince);
    }

    /**
     * @Author:柴宇航
     * @根据user_id修改manage_id改成已被分配
     * @param userId
     * @return
     */
    public int updateManagerId(int manageId,int userId){
        return silentuserRepository.updateManagerId(manageId,userId);
    }

    /**
     * @Author:柴宇航
     * @根据user_id修改沉默用户为轮空
     * @param userId
     * @return
     */
    public int updateSilentuserIsbyebye(int userId){
        return silentuserRepository.updateSilentuserIsbyebye(userId);
    }

}
