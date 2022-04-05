package com.deep.coral.annotations;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * <h2>简单循环任务辅助注解</h2>
 * 例如：
 * {@code @ScheduleRate(timeUnit = TimeUnit.SECONDS, initialDelay = 2, interval = 60)} -> 每隔60秒执行一次，初次执行延迟2秒
 *
 * @author Create by liuwenhao on 2022/1/20 11:40
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ScheduleRate {

    /**
     * 任务标识
     */
    String name() default "";

    /**
     * 延迟时间，任务被调度到任务第一次执行等待的时间
     */
    long initialDelay();

    /**
     * 间隔时间，每两次任务执行的时间间隔
     */
    long interval();

    /**
     * 单位，固定延时时间和任务间隔时间的时间刻度单位，最大为天
     */
    TimeUnit timeUnit();

}
