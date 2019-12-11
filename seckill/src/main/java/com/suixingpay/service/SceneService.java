package com.suixingpay.service;

import com.suixingpay.entity.Scene;
import com.suixingpay.entity.Silentuser;

import java.util.List;

public interface SceneService {
    //新增活动
    int insertScene(Scene scene);

    //查询所有活动
    List<Scene> getAllScenes();

    //按id查询活动
    Scene selectById(Integer id);

    int selectSceneNumberById(int id);

    Scene selectSceneByTime(String time);

    //记录每次活动未被认领的沉默用户数量
    int updateUnallocated(int sceneUnallocated,int sceneId);

    /*
     * 张佳鑫
     * 统计用户资源
     */
    List<Silentuser> selectUserResource();
}
