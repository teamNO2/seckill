package com.suixingpay.controller;

import com.suixingpay.entity.Manager;
import com.suixingpay.entity.Scene;
import com.suixingpay.entity.Silentuser;
import com.suixingpay.service.SceneService;
import com.suixingpay.service.SilentuserService;
import com.suixingpay.utils.GenericResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

/**
 * 柴宇航
 */
@RestController
@RequestMapping("/silentuser")
@Api("分配用户模块")
public class SilentuserController {
    @Autowired
    private SilentuserService silentuserService;

    /**
     * 柴宇航
     * 根据ManageerId不为空，查询出所有被鑫管家抢到的沉默用户
     * @return
     */
    @PostMapping("/selectSilentuser")
    @ApiOperation(value = "查询所有被抢到沉默用户",notes = "通过MessageId查询出被鑫管家抢到的沉默用户")
    public Callable<GenericResponse> selectSilentuser() {
        Silentuser silentuser = silentuserService.selectSilentuser();
        if (silentuser != null) {
            return () -> GenericResponse.success("selectSilentuser666", "查询成功");
        } else {
            return () -> GenericResponse.failed("selectSilentuser999", "查询失败");
        }

    }
}
