package com.suixingpay.controller;

import com.suixingpay.entity.Manager;
import com.suixingpay.entity.Silentuser;
import com.suixingpay.service.SilentuserService;
import com.suixingpay.utils.GenericResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * @Author:柴宇航
 */
@RestController
@RequestMapping("/silentuser")
@Api("分配用户模块")
public class SilentuserController {
    @Autowired
    private SilentuserService silentuserService;
    /**
     * @Author:柴宇航
     * @分配沉默用户给鑫管家接口
     * @return
     */
    @PostMapping("/distributionSilentuser")
    @ApiOperation(value = "分配沉默用户",notes = "通过鑫管家是否抢到沉默用户给予分配并修改未被鑫管家抢到的沉默用户改为轮空用户")
    public Callable<GenericResponse> distributionSilentuser(@RequestParam("userProvince") String userProvince) {
        List<Silentuser> silentusers = silentuserService.selectSilentuser(userProvince);
        List<Manager> managers = silentuserService.selectManager(userProvince);
        for(Silentuser s:silentusers){
            if(!managers.isEmpty()){
                Manager manager = managers.get(managers.size() - 1);
                managers.remove(manager);
                int i = silentuserService.updateManagerId(manager.getManageId(), s.getUserId());
            }else{
                silentuserService.updateSilentuserIsbyebye(s.getUserId());
            }
        }
       return () -> GenericResponse.success("distributionSilentuser666", "分配成功");
    }
}