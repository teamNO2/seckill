/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: 孙克强<sun_kq@suixingpay.com>
 * @date: 2019/12/08 14:49
 * @Copyright: 2019 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.suixingpay.repository;

import com.suixingpay.entity.Manager;
import com.suixingpay.entity.Silentuser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SilentuserRepository {
    /**
     * @Author:柴宇航
     * @查询出参加活动的沉默用户
     */
    List<Silentuser> selectSilentuser(String userProvince);

    /**
     * @Author:柴宇航
     * @查询出抢到沉默用户的鑫管家
     */
    List<Manager> selectManager(String manageProvince);
    /**
     * @Author:柴宇航
     * @根据user_id修改manag_id改成已被分配
     */
    Silentuser selectSilentuser();

    int updateManagerId(int manageId,int userId);

    /**
     * @Author:柴宇航
     * @根据user_id修改沉默用户为轮空用户
     */
    int updateSilentuserIsbyebye(int userId);


    /**
     * 张佳鑫
     * 查询鑫管家抢到的所有用户通知
     */
    List<Silentuser> selectByManagerId(String managerId);

    /**
     * 张佳鑫
     * 查询鑫管家抢到的沉默用户详细
     */
    Silentuser selectById(String userId);

    /*
     * 张佳鑫
     * 统计用户资源
     */
    List<Silentuser> selectUserResource();
}
