package com.xgk.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author: xugongkai
 * @created: 2022-02-08 17:56
 */
@Slf4j
public class App{

    private static final ExecutorService pool = Executors.newFixedThreadPool(1);

    public static void main(String[] args) {
        System.out.println("Hello");
    }

}
