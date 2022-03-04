package com.xgk.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringRunner.class)
public class NestedTest {

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
       B要求内嵌事务, 外部A存在事务，
       此时B直接在A的基础上新建嵌套事务运行。

        外部事务A回滚的话，子事务B也会回滚，而内部子事务B可以单独回滚,
        而不影响外部主事务A和其他子事务（需要处理掉内部子事务的异常）。
    */
    public void testNested1() {
        userServiceA.testNested1();
    }

    @Test
    /*
       B要求内嵌事务, 外部A不存在事务，
       此时B直接新建事务运行。
     */
    public void testNested2() {
        userServiceA.testNested2();
    }

}
