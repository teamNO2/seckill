package com.suixingpay.controller;

import com.suixingpay.entity.Scene;
import com.suixingpay.service.ManagerService;
import com.suixingpay.service.SceneService;
import com.suixingpay.utils.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
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
public class ClickController {
    @Autowired
    private ManagerService managerService;
    @Autowired
    private SceneService sceneService;

    private static int count = -1;

    @GetMapping("/clickRob/{sceneId}/{managerId}")
    public Callable<GenericResponse> clickRob(@PathVariable("sceneId") Integer sceneId, @PathVariable Integer managerId) {
        String end = "活动结束";
        String noting = "今日用户已经被抢完，请留意后续活动";
        String joined = "已经参加活动，请等待结果公布";
        String success = "恭喜您成功参加此次秒杀活动，待活动结束后，去意向客户查看您的用户信息，并请于 3 内完成拓展";
        String nextscenestart = "下一场活动时间为,";
        String next = "您的归属地不在下一场活动开放范围内，请期待后续活动";
        String endPoint = "目前无活动，敬请期待";
        if (count == -1) {
            count = sceneService.selectById(sceneId).getSceneCount();
        }
        //判断当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String today = sdf.format(new Date());
        String s = sceneService.selectById(sceneId).getSceneEndtime();
        Date date = null;
        Date day = null;
        try {
            date = sdf.parse(s);
            day = sdf.parse(today);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //判断活动是否结束
        if (day.after(date)) {
            Scene sc=sceneService.selectById(sceneId);
            String sceneEndtime = sc.getSceneEndtime().substring(0,10);
            Scene sc1=sceneService.selectById((sceneId + 1));
            String sceneEndtime1=sc1.getSceneEndtime().substring(0,10);
            if (sceneEndtime.equals(sceneEndtime1)){
                if (managerService.selectById(managerId).getManageProvince().equals(sceneService.selectById((sceneId + 1)).getSceneProvince())) {
                    String nextstarttime = sceneService.selectById(sceneId + 1).getSceneStarttime();
                    return () -> GenericResponse.success("selectById666", "活动结束", nextscenestart + nextstarttime + "敬请期待");
                } else {
                    return () -> GenericResponse.success("selectById666", "活动结束", next);
                }
            } else {
                return () -> GenericResponse.success("selectById666", "活动结束", endPoint);
            }

        } else if (managerService.selectById(managerId).getManageIsgrab() == 0) {
            //判断鑫管家有没有领到用户，等于0 表示可以抢用户
            if (sceneService.selectById(sceneId).getSceneCount() > 0) {
                //沉默用户大于0 写抢的代码
                count--;
                if(count<0){
                    return () -> GenericResponse.success("click666", "失败", noting);
                }
                managerService.updateManageByManageId(managerId);
                System.out.println("已经抢走一个用户，还剩" + count);
                return () -> GenericResponse.success("click666", "成功", success);
            }
        } else if (managerService.selectById(managerId).getManageIsgrab() == 1) {
            //表示==1 鑫管家已经领过一次了，
            System.out.println("已经参加活动");
            return () -> GenericResponse.success("click", "已经参加过一次活动", joined);
        }
        return null;
    }
}
