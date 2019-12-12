package com.suixingpay.controller;

import com.suixingpay.entity.Manager;
import com.suixingpay.service.HelloService;
import com.suixingpay.service.SceneService;
import com.suixingpay.utils.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

/*
 *@Author sunkeqiang
 */
@RestController
@RequestMapping("/hello")
public class HelloController {
    @Autowired
    private HelloService helloService;
    @Autowired
    private SceneService sceneService;

    //按id查询管家
    @GetMapping("/selectById/{id}")
    public Callable<GenericResponse> selectById(@PathVariable("id") Integer id) {
        Manager manager = helloService.selectById(id);
        if (manager != null) {
            return () -> GenericResponse.success("selectById666", "查询成功", manager);
        } else {
            return () -> GenericResponse.failed("selectById999", "查询失败");
        }
    }


    //测试使用1
    @GetMapping("/findCountCanUse")
    public Callable<GenericResponse> findCountCanUse() {
        int countCanUse = sceneService.findCountCanUse("广东省", "2019-12-12 11:03:00");
        return () -> GenericResponse.success("findCountCanUse", "查询成功", countCanUse);
    }
}
