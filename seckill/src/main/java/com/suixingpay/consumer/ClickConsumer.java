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

import javax.servlet.http.HttpServletResponse;
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
        public String onMessage(Message message, Channel channel) throws Exception {
            long tag = message.getMessageProperties().getDeliveryTag();
            try {
                String s = new String(message.getBody(), "UTF-8");
                 s= s.replace("\"", "");
                 String[] strings = s.split(",");
                log.info("监听到秒杀用户：" );
                System.out.println(Integer.parseInt(strings[0]));
                System.out.println(Integer.parseInt(strings[1]));
                //执行秒杀业务 0：sceneId  1：manageId
                clickService.clickRob(Integer.parseInt(strings[0]),Integer.parseInt(strings[1]));

            } catch (Exception e) {
                e.printStackTrace();
                log.error("用户秒杀发生异常：" + e.getMessage());
                //拒绝接收

            }
            return null;
        }
    }


