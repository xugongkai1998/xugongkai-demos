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
 * @created: 2022-03-04 18:21
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringRunner.class)
public class MandatoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserServiceA userServiceA;

    @Test
    public void ping() {
        System.out.println(jdbcTemplate.queryForObject("SELECT 1;", String.class));
    }

    /*
        A调用B,
        A/B 各自插入一个User记录.

        MANDATORY
        B强制要求外部存在事务,
        如果外部确实存在，则加入外部事务，二者共用一个事务，异常时一起回滚。
        如果外部不存在事务，直接报错。
     */


    @Test
    /*
        B强制要求外部存在事务,A本身存在事务
        B加入到A事务中,正常执行.
     */
    public void testMandatory1() {
        userServiceA.testMandatory1();
    }

    @Test
        /*
        B强制要求外部存在事务,A本身不存在事务
        报错
     */
    public void testMandatory2() {
        userServiceA.testMandatory2();
    }

    @Test
    /*
        B强制要求外部存在事务,A本身存在事务
        B加入到A事务中,发生异常, 此时AB均回滚
     */
    public void testMandatory3() {
        userServiceA.testMandatory3();
    }


}
