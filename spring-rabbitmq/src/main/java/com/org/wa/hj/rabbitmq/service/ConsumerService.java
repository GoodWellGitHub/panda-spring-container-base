package com.org.wa.hj.rabbitmq.service;

import java.util.Map;

public class ConsumerService {
    public void getMessage(Map<String,Object> message){
        System.out.println("消费者 ： "+message);
    }
}
