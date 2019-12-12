package com.suixingpay.consumer;

import com.rabbitmq.client.Channel;
import com.suixingpay.service.ClickService;
import com.suixingpay.utils.GenericResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;

@Slf4j
@Component
    public class ClickConsumer   {

        @Autowired
        private ClickService clickService;

        /**
         * 监听用户抢红包信息
         */
        @RabbitListener(queues = "info")
        public Callable<GenericResponse> onMessage(Message message, Channel channel) throws Exception {
            long tag = message.getMessageProperties().getDeliveryTag();
            try {
                String s = new String(message.getBody(), "UTF-8");
                char[] chars = s.toCharArray();
                log.info("监听到抢红包用户：" );
                System.out.println(Integer.parseInt(String.valueOf(chars[1])));
                System.out.println(Integer.parseInt(String.valueOf(chars[3])));
                //执行秒杀业务 0：sceneId  1：manageId
               clickService.clickRob(Integer.parseInt(String.valueOf(chars[1])),Integer.parseInt(String.valueOf(chars[3])));

            } catch (Exception e) {
                e.printStackTrace();
                log.error("用户抢红包发生异常：" + e.getMessage());
                //拒绝接收

            }
            return null;
        }
    }


