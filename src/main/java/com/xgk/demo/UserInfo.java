package com.xgk.demo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author: xugongkai
 * @created: 2022-03-18 15:59
 */
@Getter
@Setter
@ToString
public class UserInfo implements Serializable {

    private String name;
    private Integer age;
    private String address;

}
