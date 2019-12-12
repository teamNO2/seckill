package com.suixingpay;

import com.suixingpay.controller.SceneController;
import com.suixingpay.service.SceneService;
import com.suixingpay.service.serviceimpl.SceneServiceImpl;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import com.suixingpay.consumer.ClickConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.servlet.annotation.WebListener;
import javax.xml.crypto.Data;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/*
 *@Author 孙克强
 */
@WebListener
@SpringBootApplication
public class StartApplication {

    /*public static void timer(){
        Timer timer = new Timer(true);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Date date = new Date();
                String dateStringParse = sdf.format(date);
                SceneService sceneService = new SceneServiceImpl();
                Integer sceneId = sceneService.selectEndTime(dateStringParse);
                System.out.println(sceneId);
            }
        },5000,1000);
    }*/

    public static void main(String[] args) {

        //timer();
        SpringApplication. run(StartApplication.class, args);
    }
    @Bean
    @ConfigurationProperties(prefix = "thread.pool")
    public ThreadPoolTaskExecutor getThreadPool() {
        return new ThreadPoolTaskExecutor();
    }
    @Bean
    public ClickConsumer getClickConsumer(){
        return new ClickConsumer();
    }

}
