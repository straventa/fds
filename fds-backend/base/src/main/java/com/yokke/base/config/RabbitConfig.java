//package com.yokke.base.config;
//
//import org.springframework.amqp.core.FanoutExchange;
//import org.springframework.amqp.core.TopicExchange;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.amqp.support.converter.MessageConverter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RabbitConfig {
//    public static final String BROADCAST_EXCHANGE = "broadcast_exchange";
//    // Regular Topic Exchange
//    public static final String TOPIC_EXCHANGE = "topic_exchange";
//
//    @Bean
//    public MessageConverter jsonMessageConverter() {
//        return new Jackson2JsonMessageConverter();
//    }// Broadcast (Fanout) Exchange
//
//    @Bean
//    public FanoutExchange broadcastExchange() {
//        return new FanoutExchange(BROADCAST_EXCHANGE);
//    }
//
//    @Bean
//    public TopicExchange topicExchange() {
//        return new TopicExchange(TOPIC_EXCHANGE);
//    }
//}
