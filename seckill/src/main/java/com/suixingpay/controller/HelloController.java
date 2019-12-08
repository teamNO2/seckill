package com.suixingpay.controller;

import com.suixingpay.entity.Manager;
import com.suixingpay.service.HelloService;
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
}
