package com.suixingpay.service;

import com.suixingpay.entity.Scene;
import com.suixingpay.entity.Silentuser;
import io.swagger.models.auth.In;
import com.suixingpay.utils.GenericResponse;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * 孙克强
 */
public interface SceneService {
    //新增活动  孙克强
    int insertScene(Scene scene);

    //查询所有活动  孙克强
    List<Scene> getAllScenes();

    //按id查询活动  孙克强
    Scene selectById(Integer id);

    int selectSceneNumberById(int id);

    Scene selectSceneByTime(String time);

    //记录每次活动未被认领的沉默用户数量
    int updateUnallocated(int sceneUnallocated, int sceneId);

    /*
     * 张佳鑫
     * 统计用户资源
     */
    List<Silentuser> selectUserResource(String curTime);

    /*
     * 张佳鑫
     * 查询每场活动的结束时间
     */
    Integer selectEndTime(String sceneEndTime);


    //根据状态判断活动状态未开始
    List<Scene> judgeStateByTime1() throws Exception;

    //根据状态判断活动状态正在进行
    List<Scene> judgeStateByTime2()throws Exception;

    //根据状态判断活动状态已结束
    List<Scene> judgeStateByTime3()throws Exception;

    //查询当前省份可用的沉默用户数  孙克强
    int findCountCanUse(String curProvince, String curDate);

}
