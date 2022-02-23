package com.xgk.demo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

/**
 * @author: xugongkai
 * @created: 2022-02-23 15:24
 */
@Getter
@Setter
public class ShareService {

    @Value("${foo.bar}")
    private Integer value;

    private String uid;

    public ShareService() {
        this.uid = UUID.randomUUID().toString();
    }

}
