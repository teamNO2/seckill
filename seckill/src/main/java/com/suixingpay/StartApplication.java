package com.suixingpay;

import com.suixingpay.consumer.ClickConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/*
 *@Author 孙克强
 */
@SpringBootApplication
public class StartApplication {
    public static void main(String[] args) {

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
