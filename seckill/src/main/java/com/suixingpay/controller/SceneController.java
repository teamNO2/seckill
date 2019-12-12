package com.suixingpay.controller;

import com.suixingpay.entity.Scene;
import com.suixingpay.entity.Silentuser;
import com.suixingpay.service.SceneService;
import com.suixingpay.utils.GenericResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RestController
@RequestMapping("/scene")
@Api("秒杀活动配置模块")
public class SceneController {
    @Autowired
    private SceneService sceneService;

    //新增活动---孙克强
    @PostMapping("/insertScene")
    @ApiOperation(value = "添加活动配置", notes = "添加秒杀活动配置")
    public Callable<GenericResponse> insertScene(Scene scene) throws ParseException {
        synchronized (this) {
            log.info("进入添加活动接口");
            log.info("前台传入的参数" + scene);
            //时间格式
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //当前系统时间--Date类型
            Date curDate = new Date();
            //当前系统时间--String类型
            String curDateStr = format.format(curDate);

            //可参与用户数的校验------------>

            log.info("校验可配置的沉默用户数量");
            //前台传入的省份
            String sceneProvince = scene.getSceneProvince();
            //前台传入的沉默用户数
            Integer sceneCount = scene.getSceneCount();
            //查询可用的沉默用户数量
            int countCanUse = sceneService.findCountCanUse(sceneProvince, curDateStr);
            if (sceneCount > countCanUse) {
                log.info("添加失败，前台传入的沉默用户数大于当前可用的沉默用户数，退出添加活动接口");
                return () -> GenericResponse.failed("insertScene999", "添加失败，当前输入的沉默用户数大于系统中可用的沉默用户数");
            }
            log.info("前台传入符合沉默用户数");

            //时间的校验------------->

            log.info("校验配置活动的时间");
            //新添加活动的开始时间
            Date newstarttime = format.parse(scene.getSceneStarttime());
            //新添加活动的结束时间
            Date newendtime = format.parse(scene.getSceneEndtime());
            //新添加活动时间如果比当前时间小
            if (newstarttime.compareTo(curDate) == -1) {
                log.info("添加失败，开始活动时间小于当前时间，退出添加活动接口");
                return () -> GenericResponse.failed("insertScene999", "添加失败，开始活动时间不能小于当前时间");
            }
            log.info("查询所有活动信息");
            List<Scene> allScenes = sceneService.getAllScenes();
            //没有已存在活动,可以添加
            if (allScenes == null || allScenes.isEmpty()) {
                sceneService.insertScene(scene);
                log.info("添加成功，退出添加活动接口");
                return () -> GenericResponse.success("insertScene666", "添加成功");
            }
            for (Scene sc : allScenes) {
                //已存在活动的开始时间
                Date oldStartTime = format.parse(sc.getSceneStarttime());
                //已存在活动的结束时间
                Date oldEndTime = format.parse(sc.getSceneEndtime());

                //有时间交集，不可以添加
                if (!((newstarttime.compareTo(newendtime) == -1 && newendtime.compareTo(oldStartTime) == -1 || newstarttime.compareTo(newendtime) == -1 && newstarttime.compareTo(oldEndTime) == 1))) {
                    log.info("添加失败，时间有冲突，退出添加活动接口");
                    return () -> GenericResponse.failed("insertScene999", "添加失败，时间有冲突");
                }
            }
            //没有时间冲突，可以添加
            int i = sceneService.insertScene(scene);
            if (i != 0) {
                log.info("添加成功，退出添加活动接口");
                return () -> GenericResponse.success("insertScene666", "添加成功");
            } else {
                log.info("添加失败，退出添加活动接口");
                return () -> GenericResponse.failed("insertScene999", "添加失败");
            }
        }
    }

    //查询所有活动---孙克强
    @GetMapping("/getAllScenes")
    @ApiOperation(value = "查询活动列表", notes = "查询所有秒杀活动配置信息")
    public Callable<GenericResponse> getAllScenes() {
        log.info("进入查询所有活动接口");
        List<Scene> allScenes = sceneService.getAllScenes();
        log.info("正在查询所有活动");
        if (allScenes != null) {
            log.info("查询成功，退出查询所有活动接口");
            return () -> GenericResponse.success("getAllScenes666", "查询成功", allScenes);
        } else {
            log.info("查询为空，退出查询所有活动接口");
            return () -> GenericResponse.failed("getAllScenes999", "查询失败");
        }
    }

    //按id查询活动---孙克强
    @GetMapping("/selectById/{scene_id}")
    @ApiOperation(value = "按id查询活动", notes = "按id查询活动")
    public Callable<GenericResponse> selectById(@PathVariable("scene_id") Integer id) {
        log.info("进入按id查询活动接口");
        Scene scene = sceneService.selectById(id);
        log.info("正在查询");
        if (scene != null) {
            log.info("查询成功，退出按id查询活动接口");
            return () -> GenericResponse.success("selectById666", "查询成功", scene);
        } else {
            log.info("查询为空，退出按id查询活动接口");
            return () -> GenericResponse.failed("selectById999", "查询失败");
        }
    }

    //查询所有未开始的活动---孙克强
    @GetMapping("/getNoStartScenes/{curTime}")
    @ApiOperation(value = "查询未开始活动和正在进行活动列表", notes = "查询所有时间小于当前时间的秒杀活动配置信息以及所有正在进行的活动信息")
    public Callable<GenericResponse> getNoStartScenes(@PathVariable("curTime") String curTime) throws ParseException {
        log.info("进入查询未开始活动和正在进行活动列表接口");
        List<Scene> allScenes = sceneService.getAllScenes();
        List<Scene> noStartScenes = new ArrayList<>();
        //时间格式
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //前台时间
        Date date1 = format.parse(curTime);
        for (Scene sc : allScenes) {
            String sceneStarttime = sc.getSceneStarttime();
            String sceneEndtime = sc.getSceneEndtime();
            //活动开始时间
            Date date2 = format.parse(sceneStarttime);
            //活动结束时间
            Date date3 = format.parse(sceneEndtime);
            //判断所有活动时间大于前台传来的时间 || 活动已经开始且未结束
            if (date2.compareTo(date1) == 1 || date2.compareTo(date1) == -1 && date3.compareTo(date1) == 1) {
                noStartScenes.add(sc);
            }
        }
        if (allScenes != null) {
            log.info("查询成功，退出查询未开始活动和正在进行活动列表接口");
            Date date = new Date();
            String str = format.format(date);
            return () -> GenericResponse.success(str, "查询成功", noStartScenes);
        } else {
            log.info("查询为空，退出查询未开始活动和正在进行活动列表接口");
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

    /*
     * 张佳鑫
     * 统计用户资源
     */
    @GetMapping("/userResource")
    @ApiOperation(value = "用户资源统计", notes = "按省份统计沉默用户数和可参加用户")
    public Callable<GenericResponse> selectUserResource() {
        List<Silentuser> silentusers = sceneService.selectUserResource();
        if (silentusers != null) {
            return () -> GenericResponse.success("selectUserResource666", "查询成功", silentusers);
        } else {
            return () -> GenericResponse.failed("selectUserResource999", "查询失败");
        }
    }
}
