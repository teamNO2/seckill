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
    private RedisTemplate<String, Object> redisTemplate;

    // 锁名
    private static final String ROP_TICKET_LOCK = "tickets:lock";
    // 锁过期时间 30s
    private static final Long ROP_TICKET_LOCK_TIME_OUT = 30000L;
    // 获取锁超时时间 10s
    private static final Long ROP_TICKET_LOCK_GET_TIME_OUT = 10000L;
    // 消息存放key
    private static final String ROP_TICKET_MESSAGE = "ticket:buy:message";
    private int index = 0;
    public  int count = 1;
    private  Scene scene = null;
    private int id  = 0;
    private String state = null;

    @Override
    public Manager Click(Integer managerId) {

        return managerService.selectById(managerId);
    }

    public Callable<GenericResponse> clickRob(Integer sceneId, Integer managerId) throws ParseException {

        if(sceneId!=index){
            scene = sceneService.selectById(sceneId);
            index = sceneId;
            count = scene.getSceneCount();
            System.out.println("apapapapp"+count);
        }

        String lockSign = redisLock.setLock(ROP_TICKET_LOCK, ROP_TICKET_LOCK_TIME_OUT);
        //获取当前时间
        Long oldTimeStamp = System.currentTimeMillis();
        while (true) {
            // 不为空则获取到锁
            if (StringUtils.isNotBlank(lockSign)) {
                if (scene.getSceneCount() <=0) {
                    count = -1;
                    state = "秒杀失败，数量不足";
                    redisLock.releaseLock(ROP_TICKET_LOCK, lockSign);
                    System.out.println("数量不足，释放锁成功");
                    return null;
                }
                if (managerService.selectById(managerId).getManageIsgrab() == 0) {
                    if (scene.getSceneCount() <= 0) {
                        id = managerId;
                        state = "秒杀失败，数量不足";
                    } else {

                        log.info("用户【{}】获取到锁");
                        count--;
                        scene.setSceneCount(count);
                        System.out.println("count数量:" + scene.getSceneCount());
                        log.info("用户【{}】获取成功！");
                        //更改管家领取用户的状态
                        managerService.updateManageByManageId(managerId);
                        //释放锁
                        redisLock.releaseLock(ROP_TICKET_LOCK, lockSign);
                        System.out.println("数量充足，释放锁成功");
                        id = managerId;
                        state = "秒杀成功";

                    }
                }
            }
            Long nowTimeStamp = System.currentTimeMillis();
            // 操作是否超时
            boolean workContinue = (nowTimeStamp - oldTimeStamp) > ROP_TICKET_LOCK_GET_TIME_OUT;
            if (workContinue) {
                log.error("用户操作超时");
                break;
            }
        }
        return  () -> GenericResponse.failed("click999", "秒杀失败");
    }

    public int getId() {
        return id;
    }

    public String getState() {
        return state;
    }
}






