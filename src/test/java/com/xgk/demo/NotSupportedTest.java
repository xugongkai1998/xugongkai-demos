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
public class NotSupportedTest {

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
        B不能以事务执行，A本身存在事务, 则B将外部事务先挂起，待自己执行完毕后（包括异常），再恢复事务A继续执行。
        如果B的异常一直
    */
    public void testNotSupported1() {
        userServiceA.testNotSupported1();
    }

    @Test
    /*
        B不能以事务执行，A本身不存在事务, 则B也直接以非事务方式执行
     */
    public void testNotSupported2() {
        userServiceA.testNotSupported2();
    }

}
