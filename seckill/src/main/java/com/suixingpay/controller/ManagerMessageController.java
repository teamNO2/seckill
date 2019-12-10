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
    public Callable<GenericResponse> searchAllUserInfo(@PathVariable("managerId") String managerId1){
        String regex = "^[0-9]*[1-9][0-9]*$";
        if(managerId1.matches(regex)){
            //把鑫管家是否抢到沉默用户状态改变
            Integer managerId  = Integer.valueOf(managerId1);
            List<Silentuser> allManager = managerMessageService.searchAllUserInfo(managerId);
            int i = managerMessageService.updateManageByManageIsgrab(managerId);
            if ((allManager != null)&&(i!=0)) {
                return () -> GenericResponse.success("searchAllUserInfo666", "查询成功", allManager);
            } else {
                return () -> GenericResponse.failed("searchAllUserInfo999", "查询失败");
            }
        }else{
            return () -> GenericResponse.failed("searchAllUserInfo999", "输入的非数字或0");
        }
    }

    /**
     * 张佳鑫
     * 查询鑫管家抢到的沉默用户详细
     */
    @GetMapping("/searchUserInfo/{userId}")
    @ApiOperation(value = "鑫管家查询已抢到沉默用户模块",notes = "根据沉默用户id查询鑫管家抢到的和用户详细")
    public Callable<GenericResponse> searchUserInfo(@PathVariable("userId") String userId1) {
        String regex = "^[0-9]*[1-9][0-9]*$";
        if (userId1.matches(regex)) {
            Integer userId = Integer.valueOf(userId1);
            Silentuser silentuser = managerMessageService.searchUserInfo(userId);
            if (silentuser != null) {
                return () -> GenericResponse.success("searchUserInfo666", "查询成功", silentuser);
            } else {
                return () -> GenericResponse.failed("searchUserInfo999", "查询失败");
            }
        }
        else{
            return () -> GenericResponse.failed("searchAllUserInfo999", "输入的非数字或0");
        }
    }

}
