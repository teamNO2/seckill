/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: 孙克强<sun_kq @ suixingpay.com>
 * @date: 2019/12/08 14:49
 * @Copyright: 2019 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.suixingpay.repository;

import com.suixingpay.entity.Scene;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description:
 * @author: 孙克强<sun_kq @ suixingpay.com>
 * @date: 2019/12/08 14:49
 * @version: V1.0
 */
@Mapper
@Repository
public interface SceneRepository {
    //新增活动
    int insertSelective(Scene scene);

    //查询所有活动
    List<Scene>  getAllScenes();

    //按id查询活动
    Scene selectById(String id);


    //记录每次活动未被认领的沉默用户数量
    int updateUnallocated(int scene_unallocated,int scene_id);




}
