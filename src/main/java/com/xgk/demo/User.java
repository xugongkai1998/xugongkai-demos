package com.xgk.demo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author: xugongkai
 * @created: 2022-03-04 15:56
 */
@Getter
@Setter
@ToString
public class User implements Serializable {

    private Long id;
    private String username;
    private String password;
    private Integer status;

    public static String insertNewUser(Long id) {
        id = id == null ? System.currentTimeMillis() : id;
        String username = RandomStringUtils.randomAlphabetic(5);
        String pwd = RandomStringUtils.randomAlphabetic(5);
        int status = Math.random() >= 0.5 ? 1 : 0;
        return String.format("INSERT INTO t_user(`id`, `username`, `password`, `status`) VALUE (%s, '%s', '%s', %s);", id, username, pwd, status);
    }

    public static String updateById(Long id, String username, String password, Integer status) {
        if (id == null) {
            throw new NullPointerException("id is null");
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (StringUtils.isNotEmpty(username)) {
            stringBuilder.append("`username` = ").append("'").append(username).append("',");
        }
        if (StringUtils.isNotEmpty(password)) {
            stringBuilder.append("`password` = ").append("'").append(password).append("',");
        }
        if (status != null) {
            stringBuilder.append("`status` = ").append(username).append(",");
        }
        if (stringBuilder.length() == 0) {
            throw new IllegalArgumentException();
        }
        stringBuilder.deleteCharAt((stringBuilder.length() - 1));
        return String.format("UPDATE t_user SET %s WHERE id = %s;", stringBuilder.toString(), id);
    }

    public static void main(String[] args) {
        String s1 = User.insertNewUser(123L);
        String s2 = User.insertNewUser(null);
        String s3 = User.updateById(1L, "a", "b", 1);
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);
    }

}
