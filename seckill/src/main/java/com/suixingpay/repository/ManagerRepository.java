/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: 孙克强<sun_kq@suixingpay.com>
 * @date: 2019/12/08 14:48
 * @Copyright: 2019 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.suixingpay.repository;

import com.suixingpay.entity.Manager;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description:
 * @author: 孙克强<sun_kq@suixingpay.com>
 * @date: 2019/12/08 14:48
 * @version: V1.0
 */
@Mapper
@Repository
//管家信息
public interface ManagerRepository {
    Manager selectById(String id);

    /**
     * @Description:根据城市和鑫管家ID判断鑫管家是否在本城市
     * @Param: [City]
     * @return: java.util.List<com.suixingpay.entity.Manager>
     * @Author: lichanghao
     * @Date: 2019/12/8
     */
    Manager selectManagerByCity(String City,int manageId);

    int updateManageByManageId(int id);

    //鑫管家登录
    Manager selectManagerByNameAndPassword(Manager manager);

    /*
     * 张佳鑫
     * 修改鑫管家状态从1变成0
     */
    int updateManageByManageId2(Integer managerId);
}
