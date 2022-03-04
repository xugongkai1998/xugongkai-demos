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
public class BootTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void ping() {
        System.out.println(jdbcTemplate.queryForObject("SELECT 1;", String.class));
    }

}
