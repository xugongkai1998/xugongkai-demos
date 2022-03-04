package com.xgk.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: xugongkai
 * @created: 2022-03-04 16:18
 */
@Service
public class UserServiceB {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testRequired1() {
        String sql = User.insertNewUser(null);
        jdbcTemplate.execute(sql);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testRequired2() {
        String sql = User.insertNewUser(null);
        jdbcTemplate.execute(sql);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testRequired3() {
        String sql = User.insertNewUser(null);
        jdbcTemplate.execute(sql);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testRequired4() {
        String sql = User.insertNewUser(null);
        jdbcTemplate.execute(sql);
        Utils.causeException("serviceB异常");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void testRequiredNew1() {
        String sql = User.insertNewUser(null);
        jdbcTemplate.execute(sql);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void testRequiredNew2() {
        String sql = User.insertNewUser(null);
        jdbcTemplate.execute(sql);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void testRequiredNew3() {
        String sql = User.insertNewUser(null);
        jdbcTemplate.execute(sql);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void testRequiredNew4() {
        String sql = User.insertNewUser(null);
        jdbcTemplate.execute(sql);
        Utils.causeException("serviceB异常");
    }

    @Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
    public void testMandatory1() {
        String sql = User.insertNewUser(null);
        jdbcTemplate.execute(sql);
    }

    @Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
    public void testMandatory2() {
        String sql = User.insertNewUser(null);
        jdbcTemplate.execute(sql);
    }

    @Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
    public void testMandatory3() {
        String sql = User.insertNewUser(null);
        jdbcTemplate.execute(sql);
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public void testSupports1() {
        String sql = User.insertNewUser(null);
        jdbcTemplate.execute(sql);
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public void testSupports2() {
        String sql = User.insertNewUser(null);
        jdbcTemplate.execute(sql);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    public void testNotSupported1() {
        String sql = User.insertNewUser(null);
        jdbcTemplate.execute(sql);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    public void testNotSupported2() {
        String sql = User.insertNewUser(null);
        jdbcTemplate.execute(sql);
    }

}
