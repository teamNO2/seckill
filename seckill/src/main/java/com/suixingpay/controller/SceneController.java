package com.suixingpay.controller;

import com.suixingpay.entity.Scene;
import com.suixingpay.service.SceneService;
import com.suixingpay.utils.GenericResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

/*
 *@Author 孙克强
 */
@RestController
@RequestMapping("/scene")
@Api("秒杀活动配置模块")
public class SceneController {
    @Autowired
    private SceneService sceneService;

    //新增活动---孙克强
    @PostMapping("/insertScene")
    @ApiOperation(value = "添加活动配置", notes = "添加秒杀活动配置")
    public Callable<GenericResponse> insertScene(Scene scene) {
        int i = sceneService.insertScene(scene);
        if (i != 0) {
            return () -> GenericResponse.success("insertScene666", "添加成功");
        } else {
            return () -> GenericResponse.failed("insertScene999", "添加失败");
        }
    }

    //查询所有活动---孙克强
    @GetMapping("/getAllScenes")
    @ApiOperation(value = "查询活动列表", notes = "查询所有秒杀活动配置信息")
    public Callable<GenericResponse> getAllScenes() {
        List<Scene> allScenes = sceneService.getAllScenes();
        if (allScenes != null) {
            return () -> GenericResponse.success("getAllScenes666", "查询成功", allScenes);
        } else {
            return () -> GenericResponse.failed("getAllScenes999", "查询失败");
        }
    }

    //按id查询活动---孙克强
    @GetMapping("/selectById/{scene_id}")
    @ApiOperation(value = "按id查询活动", notes = "按id查询活动")
    public Callable<GenericResponse> selectById(@PathVariable("scene_id") Integer id) {
        Scene scene = sceneService.selectById(id);
        if (scene != null) {
            return () -> GenericResponse.success("selectById666", "查询成功", scene);
        } else {
            return () -> GenericResponse.failed("selectById999", "查询失败");
        }
    }

    //查询所有未开始的活动---孙克强
    @GetMapping("/getNoStartScenes/{curTime}")
    @ApiOperation(value = "查询未开始活动列表", notes = "查询所有时间小于当前时间的秒杀活动配置信息")
    public Callable<GenericResponse> getNoStartScenes(@PathVariable("curTime") String curTime) throws ParseException {
        List<Scene> allScenes = sceneService.getAllScenes();
        List<Scene> noStartScenes = new ArrayList<>();
        //时间格式
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //前台时间
        Date date1 = format.parse(curTime);
        for (Scene sc : allScenes) {
            String sceneStarttime = sc.getSceneStarttime();
            Date date2 = format.parse(sceneStarttime);
            //判断所有活动时间大于前台传来的时间
            if (date2.compareTo(date1) == 1) {
                noStartScenes.add(sc);
            }
        }
        if (allScenes != null) {
            Date date = new Date();
            String str = format.format(date);
            return () -> GenericResponse.success(str, "查询成功", noStartScenes);
        } else {
            return () -> GenericResponse.failed("getNoStartScenes999", "查询失败");
        }
    }


//    /**
//     * @Description: 根据时间
//     * @Param: [time]
//     * @return: java.util.concurrent.Callable<com.suixingpay.utils.GenericResponse>
//     * @Author: lichanghao
//     * @Date: 2019/12/9
//     */
//    @GetMapping("/selectSceneByTime/{time}")
//    @ApiOperation(value = "按id查询活动",notes = "按id查询活动")
//    public Callable<GenericResponse> selectSceneByTime(@PathVariable("time") String time) {
//        Scene scene = sceneService.selectSceneByTime(time);
//        if (scene != null) {
//            return () -> GenericResponse.success("selectById666", "查询成功", scene);
//        } else {
//            return () -> GenericResponse.failed("selectById999", "查询失败");
//        }
//    }

    /**
     * @Description: 根据时间查询活动
     * @Param: [time]
     * @return: java.util.concurrent.Callable<com.suixingpay.utils.GenericResponse>
     * @Author: lichanghao
     * @Date: 2019/12/9
     */
    @PostMapping("/selectSceneByTime")
    @ApiOperation(value = "按time查询活动", notes = "按time查询活动")
    public Callable<GenericResponse> selectSceneByTime(@RequestParam("time") String time) {
        Scene scene = sceneService.selectSceneByTime(time);
        if (scene != null) {
            return () -> GenericResponse.success("selectById666", "查询成功", scene);
        } else {
            return () -> GenericResponse.failed("selectById999", "查询失败");
        }
    }
}
