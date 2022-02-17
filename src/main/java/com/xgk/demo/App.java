package com.xgk.demo;

import com.xgk.demo.mapper.StudentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private StudentMapper studentMapper;

    public static void main(String[] args) {
        new SpringApplicationBuilder(App.class).bannerMode(Banner.Mode.OFF).run(args);
    }

    @Override
    public void run(ApplicationArguments args) {
        System.out.printf("StudentCount:%s\n", studentMapper.countStudent());
    }

}
