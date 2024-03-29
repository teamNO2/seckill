package com.suixingpay.service.serviceimpl;

import com.suixingpay.config.RedisLock;
import com.suixingpay.entity.Manager;
import com.suixingpay.entity.Scene;
import com.suixingpay.entity.Silentuser;
import com.suixingpay.service.ClickService;
import com.suixingpay.service.ManagerService;
import com.suixingpay.service.SceneService;
import com.suixingpay.service.SilentuserService;
import com.suixingpay.utils.GenericResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
    private SilentuserService silentuserService;

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
    public  int count = -1000000000;
    private  Scene scene = null;
    private int id  = 0;
    private String state = null;
    private List<Silentuser> silentusers=null;

    @Override
    public Manager Click(Integer managerId) {

        return managerService.selectById(managerId);
    }

    public boolean clickRob(Integer sceneId, Integer managerId) throws ParseException {

        if(sceneId!=index){
            scene = sceneService.selectById(sceneId);
            silentusers = silentuserService.selectSilentuser(managerService.selectById(managerId).getManageProvince());
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
                if (scene.getSceneCount() <0) {
                    count = -1;
                    state = "秒杀失败，数量不足";
                    System.out.println("数量不足，释放锁成功");
                    return false;
                }
                if (managerService.selectById(managerId).getManageIsgrab() == 0) {
                    if (scene.getSceneCount() <= 0) {
                        id = managerId;
                        state = "秒杀失败，数量不足";
                        return false;
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

                        Date userDate = new Date();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String format = simpleDateFormat.format(userDate);
                        log.info("进入修改沉默用户的分配时间，被分配的鑫管家ID，以及当前沉默用户的ID方法");
                        if(silentusers.size()-1>=0) {
                            silentuserService.updateManagerId(managerId, format, silentusers.get(silentusers.size() - 1).getUserId());
                            silentuserService.updateSilentuserIsbyebye(silentusers.get(silentusers.size() - 1).getUserId());
                            silentusers.remove(silentusers.size() - 1);
                            return true;
                        }
                        return false;
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
//        return  () -> GenericResponse.failed("click999", "秒杀失败");
        return false;
    }

    public int getId() {
        return id;
    }

    public String getState() {
        return state;
    }
}






