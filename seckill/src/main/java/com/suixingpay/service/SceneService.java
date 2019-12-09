package com.suixingpay.service;

import com.suixingpay.entity.Scene;

import java.util.List;

public interface SceneService {
    //新增活动
    int insertScene(Scene scene);

    //查询所有活动
    List<Scene> getAllScenes();

    //按id查询活动
    Scene selectById(Integer id);


    /**
      * @Description:  根据开始时间获取活动详细信息
      * @Param:   String
      * @return:   Scene
      * @Author: lichanghao
      * @Date: 2019/12/9
      */
    Scene selectSceneByTime(String time);



    int selectSceneNumberById(int id);
}
