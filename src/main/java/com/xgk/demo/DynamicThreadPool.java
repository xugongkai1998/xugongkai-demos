package com.xgk.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author: xugongkai
 * @created: 2022-03-18 11:08
 */
@Slf4j
public class DynamicThreadPool implements InitializingBean {

    // true:线程池刷新时立即丢弃其中的任务不再执行.
    // false:正常刷新线程池,继续执行现存任务,此时该池对外不可见,外部的任务将提交到新池.
    private final Boolean closeImmediately;
    private final RestTemplate restTemplate;
    private final ReentrantReadWriteLock readWriteLock;

    private ThreadPoolProperties prop;
    private ThreadPoolExecutor executor;
    private ThreadPoolExecutor pendingCloseExecutor;

    public DynamicThreadPool(ThreadPoolProperties threadPoolProperties, RestTemplate restTemplate, Boolean closeImmediately) {
        this.closeImmediately = closeImmediately;
        this.prop = threadPoolProperties;
        this.restTemplate = restTemplate;
        this.readWriteLock = new ReentrantReadWriteLock(true);
    }

    public String execute(Runnable runnable) {
        Assert.notNull(runnable, "task is null");
        try {
            readWriteLock.readLock().lock();
            executor.execute(runnable);
            return prop.getPoolName();
        }finally {
            readWriteLock.readLock().unlock();
        }
    }

    @Override
    public void afterPropertiesSet() {
        executor = build(prop);
        startConfigAutoRefreshListener();
        log.info("Started thread pool config auto refresh listener");
    }

    private void startConfigAutoRefreshListener() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(true);
                t.setName("configRefreshListener");
                t.setPriority(Thread.MIN_PRIORITY);
                return t;
            }
        });
        scheduledExecutorService.scheduleAtFixedRate(()->{
            try {
                doRefresh();
            } catch (Exception e) {
                log.error("Refresh failed", e);
            }
        }, 3, 10, TimeUnit.SECONDS);
    }

    private void doRefresh() {
        if (pendingCloseExecutor != null && pendingCloseExecutor.isTerminated()) {
            pendingCloseExecutor = null;
        }

        final ThreadPoolProperties newConfig = getConfig();
        boolean configIsChange = !prop.equals(newConfig);
        if (!configIsChange) {
            return;
        }

        log.info("Thread pool config is change, prepare refresh thread pool, current config:{}, new config:{}", prop, newConfig);
        ThreadPoolExecutor newExecutor = build(newConfig);
        try {
            readWriteLock.writeLock().lock();
            pendingCloseExecutor = executor;
            executor = newExecutor;
            if (closeImmediately) {
                List<Runnable> list = pendingCloseExecutor.shutdownNow();
                log.info("thread pool [{}] was called shutdownNow(), drop task count:{}", prop.getPoolName(), list.size());
            } else {
                pendingCloseExecutor.shutdown();
                log.info("thread pool [{}] was called shutdown()", prop.getPoolName());
            }
            prop = newConfig;
            log.info("Thread pool refresh success, new thread pool name: [{}] :)", prop.getPoolName());
        }finally {
            readWriteLock.writeLock().unlock();
        }
    }

    // 从远端拉取配置信息
    // 扩展：可响应为map，同时更新多个线程池信息（新建，销毁，更新）
    private ThreadPoolProperties getConfig() {
        String str = "http://localhost:8080/demos/threadPoolConfig";
        ResponseEntity<ThreadPoolProperties> entity = restTemplate.getForEntity(str, ThreadPoolProperties.class);
        return entity.getBody();
    }

    private ThreadPoolExecutor build(ThreadPoolProperties prop) {
        return new ThreadPoolExecutor(prop.getCoreSize(), prop.getMaxSize(), 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(prop.getQueueSize()));
    }

}
