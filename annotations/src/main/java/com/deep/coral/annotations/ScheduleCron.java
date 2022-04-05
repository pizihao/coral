package com.deep.coral.annotations;

import java.lang.annotation.*;

/**
 * <h2>Cron定时任务辅助注解</h2>
 * 例如：
 * {@code @ScheduleCron(name = "ScheduleCron", cron = "5 * * * *")} -> 每个点钟的5分执行，00:05,01:05……</li>
 *
 * @author Create by liuwenhao on 2022/2/10 10:26
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ScheduleCron {

    /**
     * 任务标识
     */
    String name() default "";

    /**
     * 表达式
     */
    String cron();

}