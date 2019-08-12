package com.org.wa.hj.redis;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;

public class Application {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-redis-cluster.xml");
        StringRedisTemplate template = (StringRedisTemplate) context.getBean("stringRedisTemplate");
        System.out.println(template.opsForValue().get("hello"));
    }
}
