/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: 孙克强<sun_kq@suixingpay.com>
 * @date: 2019/12/08 14:49
 * @Copyright: 2019 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.suixingpay.repository;

import com.suixingpay.entity.Silentuser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description:
 * @author: 孙克强<sun_kq@suixingpay.com>
 * @date: 2019/12/08 14:49
 * @version: V1.0
 */
@Mapper
@Repository
public interface SilentuserRepository {

    //查询鑫管家抢到的用户通知
    List<Silentuser> selectByManagerId(String managerId);

    //查询抢到的沉默用户
    Silentuser selectById(String userId);

}
