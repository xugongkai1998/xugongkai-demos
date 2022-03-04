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
public class NeverTest {

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
        B要求不能存在事务, A本身存在事务
        因此B直接报错.
    */
    public void testNever1() {
        userServiceA.testNever1();
    }

    @Test
    /*
        B要求不能存在事务, A本身也不存在事务,
        正常执行
     */
    public void testNever2() {
        userServiceA.testNever2();
    }

}
