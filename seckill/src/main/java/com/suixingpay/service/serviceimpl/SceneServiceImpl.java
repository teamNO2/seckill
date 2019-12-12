package com.suixingpay.service.serviceimpl;

import com.suixingpay.entity.Scene;
import com.suixingpay.entity.Silentuser;
import com.suixingpay.repository.SceneRepository;
import com.suixingpay.repository.SilentuserRepository;
import com.suixingpay.service.SceneService;
import io.swagger.models.auth.In;
import com.suixingpay.utils.GenericResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

/*
 *@Author sunkeqiang
 */
@Service
@Slf4j
public class SceneServiceImpl implements SceneService {
    @Autowired
    private SceneRepository sceneRepository;

    @Autowired
    private SilentuserRepository silentuserRepository;

    @Override
    public int insertScene(Scene scene) {
        return sceneRepository.insertSelective(scene);
    }

    @Override
    public List<Scene> getAllScenes() {
        return sceneRepository.getAllScenes();
    }

    @Override
    public Scene selectById(Integer id) {
        return sceneRepository.selectById(String.valueOf(id));
    }



    /**
     * @Description: 根据时间查找活动
     * @Param: [time]
     * @return: com.suixingpay.entity.Scene
     * @Author: lichanghao
     * @Date: 2019/12/9
     */
    @Override
    public Scene selectSceneByTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date needTime = null;
        Date startTime = null;
        Date endTime = null;
        int index = 0;//给定一个查询标识
        try {
            needTime = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Scene> scenes = this.getAllScenes();
        for (Scene scene : scenes) {
            try {
                startTime = format.parse(scene.getSceneStarttime());
                endTime = format.parse(scene.getSceneEndtime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //判断是否获取数据成功
            if (needTime == null || startTime == null || endTime == null) {
                log.error("数据库获取数据异常");
            } else {
                //判断是否在活动时间内
                if (needTime.before(endTime) && needTime.after(startTime)) {
                    index = 1;
                    log.info("查询成功");
                    return scene;
                }
                if (!(needTime.before(startTime)) && (!needTime.after(startTime))) {
                    index = 1;
                    log.info("查询成功");
                    return scene;
                }
            }
        }
        //判断标识是否改变
        if (index == 0) {
            log.debug("您查询的时间没有活动进行");
        }
        return null;
    }


    /**
     * @Description: 查询某一场的沉默用户总数
     * @Param: [id]
     * @return: int
     * @Author: lichanghao
     * @Date: 2019/12/9
     */
    @Override
    public int selectSceneNumberById(int id) {
        Scene scene = this.selectById(id);
        int index = scene.getSceneCount();
        return index;
    }


    /**
     * @Description: 记录每次活动未被认领的沉默用户数量
     * @Param: [scene_unallocated, scene_id]
     * @return: int
     * @Author: lichanghao
     * @Date: 2019/12/10
     */
    @Override
    public int updateUnallocated(int sceneUnallocated, int sceneId) {
        return sceneRepository.updateUnallocated(sceneUnallocated, sceneId);
    }

    /*
     * 张佳鑫
     * 统计用户资源
     */
    @Override
    public List<Silentuser> selectUserResource(String curTime) {
        return silentuserRepository.selectUserResource(curTime);
    }

    @Override
    public Integer selectEndTime(String sceneEndTime) {
        return sceneRepository.selectEndTime(sceneEndTime);
    }

    /*
     * 李常昊
     * 未开始
     */
    @Override
    public List<Scene> judgeStateByTime1() throws Exception {
        List<Scene> scenes = sceneRepository.getAllScenes();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Scene> scenes1 = new ArrayList<>();
        for (Scene scene : scenes) {
            Date startTime = format.parse(scene.getSceneStarttime());
            if (startTime.after(new Date())) {
                scenes1.add(scene);
            }
        }
        return scenes1;
    }
    /*
     * 李常昊
     * 正在进行
     */

    @Override
    public List<Scene> judgeStateByTime2() throws Exception {
        List<Scene> scenes = sceneRepository.getAllScenes();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Scene> scenes1 = new ArrayList<>();
        for (Scene scene : scenes) {
            Date startTime = format.parse(scene.getSceneStarttime());
            Date endTime = format.parse(scene.getSceneEndtime());
            if (startTime.before(new Date()) && endTime.after(new Date())) {
                scenes1.add(scene);
            }
        }
        return scenes1;
    }

    /*
     * 李常昊
     * 已结束
     */
    @Override
    public List<Scene> judgeStateByTime3() throws Exception {
        List<Scene> scenes = sceneRepository.getAllScenes();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Scene> scenes1 = new ArrayList<>();
        for (Scene scene : scenes) {
            Date startTime = format.parse(scene.getSceneStarttime());
            Date endTime = format.parse(scene.getSceneEndtime());
            if (endTime.before(new Date())) {
                scenes1.add(scene);
            }
        }
        return scenes1;
    }
}


