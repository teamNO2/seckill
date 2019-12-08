package com.suixingpay.controller;

import com.suixingpay.entity.Scene;
import com.suixingpay.service.SceneService;
import com.suixingpay.utils.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.Callable;

/*
 *@Author 孙克强
 */
@RestController
@RequestMapping("/scene")
public class SceneController {
    @Autowired
    private SceneService sceneService;

    //新增活动
    @PostMapping("/insertScene")
    public Callable<GenericResponse> insertScene(Scene scene) {
        int i = sceneService.insertScene(scene);
        if (i != 0) {
            return () -> GenericResponse.success("insertScene666", "添加成功");
        } else {
            return () -> GenericResponse.failed("insertScene999", "添加失败");
        }
    }

    //查询所有活动
    @GetMapping("/getAllScenes")
    public Callable<GenericResponse> getAllScenes() {
        List<Scene> allScenes = sceneService.getAllScenes();
        if (allScenes != null) {
            return () -> GenericResponse.success("getAllScenes666", "查询成功", allScenes);
        } else {
            return () -> GenericResponse.failed("getAllScenes999", "查询失败");
        }
    }

    //按id查询活动
    @GetMapping("/selectById/{scene_id}")
    public Callable<GenericResponse> selectById(@PathVariable("scene_id") Integer id) {
        Scene scene = sceneService.selectById(id);
        if (scene != null) {
            return () -> GenericResponse.success("selectById666", "查询成功", scene);
        } else {
            return () -> GenericResponse.failed("selectById999", "查询失败");
        }
    }
}
