package com.suixingpay.controller;

import com.suixingpay.entity.Manager;
import com.suixingpay.entity.Silentuser;
import com.suixingpay.service.SilentuserService;
import com.suixingpay.utils.GenericResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
     * 分配沉默用户给鑫管家接口
     * @return
     */
    @GetMapping("/distributionSilentuser/{userAddress}")
    @ApiOperation(value = "分配沉默用户",notes = "通过鑫管家是否抢到沉默用户给予分配并修改未被鑫管家抢到的沉默用户改为轮空用户")
    public Callable<GenericResponse> distributionSilentuser(@PathVariable String userAddress) {
        List<Silentuser> silentusers = silentuserService.selectSilentuser(userAddress);
        List<Manager> managers = silentuserService.selectManager();
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