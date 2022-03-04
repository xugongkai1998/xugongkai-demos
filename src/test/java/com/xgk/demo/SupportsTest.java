package com.xgk.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: xugongkai
 * @created: 2022-03-04 18:50
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringRunner.class)
public class SupportsTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserServiceA userServiceA;

    @Test
    public void ping() {
        System.out.println(jdbcTemplate.queryForObject("SELECT 1;", String.class));
    }

    @Test
    /*
        B对事务可有可无，A本身存在事务, 则B加入事务A，AB共用一个，异常时同时回滚.
    */
    public void testSupports1() {
        userServiceA.testSupports1();
    }

    @Test
    /*
        B对事务可有可无，A本身不存在事务, 则B也直接以非事务方式执行
     */
    public void testSupports2() {
        userServiceA.testSupports2();
    }

}