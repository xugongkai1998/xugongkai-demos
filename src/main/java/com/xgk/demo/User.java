package com.xgk.demo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: xugongkai
 * @created: 2022-03-29 11:18
 */
@Data
@NoArgsConstructor
public class User {
    private String name;
    private Integer age;
    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}
