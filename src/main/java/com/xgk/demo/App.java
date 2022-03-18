package com.xgk.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.List;
import java.util.Map;

/**
 * @author: xugongkai
 * @created: 2022-02-08 17:56
 */
@Slf4j
@Configuration
@ConfigurationProperties
@SpringBootApplication
public class App implements ApplicationRunner {

    // 如果要验证EnvPostProcessor处理的场景，置为true.
    // 默认情况下，不要打开。
    public static final boolean useEnvPostProcessor = true;

    @Autowired
    private ApplicationContext ctx;
    @Autowired
    private Environment environment;

    public static void main(String[] args) {
        new SpringApplicationBuilder(App.class).bannerMode(Banner.Mode.OFF).run(args);
    }

    @Override
    public void run(ApplicationArguments args) {

        final Binder binder = Binder.get(ctx.getEnvironment());

        //绑定对象
        final String bindToObjPropertyPrefix = "user.info";//属性前缀
        BindResult<UserInfo> bindResult = binder.bind(bindToObjPropertyPrefix, Bindable.of(UserInfo.class));

        if (Boolean.TRUE.equals(bindResult.isBound())) {
            UserInfo userInfo = bindResult.get();
            log.info("bind property to UserInfo success:{}", userInfo);
        }else {
            //属性前缀不存在时
            log.info("cannot bind property to UserInfo");
        }

        //绑定map
        final String bindToMapPropertyPrefix = "bind-to-map";
        BindResult<Map<String, UserInfo>> bindResult1 = binder.bind(bindToMapPropertyPrefix, Bindable.mapOf(String.class, UserInfo.class));
        if (bindResult1.isBound()) {
            log.info("bind property to Map success:{}", bindResult1.get());
        }

        //绑定list, set
        final String bindToListPropertyPrefix = "bind-to-list";
        BindResult<List<UserInfo>> bindResult2 = binder.bind(bindToListPropertyPrefix, Bindable.listOf(UserInfo.class));
        if (bindResult2.isBound()) {
            log.info("bind property to list success:{}", bindResult2.get());
        }


    }

}
