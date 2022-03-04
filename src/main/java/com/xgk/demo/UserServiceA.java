package com.xgk.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: xugongkai
 * @created: 2022-03-04 16:17
 */
@Service
public class UserServiceA {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserServiceB userServiceB;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testRequired1() {
        String sql = User.insertNewUser(null);
        jdbcTemplate.execute(sql);
        userServiceB.testRequired1();
    }

    public void testRequired2() {
        String sql = User.insertNewUser(null);
        jdbcTemplate.execute(sql);
        userServiceB.testRequired2();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testRequired3() {
        String sql = User.insertNewUser(null);
        jdbcTemplate.execute(sql);
        userServiceB.testRequired3();
        Utils.causeException("serverA异常");
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testRequired4() {
        String sql = User.insertNewUser(null);
        jdbcTemplate.execute(sql);
        userServiceB.testRequired4();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void testRequiredNew1() {
        String sql = User.insertNewUser(null);
        jdbcTemplate.execute(sql);
        userServiceB.testRequiredNew1();
    }

    public void testRequiredNew2() {
        String sql = User.insertNewUser(null);
        jdbcTemplate.execute(sql);
        userServiceB.testRequiredNew2();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void testRequiredNew3() {
        String sql = User.insertNewUser(null);
        jdbcTemplate.execute(sql);
        userServiceB.testRequiredNew3();
        Utils.causeException("serverA异常");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void testRequiredNew4() {
        String sql = User.insertNewUser(null);
        jdbcTemplate.execute(sql);
        userServiceB.testRequiredNew4();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testMandatory1() {
        userServiceB.testMandatory1();
    }

    public void testMandatory2() {
        userServiceB.testMandatory2();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testMandatory3() {
        userServiceB.testMandatory3();
        Utils.causeException("serviceA异常");
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testSupports1() {
        userServiceB.testSupports1();
    }

    public void testSupports2() {
        String sql = User.insertNewUser(null);
        jdbcTemplate.execute(sql);
        userServiceB.testSupports2();
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testNotSupported1() {
        userServiceB.testNotSupported1();
    }

    public void testNotSupported2() {
        userServiceB.testNotSupported2();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testNever1() {
        userServiceB.testNever1();
    }

    public void testNever2() {
        userServiceB.testNever2();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testNested1() {
        userServiceB.testNested1();
    }

    public void testNested2() {
        userServiceB.testNested2();
    }

}
