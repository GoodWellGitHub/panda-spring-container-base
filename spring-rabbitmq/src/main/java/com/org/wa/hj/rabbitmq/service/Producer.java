package com.org.wa.hj.rabbitmq.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 消息队列发送者
 */
@Service
public class Producer {

    public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendQueue(String exchangeKey, String queueKey, Object object) {
        amqpTemplate.convertAndSend(exchangeKey, queueKey, object);
    }
}
