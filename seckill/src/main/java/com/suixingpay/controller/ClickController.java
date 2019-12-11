package com.suixingpay.controller;

import com.suixingpay.entity.Scene;
import com.suixingpay.service.ManagerService;
import com.suixingpay.service.SceneService;
import com.suixingpay.utils.GenericResponse;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ClickController {
    @Autowired
    private ManagerService managerService;
    @Autowired
    private SceneService sceneService;

    private static int count = -1;

    @GetMapping("/clickRob/{sceneId}/{managerId}")
    public Callable<GenericResponse> clickRob(@PathVariable("sceneId") Integer sceneId, @PathVariable Integer managerId) throws ParseException {

        String noting = "今日用户已经被抢完，请留意后续活动";
        String joined = "已经参加活动，请等待结果公布";
        String success = "恭喜您成功参加此次秒杀活动，待活动结束后，去意向客户查看您的用户信息，并请于 3 内完成拓展";
        String nextscenestart = "下一场活动时间为：";
        String next = "您的归属地不在下一场活动开放范围内，请期待后续活动";
        String endPoint = "目前无活动，敬请期待";
        String end = "今天全部活动已经结束";
        String start = "活动还没有开始";
        String without = "没有沉默用户可以被抢购";
        String different = "您的归属地不在本次活动范围内";

        synchronized (this) {
            if (count == -1) {
                count = sceneService.selectById(sceneId).getSceneCount();
                log.info("总数为" + count);
            }
            //判断当前时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //获取活动的结束时间
            String s = sceneService.selectById(sceneId).getSceneEndtime();
            //获取活动的开始时间
            String starttime = sceneService.selectById(sceneId).getSceneStarttime();
            Date date = null;
            date = sdf.parse(s);//数据库中的活动结束时间

        if (managerService.selectById(managerId).getManageProvince().equals(sceneService.selectById(sceneId).getSceneProvince())) {
            //如果鑫管家和活动城市相同
            //判断活动是否结束  当前时间是否在活动结束时间之后
            if (new Date().after(date)) {
                //如果这次活动下面没有活动了 返回目前没有活动
                if (sceneService.selectById(sceneId + 1) == null) {
                    return () -> GenericResponse.success("selectById666", "活动结束", end);
                }
                Scene sc = sceneService.selectById(sceneId);
                String sceneEndtime = sc.getSceneEndtime().substring(0, 10);
                Scene sc1 = sceneService.selectById((sceneId + 1));
                String sceneEndtime1 = sc1.getSceneEndtime().substring(0, 10);
                //当前结束时间和下一场的活动时间是否在同一天。
                if (sceneEndtime.equals(sceneEndtime1)) {
                    //下一场的的城市跟鑫管家的所在城市是否在一个地方
                    if (managerService.selectById(managerId).getManageProvince().equals(sceneService.selectById((sceneId + 1)).getSceneProvince())) {
                        String nextstarttime = sceneService.selectById((sceneId + 1)).getSceneStarttime();
                        log.info("下一场活动时间为："+nextstarttime);
                        //下一场活动时间为：
                        return () -> GenericResponse.success("selectById666", "活动结束", nextscenestart + nextstarttime + "敬请期待");
                    } else {
                        //您的归属地不在下一场活动开放范围内，请期待后续活动
                        return () -> GenericResponse.success("selectById666", "活动结束", next);
                    }
                } else {
                    //目前无活动敬请期待
                    return () -> GenericResponse.success("selectById666", "活动结束", endPoint);
                }

            } else if (managerService.selectById(managerId).getManageIsgrab() == 0 && count > 0 && new Date().before(date) && new Date().after(sdf.parse(starttime))) {
                    if (sceneService.selectById(sceneId).getSceneCount() > 0) {
                        //沉默用户大于0 写抢的代码
                        synchronized (this) {
                            count--;
                            if (count < 0) {
                                return () -> GenericResponse.success("click666", "失败", noting);
                            }
                            //更改管家领取用户的状态
                            managerService.updateManageByManageId(managerId);
                            log.info(managerId+"已经抢走一个用户，还剩" + count);
                            return () -> GenericResponse.success("click666", "成功", success);
                        }
                    } else {
                        //最开始就没有沉默用户
                        return () -> GenericResponse.success("click666", "失败", without);
                    }
            } else if (managerService.selectById(managerId).getManageIsgrab() == 1) {
                //表示==1 鑫管家已经领过一次了，
                log.info(managerId+"已经参加活动");
                return () -> GenericResponse.success("click", "已经参加过一次活动", joined);
            } else {
                return () -> GenericResponse.success("click", "活动还没有开始", start);
            }
        } else {
            return () ->  GenericResponse.success("click", "归属地不在活动范围内", different);
        }
        }
    }
}

