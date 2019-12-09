package com.suixingpay.controller;

import com.suixingpay.entity.Manager;
import com.suixingpay.service.LoginService;
import com.suixingpay.utils.GenericResponse;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.PublicKey;
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
