package com.xgk.demo;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: xugongkai
 * @created: 2022-03-04 16:16
 */
@Slf4j
public class Utils {

    public static void causeException(String msg) {
        throw new RuntimeException(msg);
    }

}
