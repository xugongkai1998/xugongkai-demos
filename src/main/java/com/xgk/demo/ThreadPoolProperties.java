package com.xgk.demo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

/**
 * @author: xugongkai
 * @created: 2022-03-18 11:07
 */
@Getter
@Setter
@ToString
@Configuration
@ConfigurationProperties(prefix = "thread-pool")
public class ThreadPoolProperties {

    private String poolName;
    private Integer coreSize;
    private Integer maxSize;
    private Integer queueSize;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ThreadPoolProperties)) return false;
        ThreadPoolProperties that = (ThreadPoolProperties) o;
        return Objects.equals(coreSize, that.coreSize) &&
                Objects.equals(maxSize, that.maxSize) &&
                Objects.equals(queueSize, that.queueSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coreSize, maxSize, queueSize);
    }
}
