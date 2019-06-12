package com.org.wa.hj.rabbitmq.service;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Service;

/**
 * 消费者代码
 */
@Service
public class RabbitService implements MessageListener {
    public void onMessage(Message message) {
        System.out.println("消息消费者 = " + message.toString());

    }
}
