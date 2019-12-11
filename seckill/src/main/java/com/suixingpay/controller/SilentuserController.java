package com.suixingpay.controller;

import com.suixingpay.entity.Manager;
import com.suixingpay.entity.Silentuser;
import com.suixingpay.service.SceneService;
import com.suixingpay.service.SilentuserService;
import com.suixingpay.utils.GenericResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @Author:柴宇航
 */
@RestController
@RequestMapping("/silentuser")
@Api("分配用户模块")
@Slf4j
public class SilentuserController {
    @Autowired
    private SilentuserService silentuserService;
    @Autowired
    private SceneService sceneService;
    /**
     * @Author:柴宇航
     * @分配沉默用户给鑫管家接口
     * @return
     */
    @PostMapping("/distributionSilentuser")
    @ApiOperation(value = "分配沉默用户",notes = "通过鑫管家是否抢到沉默用户给予分配并修改未被鑫管家抢到的沉默用户改为轮空用户")
    public Callable<GenericResponse> distributionSilentuser(@RequestParam("userProvince") String userProvince,@RequestParam("sceneId")int id) {
        log.info("进入分配沉默用户的接口" );
        int index =0;//记录未被分配的沉默用户
        log.info("进入查询符合当前城市的沉默用户方法");
        List<Silentuser> silentusers = silentuserService.selectSilentuser(userProvince);
        log.info("进入查询符合当前城市的鑫管家");
        List<Manager> managers = silentuserService.selectManager(userProvince);
        for(Silentuser s:silentusers){
            if(!managers.isEmpty()){
                Manager manager = managers.get(managers.size() - 1);
                managers.remove(manager);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date=new Date();
                String dateStr = simpleDateFormat.format(date);
                log.info("进入修改沉默用户的分配时间，被分配的鑫管家ID，以及当前沉默用户的ID方法");
                silentuserService.updateManagerId(manager.getManageId(),dateStr,s.getUserId());
            }else{
                log.info("进去修改沉默用户是否被轮空的方法");
                silentuserService.updateSilentuserIsbyebye(s.getUserId());
                index++;//记录+1
            }
            sceneService.updateUnallocated(index, id);//记录该id活动所对应的未分配沉默用户的数量
        }
        log.info("沉默用户分配成功");
       return () -> GenericResponse.success("distributionSilentuser666", "分配成功");
    }
}