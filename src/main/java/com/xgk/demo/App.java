package com.xgk.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@Slf4j
@SpringBootApplication
public class App implements ApplicationRunner {

    @Autowired
    private ServiceA serviceA;
    @Autowired
    private ServiceB serviceB;

    /*
    调整ServiceConfig上注解@Configuration(proxyBeanMethods = true / false)
    当为true，且shareService()未使用@Scope("prototype")时：
        注入serviceA / serviceB 时使用的 shareService()，都会被拦截，且被Spring后处理类ShareServiceBeanPostProcessor处理，将uid调整为：Build UUID by beanPostProcessor + 处理时生成的随机数
        然后实例化 serviceA / serviceB 时候，从IOC拿到同一个shareService对象，
        且ShareService中也可通过spring的方式，注入属性value.
        此时serviceA， serviceB中的shareService是同一个。
        【200】

    当为true，且shareService()使用@Scope("prototype")时：
        注入serviceA / serviceB 时使用的 shareService()，都会被拦截，且被Spring后处理类ShareServiceBeanPostProcessor处理，将uid调整为：Build UUID by beanPostProcessor + 处理时生成的随机数
        然后实例化 serviceA / serviceB 时候，从IOC拿到每次重新生成的shareService对象，
        且ShareService中也可通过spring的方式，注入属性value.
        此时serviceA， serviceB中的shareService不是同一个。
        【500】

    当为false, 实例化serviceA 、serviceB时，调用的shareService()实际上就是Java普通调用
    观察控制台输出结果，发现拿到的shareService都是new的，完全不同，且不会经过spring处理。value字段并没有注入值
    【100】
    */
    public static void main(String[] args) {
        new SpringApplicationBuilder(App.class).bannerMode(Banner.Mode.OFF).run(args);
    }

    @Override
    public void run(ApplicationArguments args) {
        System.out.println(serviceA.getShareService().getUid());
        System.out.println(serviceB.getShareService().getUid());
        System.out.println(serviceA.getShareService().getValue());
        System.out.println(serviceB.getShareService().getValue());
    }

}
