package com.suixingpay.service.serviceimpl;

import com.suixingpay.service.SendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SendServiceImpl implements SendService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendMessage(int sceneId, int manageId,int count) {
        String sceneIdString = String.valueOf(sceneId);
        String manageIdString = String.valueOf(manageId);
        String countString = String.valueOf(count);
        try {
            rabbitTemplate.convertAndSend("info", sceneIdString+","+manageIdString+","+countString);
        } catch (AmqpException e) {
            log.error("发送用户抢红包进入消息队列异常："+e.getMessage());
            e.printStackTrace();
        }
    }
}
