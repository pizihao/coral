package com.deep.coral.annotations;

import com.deep.coral.enums.enums.CalendarEnum;

import java.lang.annotation.*;

/**
 * <h2>定制周期任务辅助注解</h2>
 * 例如：
 * {@code @Schedule(name = "Schedule", second = 20)}  -> 每分钟的第20秒执行
 * {@code @Schedule(name = "Schedule", minute = 10,second = 20)}  -> 每小时的10分20秒执行
 * {@code @Schedule(name = "Schedule", day = 5,hour = 3, minute = 10,second = 20)}  -> 每月的3号3点10分20秒执行
 * {@code @Schedule(name = "Schedule", day = 5,hour = 3, minute = 10,second = 20,isMonth = false)}  -> 每周的周三3点10分20秒执行
 *
 * @author Create by liuwenhao on 2022/2/10 10:26
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Schedule {

    /**
     * 任务标识
     */
    String name() default "";

    /**
     * 历制，区分农历和公历
     */
    CalendarEnum calendarEnum() default CalendarEnum.GREGORIAN;

    /**
     * 年
     */
    int year() default -1;

    /**
     * 月
     */
    int month() default -1;

    /**
     * 日
     */
    int day() default -1;

    /**
     * 时
     */
    int hour() default -1;

    /**
     * 分
     */
    int minute() default -1;

    /**
     * 秒
     */
    int second();

    /**
     * 用于区分周和月，如果任务以月为单位循环则为true，如果任务以周围单位循环则为false
     */
    boolean isMonth() default true;

}
