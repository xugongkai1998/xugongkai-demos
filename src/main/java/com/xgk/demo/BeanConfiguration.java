package com.xgk.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.*;
import org.springframework.http.converter.feed.AtomFeedHttpMessageConverter;
import org.springframework.http.converter.feed.RssChannelHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.util.ClassUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: xugongkai
 * @created: 2022-03-18 14:17
 */
@Configuration
public class BeanConfiguration {

    private static final boolean romePresent =
            ClassUtils.isPresent("com.rometools.rome.feed.WireFeed", RestTemplate.class.getClassLoader());
    private static final boolean jaxb2Present =
            ClassUtils.isPresent("javax.xml.bind.Binder", RestTemplate.class.getClassLoader());
    private static final boolean jackson2Present =
            ClassUtils.isPresent("com.fasterxml.jackson.databind.ObjectMapper", RestTemplate.class.getClassLoader()) &&
                    ClassUtils.isPresent("com.fasterxml.jackson.core.JsonGenerator", RestTemplate.class.getClassLoader());
    private static final boolean jackson2XmlPresent =
            ClassUtils.isPresent("com.fasterxml.jackson.dataformat.xml.XmlMapper", RestTemplate.class.getClassLoader());
    private static final boolean gsonPresent =
            ClassUtils.isPresent("com.google.gson.Gson", RestTemplate.class.getClassLoader());

    private final List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();

    @Bean
    RestTemplate restTemplate() {
        this.messageConverters.add(new ByteArrayHttpMessageConverter());
        this.messageConverters.add(new StringHttpMessageConverter());
        this.messageConverters.add(new ResourceHttpMessageConverter());
        this.messageConverters.add(new SourceHttpMessageConverter<>());
        this.messageConverters.add(new FormHttpMessageConverter());
        this.messageConverters.add(new AllEncompassingFormHttpMessageConverter());
        if (romePresent) {
            this.messageConverters.add(new AtomFeedHttpMessageConverter());
            this.messageConverters.add(new RssChannelHttpMessageConverter());
        }
        if (jackson2XmlPresent) {
            this.messageConverters.add(new MappingJackson2XmlHttpMessageConverter());
        } else if (jaxb2Present) {
            this.messageConverters.add(new Jaxb2RootElementHttpMessageConverter());
        }
        if (jackson2Present) {
            this.messageConverters.add(new MappingJackson2HttpMessageConverter());
        } else if (gsonPresent) {
            this.messageConverters.add(new GsonHttpMessageConverter());
        }
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().addAll(this.messageConverters);
        return restTemplate;
    }

    @Bean
    DynamicThreadPool dynamicThreadPool(ThreadPoolProperties properties, RestTemplate restTemplate) {
        return new DynamicThreadPool(properties, restTemplate, false);
    }

}
