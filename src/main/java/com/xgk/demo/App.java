package com.xgk.demo;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author: xugongkai
 * @created: 2022-02-08 17:56
 */
@Slf4j
@SpringBootApplication
public class App implements ApplicationRunner {

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public static void main(String[] args) {
        new SpringApplicationBuilder(App.class).bannerMode(Banner.Mode.OFF).run(args);
    }

    @Override
    public void run(ApplicationArguments args) {
        RLock userLock = redissonClient.getLock("USER_LOCK");
        try {
            if (userLock.tryLock(1, 5, TimeUnit.SECONDS)) {
                User tom = new User("Tom", 23);
                redisTemplate.opsForValue().set("tom", tom);
                log.info("add user info into redis success...");
            }
            userLock.unlock();
        } catch (InterruptedException e) {
            log.info("Get USER_LOCK failed...");
        }
    }

}
