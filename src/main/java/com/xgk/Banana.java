package com.xgk;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

/**
 * @author: xugongkai
 * @created: 2022-02-17 17:49
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "banana")
public class Banana {

    private String cnName;
    private BigDecimal netPrice;

}
