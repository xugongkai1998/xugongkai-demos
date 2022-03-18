package com.xgk.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.logging.DeferredLog;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: xugongkai
 * @created: 2022-03-18 18:16
 */
public class EnvPropertyPostInjectProcessor implements EnvironmentPostProcessor, ApplicationListener<ApplicationEvent> {
    private static final DeferredLog log = new DeferredLog();
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        if (!App.useEnvPostProcessor) {
            log.info(">>> EnvPropertyPostInjectProcessor is skip");
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("user.info.name", "baby");
        log.info(String.format(">>>>>Inject UnOverride property: %s", map));
        MapPropertySource mapPropertySource = new MapPropertySource("UnOverrideUserInfo", map);
        environment.getPropertySources().addFirst(mapPropertySource);
    }
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        log.replayTo(EnvPropertyPostInjectProcessor.class);
    }
}
