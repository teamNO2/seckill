package com.suixingpay.consumer;

import com.rabbitmq.client.Channel;
import com.suixingpay.service.ClickService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
    @Component
    public class ClickConsumer implements ChannelAwareMessageListener {


        @Autowired
        private ClickService clickService;

        /**
         * 监听用户抢红包信息
         */
        @Override

        public void onMessage(Message message, Channel channel) throws Exception {
            long tag = message.getMessageProperties().getDeliveryTag();
            try {
                String s = new String(message.getBody(), "UTF-8");
                String[] strings = s.split(",");
                log.info("监听到抢红包用户：" + strings[1]);
                //执行秒杀业务 0：sceneId  1：manageId
                clickService.clickRob(Integer.parseInt(strings[0]),Integer.parseInt(strings[1]));

            } catch (Exception e) {
                e.printStackTrace();
                log.error("用户抢红包发生异常：" + e.getMessage());
                //拒绝接收
                channel.basicReject(tag, false);
            }
        }
    }


