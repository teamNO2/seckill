package com.suixingpay.controller;

import com.suixingpay.service.ClickService;
import com.suixingpay.service.ManagerService;
import com.suixingpay.service.SceneService;
import com.suixingpay.utils.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;

/**
 * @ClassName ClickController
 * @Description TODO
 * @Author ShiMengyao
 * @Date 2019 年 12 月 9 日 0009 13:57:11
 * @Version 1.0
 */
@RestController
@RequestMapping("/click")
public class ClickController{

    @Autowired
    private ClickService clickService;
    @Autowired
    private ManagerService managerService;
    @Autowired
    private SceneService sceneService;

    private static int count =0;

    @GetMapping("/clickRob/{sceneId}/{managerId}")
    public Callable<GenericResponse> clickRob(@PathVariable("sceneId") Integer sceneId,@PathVariable Integer managerId){
        String end = "活动结束";
        String noting="沉默用户没有了";
        String joined="您已成功参与本次活动";
        String success="已成功参与本次活动";
        String windows="“恭喜您成功参加此次秒杀活动，待活动结束后，去意向客户查看您的用户信息，并请于 3 内完成拓展";
        if (count == 0) {
            count =sceneService.selectById(sceneId).getSceneCount() ;
        }
        //判断当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String today = sdf.format(new Date());
        //Integer date = Integer.valueOf(String.valueOf(today));
        Long date = Long.valueOf(today);
        //判断活动是否结束
        if (date > Long.valueOf(sceneService.selectById(sceneId).getSceneEndtime())) {
            System.out.println("活动结束");
            return () -> GenericResponse.success("selectById666", "活动结束", end);

        }else if (managerService.selectById(managerId).getManageIsgrab() == 0) {
            //判断鑫管家有没有领到用户，等于0 表示可以抢用户
            if (sceneService.selectById(sceneId).getSceneCount() > 0) {
                //沉默用户大于0 写抢的代码
                count--;
                managerService.updateManageByManageId(managerId);
                System.out.println("已经抢走一个用户，还剩"+count);
                return () -> GenericResponse.success("click666", "你", success);
            } else {
                System.out.println("沉默用户没了");
                return () -> GenericResponse.success("click666", "沉默用户没了", noting);

            }
        } else if (managerService.selectById(managerId).getManageIsgrab() == 1) {
            //表示==1 鑫管家已经领过一次了，
            System.out.println("已经参加过一次活动");
            return () -> GenericResponse.success("click", "已经参加过一次活动", joined);

        }
        return null;
    }

}
