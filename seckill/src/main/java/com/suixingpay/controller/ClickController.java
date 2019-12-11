package com.suixingpay.controller;

import com.suixingpay.config.RedisLock;
import com.suixingpay.entity.Scene;
import com.suixingpay.service.ClickService;
import com.suixingpay.service.ManagerService;
import com.suixingpay.service.SceneService;
import com.suixingpay.service.SendService;
import com.suixingpay.utils.GenericResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
    @Autowired
    private RedisLock redisLock;
    @Autowired
    private ClickService clickService;
    @Autowired
    private SendService sendService;
//
//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;

//    private static int count = 0;
//    // 锁名
//    private static final String ROP_TICKET_LOCK = "tickets:lock";
//    // 锁过期时间 30s
//    private static final Long ROP_TICKET_LOCK_TIME_OUT = 30000L;
//    // 获取锁超时时间 10s
//    private static final Long ROP_TICKET_LOCK_GET_TIME_OUT = 10000L;
//    // 消息存放key
//    private static final String ROP_TICKET_MESSAGE = "ticket:buy:message";


    @GetMapping("/clickRob/{sceneId}/{managerId}")
    public void clickRob(@PathVariable("sceneId") Integer sceneId, @PathVariable Integer managerId) throws ParseException {
        sendService.sendMessage(sceneId, managerId);


    }
}

