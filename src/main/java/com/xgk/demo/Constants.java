package com.xgk.demo;

import lombok.Getter;

/**
 * @author: xugongkai
 * @created: 2022-03-14 14:29
 */
@Getter
public class Constants {

    public static class Exchange{
        public static final String DEFAULT_EXCHANGE = "x_sp_orderservice";
    }

    public static class RouterKey{
        public static final String DEFAULT_ROUTER_KEY = "rk_xtest";
        public static final String DEATH_ROUTER_KEY = "rk_xtest_death";
    }

    public static class Queue {
        public static final String ORDER_QUEUE = "q_sp_order";
        public static final String DEATH_QUEUE = "q_sp_order_death";
    }

}
