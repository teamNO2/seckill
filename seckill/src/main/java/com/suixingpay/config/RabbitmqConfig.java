//package com.suixingpay.config;
//
//
//import org.springframework.amqp.core.*;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.amqp.support.converter.MessageConverter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RabbitmqConfig {
//    private static final String QUENE_NAME="info";//这个是队列的名称
//    private static final String TASK_EXCHANGE="a.exchange.name";//这个是交换机的名称
//
//    @Bean
//    public RabbitTemplate getRabbitTemplate(ConnectionFactory connectionFactory){
//        RabbitTemplate rabbitTemplate=new RabbitTemplate(connectionFactory);//Create a rabbit template with default strategies and settings.
//        rabbitTemplate.setMessageConverter(jsonConverter());
//        return rabbitTemplate;
//    }
//    @Bean
//    public MessageConverter jsonConverter() {
//        return new Jackson2JsonMessageConverter();//以json的格式传递数据
//    }
//
//
//    public static final String suffix = Long.toString(System.currentTimeMillis());//用时间戳就是为了保证名字的唯一性
//    public final static boolean durable = false;
//    public final static boolean autoDelete = false;
//
//
//    /**
//     * 交换机
//     * @return
//     */
//    @Bean
//    public DirectExchange taskExchange() {
//        /**
//         * durable： true if we are declaring a durable exchange (the exchange will survive a server restart)
//         * 意思就是：如果我们宣布一个持久的交换机，交换器将在服务器重启后继续存在。false就是服务器重启后将不在存在
//         * autoDelete：true if the server should delete the exchange when it is no longer in use
//         * 意思就是：如果长时间不用这个交换机，就删掉这个交换机
//         */
//
//        return new DirectExchange (TASK_EXCHANGE + "." + suffix, true, autoDelete);
//    }
//    /**
//     * 一个队列
//     * @return
//     */
//    @Bean
//    public Queue taskNoticeQueue() {
//        /**
//         * exclusive: true if we are declaring an exclusive queue (the queue will only be used by the declarer's connection)
//         *如果我们声明一个独占队列,队列只会被声明者的连接使用
//         */
//        return new Queue(QUENE_NAME, true, false, autoDelete);
//    }
//
//    /**
//     * 将队列和交换机绑定起来
//     * @param directExchange
//     * @param taskNoticeQueue
//     * @return
//     */
//    @Bean
//    public Binding taskNoticeBinding(DirectExchange directExchange,
//                                     Queue taskNoticeQueue) {
//        return BindingBuilder.bind(taskNoticeQueue).to(directExchange).with("info");
//    }
//
//}
//
