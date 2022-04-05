package com.deep.coral.web.conf;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

/**
 * <h2></h2>
 *
 * @author Create by liuwenhao on 2022/4/1 1:08
 */
@Configuration
public class ExecutorConf {

    /**
     * <h2>创建一个{@link ScheduledThreadPoolExecutor}</h2>
     *
     * @return java.util.concurrent.ScheduledThreadPoolExecutor
     * @author liuwenhao
     * @date 2022/4/1 1:10
     */
    @Bean
    @ConditionalOnMissingBean(ScheduledThreadPoolExecutor.class)
    public ScheduledThreadPoolExecutor executor() {
        // 线程工厂
        ThreadFactory factory = new CustomizableThreadFactory("TASK-");
        // 核心线程数
        int core = Runtime.getRuntime().availableProcessors();
        return new ScheduledThreadPoolExecutor(core, factory);
    }

}