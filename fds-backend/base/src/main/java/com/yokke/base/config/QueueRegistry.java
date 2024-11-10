//package com.yokke.base.config;
//
//
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import org.springframework.amqp.core.*;
//import org.springframework.beans.factory.config.BeanDefinition;
//import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.type.filter.AnnotationTypeFilter;
//
//import java.util.Set;
//
//@Configuration
//@RequiredArgsConstructor
//public class QueueRegistry {
//
//    private final AmqpAdmin amqpAdmin;
//
//    @PostConstruct
//    public void init() {
//        registerQueues("com.yokke"); // Replace with your base package
//    }
//
//    private void registerQueues(String basePackage) {
//        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(true);
//        scanner.addIncludeFilter(new AnnotationTypeFilter(QueueDefinition.class));
//
//        Set<BeanDefinition> beans = scanner.findCandidateComponents(basePackage);
//
//        for (BeanDefinition bean : beans) {
//            try {
//                Class<?> clazz = Class.forName(bean.getBeanClassName());
//                QueueDefinition queueDefinition = clazz.getAnnotation(QueueDefinition.class);
//
//                if (queueDefinition != null) {
//                    registerQueue(queueDefinition);
//                }
//            } catch (ClassNotFoundException e) {
//                throw new RuntimeException("Failed to register queue", e);
//            }
//        }
//    }
//
//    private void registerQueue(com.yokke.base.config.QueueDefinition definition) {
//        // Create Queue
//        Queue queue = new Queue(definition.queue(), true);
//        amqpAdmin.declareQueue(queue);
//
//        // Create Exchange
//        Exchange exchange = new TopicExchange(definition.exchange());
//        amqpAdmin.declareExchange(exchange);
//
//        // Create Binding
//        Binding binding = BindingBuilder
//                .bind(queue)
//                .to((TopicExchange) exchange)
//                .with(definition.routingKey());
//        amqpAdmin.declareBinding(binding);
//    }
//}