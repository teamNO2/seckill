package com.suixingpay.service.serviceimpl;

import com.suixingpay.config.RedisLock;
import com.suixingpay.entity.Manager;
import com.suixingpay.entity.Scene;
import com.suixingpay.service.ClickService;
import com.suixingpay.service.ManagerService;
import com.suixingpay.service.SceneService;
import com.suixingpay.utils.GenericResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;

/**
 * @ClassName ClickServiceImpl
 * @Description TODO
 * @Author ShiMengyao
 * @Date 2019 年 12 月 8 日 0008 17:32:25
 * @Version 1.0
 */

@Service
@Slf4j
public class ClickServiceImpl implements ClickService {
    @Autowired
    private ManagerService managerService;
    @Autowired
    private SceneService sceneService;
    @Autowired
    private RedisLock redisLock;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    private static int count = 0;
    // 锁名
    private static final String ROP_TICKET_LOCK = "tickets:lock";
    // 锁过期时间 30s
    private static final Long ROP_TICKET_LOCK_TIME_OUT = 30000L;
    // 获取锁超时时间 10s
    private static final Long ROP_TICKET_LOCK_GET_TIME_OUT = 10000L;
    // 消息存放key
    private static final String ROP_TICKET_MESSAGE = "ticket:buy:message";

    @Override
    public Manager Click(Integer managerId) {


        return managerService.selectById(managerId);
    }

    public Callable<GenericResponse> clickRob(Integer sceneId, Integer managerId)throws ParseException {
        String noting = "今日用户已经被抢完，请留意后续活动";
        String joined = "已经参加活动，请等待结果公布";
        String success = "恭喜您成功参加此次秒杀活动，待活动结束后，去意向客户查看您的用户信息，并请于 3 内完成拓展";
        String nextscenestart = "下一场活动时间为：";
        String next = "您的归属地不在下一场活动开放范围内，请期待后续活动";
        String endPoint = "目前无活动，敬请期待";
        String end = "今天全部活动已经结束";
        String start = "活动还没有开始";
        int sceneIdIndex = 0;//设置sceneId场次值，默认为0；

        //判断是否为新活动
        if (sceneIdIndex !=sceneId){
            sceneIdIndex = sceneId;
            count = sceneService.selectById(sceneId).getSceneCount();
        }
        //判断当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取活动的结束时间
        String s = sceneService.selectById(sceneId).getSceneEndtime();
        System.out.println(s);
        //获取活动的开始时间
        String starttime = sceneService.selectById(sceneId).getSceneStarttime();
        Date date = null;
        date = sdf.parse(s);//数据库中的活动结束时间

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


            if (sceneEndtime.equals(sceneEndtime1)) {
                //下一场的的城市跟鑫管家的所在城市是否在一个地方
                if (managerService.selectById(managerId).getManageProvince().equals(sceneService.selectById((sceneId + 1)).getSceneProvince())) {
                    String nextstarttime = sceneService.selectById((sceneId + 1)).getSceneStarttime();
                    System.out.println(nextstarttime);
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

        } else if ((managerService.selectById(managerId).getManageIsgrab() == 0) && count > 0 && new Date().before(date) && new Date().after(sdf.parse(starttime))) {
            //判断鑫管家有没有领到用户，等于0 表示可以抢用户
            if (sceneService.selectById(sceneId).getSceneCount() > 0) {
                //获取锁
                String lockSign = redisLock.setLock(ROP_TICKET_LOCK, ROP_TICKET_LOCK_TIME_OUT);
                //获取当前时间
                Long oldTimeStamp = System.currentTimeMillis();
                while (true) {
                    // 不为空则获取到锁
                    if (StringUtils.isNotBlank(lockSign)) {
                        //再次判断沉默用户数量是否被抢完
                        if (count <= 0) {
                            return () -> GenericResponse.failed("click999", "失败");
                        }
                        log.info("用户【{}】获取到锁");
                        count--;
                        log.info("用户【{}】抢票成功！票量剩余：【{}】张");
                        //更改管家领取用户的状态
                        managerService.updateManageByManageId(managerId);
                        System.out.println("已经抢走一个用户，还剩" + count);
                        redisLock.releaseLock(ROP_TICKET_LOCK, lockSign);
                        return () -> GenericResponse.success("click666", "成功", success);
                    }
                    Long nowTimeStamp = System.currentTimeMillis();
                    // 操作是否超时
                    boolean workContinue = (nowTimeStamp - oldTimeStamp) > ROP_TICKET_LOCK_GET_TIME_OUT;
                    if (workContinue) {
                        log.error("用户操作超时");
                        break;
                    }
                }

            } else if (managerService.selectById(managerId).getManageIsgrab() == 1) {
                //表示==1 鑫管家已经领过一次了，
                System.out.println("已经参加活动");
                return () -> GenericResponse.success("click", "已经参加过一次活动", joined);
            } else {
                return () -> GenericResponse.success("click", "活动还没有开始", start);
            }
        }


        return null;
    }

    }



