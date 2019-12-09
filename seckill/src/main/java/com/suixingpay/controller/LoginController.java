package com.suixingpay.controller;

import com.suixingpay.entity.Manager;
import com.suixingpay.service.LoginService;
import com.suixingpay.utils.GenericResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.Callable;

/*
 *@Author 孙克强
 */
@RestController
@RequestMapping("/login")
@Api("登录模块")
public class LoginController {
    @Autowired
    private LoginService loginService;

    //登录
    @PostMapping("/login")
    @ApiOperation(value = "登录",notes = "根据鑫管家名和密码进行登录")
    public Callable<GenericResponse> login(Manager manager, HttpServletRequest request) {
        Manager loginManager = loginService.selectManagerByNameAndPassword(manager);
        if (loginManager != null) {
            request.getSession().setAttribute(String.valueOf(loginManager.getManageId()), loginManager);
            return () -> GenericResponse.success("login666", "登录成功", loginManager);
        } else {
            return () -> GenericResponse.failed("login999", "登录失败");
        }
    }
}
