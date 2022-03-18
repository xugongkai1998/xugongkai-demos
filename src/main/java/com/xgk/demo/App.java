package com.xgk.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author: xugongkai
 * @created: 2022-02-08 17:56
 */
@Slf4j
@RestController
@SpringBootApplication
public class App implements ApplicationRunner {

    @Autowired
    private DynamicThreadPool dynamicThreadPool;

    public static void main(String[] args) {
        new SpringApplicationBuilder(App.class).bannerMode(Banner.Mode.OFF).run(args);
    }

    @GetMapping("/threadPoolConfig")
    public ThreadPoolProperties threadPoolProperties() {
        ThreadPoolProperties poolProperties = new ThreadPoolProperties();
        poolProperties.setCoreSize(new Random().nextInt(2) + 1);
        poolProperties.setMaxSize(new Random().nextInt(5) + 3);
        poolProperties.setQueueSize(new Random().nextInt(1023) + 1);
        poolProperties.setPoolName("Pool" + UUID.randomUUID().toString());
        log.info("Generator new thread pool config :{}", poolProperties);
        return poolProperties;
    }

    @GetMapping("/submit")
    public String submit() {
        return dynamicThreadPool.execute(() -> {
            String s = UUID.randomUUID().toString();
            System.out.println("任务" + s + "开始");
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务" + s + "结束");
        });
    }

    @Override
    public void run(ApplicationArguments args) {
        System.out.println("Hello:)");
    }

}
