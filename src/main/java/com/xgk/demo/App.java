package com.xgk.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author: xugongkai
 * @created: 2022-02-08 17:56
 */
@Slf4j
@SpringBootApplication
public class App implements ApplicationRunner {

    public static void main(String[] args) {
        new SpringApplicationBuilder(App.class).bannerMode(Banner.Mode.OFF).run(args);
    }

    @Override
    public void run(ApplicationArguments args) {
        System.out.println("Hello:)");
    }

}
