package com.xgk.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.AbstractBindHandler;
import org.springframework.boot.context.properties.bind.BindContext;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;

/**
 * @author: xugongkai
 * @created: 2022-03-18 17:49
 */
@Slf4j
public class MyBindHandler extends AbstractBindHandler {

    @Override
    public <T> Bindable<T> onStart(ConfigurationPropertyName name, Bindable<T> target, BindContext context) {
        log.info("绑定开始{}",name);
        return target;
    }

    @Override
    public Object onSuccess(ConfigurationPropertyName name, Bindable<?> target, BindContext context, Object result) {
        log.info("绑定成功{}",target.getValue());
        return result;
    }

    @Override
    public Object onFailure(ConfigurationPropertyName name, Bindable<?> target, BindContext context, Exception error) throws Exception {
        log.info("绑定失败{}",name);
        return "没有找到匹配的属性";
    }

    @Override
    public void onFinish(ConfigurationPropertyName name, Bindable<?> target, BindContext context, Object result) throws Exception {
        log.info("绑定结束{}", name);
    }

}
