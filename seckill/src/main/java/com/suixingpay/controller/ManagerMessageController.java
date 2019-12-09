package com.suixingpay.controller;

import com.suixingpay.entity.Manager;
import com.suixingpay.entity.Silentuser;
import com.suixingpay.service.ManagerMessageService;
import com.suixingpay.utils.GenericResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api("鑫管家查询已抢到用户模块")
public class ManagerMessageController {

    @Autowired
    private ManagerMessageService managerMessageService;

    /**
     * 张佳鑫
     * 查询鑫管家抢到的所有用户通知
     */
    @GetMapping("/searchAllUserInfo/{managerId}")
    @ApiOperation(value = "鑫管家查询已抢到所有用户模块",notes = "根据鑫管家id查询鑫管家抢到的所有用户通知")
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

    /**
     * 张佳鑫
     * 查询鑫管家抢到的沉默用户详细
     */
    @GetMapping("/searchUserInfo/{userId}")
    @ApiOperation(value = "鑫管家查询已抢到沉默用户模块",notes = "根据沉默用户id查询鑫管家抢到的和用户详细")
    public Callable<GenericResponse> searchUserInfo(@PathVariable("userId") Integer userId){
        Silentuser silentuser = managerMessageService.searchUserInfo(userId);
        if (silentuser != null) {

            return () -> GenericResponse.success("searchUserInfo666", "查询成功", silentuser);
        } else {
            return () -> GenericResponse.failed("searchUserInfo999", "查询失败");
        }
    }

}
