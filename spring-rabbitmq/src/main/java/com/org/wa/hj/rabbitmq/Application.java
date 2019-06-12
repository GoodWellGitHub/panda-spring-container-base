package com.org.wa.hj.rabbitmq;

import com.org.wa.hj.rabbitmq.service.Producer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class Application {
    //@Value("#{appConfig['mq.queue']}")


    public static void main(String[] args) {
        String queueId = "spring_queue";
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:/applicationContext.xml");
        Producer producer = (Producer) context.getBean("producer");
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("data", "hello rabbitmq");
            // 注意：第二个属性是 Queue 与 交换机绑定的路由
            producer.sendQueue(queueId + "_exchange", queueId + "_patt", map);
            System.out.println("消息发送结束。。。。。。。");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
