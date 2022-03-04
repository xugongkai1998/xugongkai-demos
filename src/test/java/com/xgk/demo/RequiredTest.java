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
 * @created: 2022-03-04 15:51
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringRunner.class)
public class RequiredTest {

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
        A/B 各自插入一个User记录

        REQUIRED:
            执行过程需要事务,
            如果被调用时已经存在事务,则加入已存在事务,此时二者使用同一个事务，在这种情况下任何一方发生异常, 整个事务操作都将回滚.
            如果被调用时外部不存在事务,则自己新建事务环境,一方发生异常时,另一方不受影响(有被正常执行到)
     */

    @Test
    // A(存在事务)调用B(需要事务), B直接加入到A事务, AB共用一个
    // A为自己新建事务：Creating new transaction with name [com.xgk.demo.UserServiceA.testRequired1]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT,-java.lang.Exception
    // B加入到A的事务：Participating in existing transaction
    public void testRequired1() {
        userServiceA.testRequired1();
    }

    @Test
    // A(不存在事务)调用B(需要事务), B会为自己新建一个事务,此时A非事务执行,B以事务执行,二者独立.
    // B为自己新建事务：Creating new transaction with name [com.xgk.demo.UserServiceB.testRequired2]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT,-java.lang.Exception
    public void testRequired2() {
        userServiceA.testRequired2();
    }

    @Test
    // A(存在事务)调用B(需要事务), B直接加入到A事务, AB共用一个
    // A中发生异常, AB所做的操作，全部回滚
    // A新建自己的事务：Creating new transaction with name [com.xgk.demo.UserServiceA.testRequired3]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT,-java.lang.Exception
    // B加入A: Participating in existing transaction
    // 回滚：Rolling back JDBC transaction on Connection [com.mysql.cj.jdbc.ConnectionImpl@3a095ec0]
    public void testRequired3() {
        userServiceA.testRequired3();
    }

    @Test
    // A(不存在事务)调用B(需要事务), B会为自己新建一个事务,此时A非事务执行,B以事务执行,二者独立.
    // A成功执行SQL, B中发生异常.
    // 此时仅回滚B的操作, A操作不会回滚
    // B新建事务：Creating new transaction with name [com.xgk.demo.UserServiceB.testRequired4]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT,-java.lang.Exception
    // B回滚：Rolling back JDBC transaction on Connection [com.mysql.cj.jdbc.ConnectionImpl@aced190]
    public void testRequired4() {
        userServiceA.testRequired4();
    }

}
