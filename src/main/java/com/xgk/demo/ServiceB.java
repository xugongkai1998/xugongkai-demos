package com.xgk.demo;

import lombok.Getter;

/**
 * @author: xugongkai
 * @created: 2022-02-23 15:23
 */
public class ServiceB {
    @Getter
    private final ShareService shareService;
    public ServiceB(ShareService shareService) {
        this.shareService = shareService;
    }
}
