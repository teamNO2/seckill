package com.suixingpay.controller;

import com.suixingpay.entity.Manager;
import com.suixingpay.entity.Silentuser;
import com.suixingpay.service.ManagerMessageService;
import com.suixingpay.utils.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.Callable;

/*
 *@Author 张佳鑫
 */
@RestController
@RequestMapping("/managermessage")
public class ManagerMessageController {

    @Autowired
    private ManagerMessageService managerMessageService;

    //查询鑫管家抢到的用户通知
    @GetMapping("/searchAllUserInfo/{managerId}")
    public Callable<GenericResponse> searchAllUserInfo(@PathVariable("managerId") Integer managerId){
        //Manager manager = (Manager) request.getSession().getAttribute(String.valueOf(request.getHeader("manage_Id")));
        //Integer managerId = manager.getManageId();
        List<Silentuser> allManager = managerMessageService.searchAllUserInfo(managerId);
        if (allManager != null) {
            return () -> GenericResponse.success("searchAllUserInfo666", "查询成功", allManager);
        } else {
            return () -> GenericResponse.failed("searchAllUserInfo999", "查询失败");
        }
    }

    //查询抢到的沉默用户
    @GetMapping("/searchUserInfo/{userId}")
    public Callable<GenericResponse> searchUserInfo(@PathVariable("userId") Integer userId){
        Silentuser silentuser = managerMessageService.searchUserInfo(userId);
        if (silentuser != null) {
            return () -> GenericResponse.success("searchUserInfo666", "查询成功", silentuser);
        } else {
            return () -> GenericResponse.failed("searchUserInfo999", "查询失败");
        }
    }

}
