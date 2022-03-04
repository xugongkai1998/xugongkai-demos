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
 * @created: 2022-03-04 17:19
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringRunner.class)
public class RequiredNewTest {

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

        REQUIRED_NEW
        B要求使用自己独立的事务.
        此时无论外部是否有事务,都会为自己新建事务执行.
        如果外部存在事务，则会在执行自己的事务之前，将外部事务挂起。直到自己执行完毕时（包括执行异常）才会恢复外部事务，让外部事务继续执行。
        如果一个内部事务执行异常，异常传递给外部事务，外部事务没catch继续上抛，那么此时也是会导致外部事务回滚的。
    */

    @Test
    /* A(存在事务)调用B(需要独立事务),
        A新建事务并正常执行, 当执行到B时:
                挂起A事务,并为自己开启新事务执行...
                执行完毕后，提交自己的事务。并恢复A事务
        A在自己的事务接着执行

        控制台输出：

        A新建事务：Creating new transaction with name [com.xgk.demo.UserServiceA.testRequiredNew1]: PROPAGATION_REQUIRES_NEW,ISOLATION_DEFAULT,-java.lang.Exception
        B挂起A事务并新建自己的事务： Suspending current transaction, creating new transaction with name [com.xgk.demo.UserServiceB.testRequiredNew1]
        恢复A事务：Resuming suspended transaction after completion of inner transaction
    */
    public void testRequiredNew1() {
        userServiceA.testRequiredNew1();
    }

    @Test
    /*
        A(不存在事务)调用B(需要独立事务), B会为自己新建一个事务,此时A非事务执行,B以事务执行,二者独立.
        B: 新建事务Creating new transaction with name [com.xgk.demo.UserServiceB.testRequiredNew2]: PROPAGATION_REQUIRES_NEW,ISOLATION_DEFAULT,-java.lang.Exception
        B:提交事务Committing JDBC transaction on Connection [com.mysql.cj.jdbc.ConnectionImpl@aced190]
    */
    public void testRequiredNew2() {
        userServiceA.testRequiredNew2();
    }

    @Test
    // A(存在事务)调用B(需要独立事务), AB事务独立
    // A中发生异常, 事务B不受影响,正常提交.
    // A回滚
    public void testRequiredNew3() {
        userServiceA.testRequiredNew3();
    }

    @Test
    // A(不存在事务)调用B(需要独立事务), AB事务独立
    // B中发生异常, 事务A不受影响,正常提交.
    // B回滚
    public void testRequiredNew4() {
        userServiceA.testRequiredNew4();
    }

}
