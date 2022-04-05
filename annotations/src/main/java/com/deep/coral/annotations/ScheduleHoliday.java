package com.deep.coral.annotations;

import com.deep.coral.enums.enums.HolidayEnum;

import java.lang.annotation.*;

/**
 * <h2>节日/节气定时任务辅助注解</h2>
 * 例如：
 * {@code @ScheduleHoliday(name = "ScheduleHoliday", holiday = HolidayEnum.SPRING_FESTIVAL),isOnce = true} -> 每年的春节执行
 * {@code @ScheduleHoliday(name = "ScheduleHoliday", holiday = HolidayEnum.SPRING_FESTIVAL),isOnce = false} -> 下一个春节执行
 *
 * @author Create by liuwenhao on 2022/2/10 10:26
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ScheduleHoliday {

    /**
     * 任务标识
     */
    String name() default "";

    /**
     * 节日
     */
    HolidayEnum holiday();

    /**
     * 是否仅执行一次，true：仅执行一次，false：循环执行(节气按照年来划分)
     */
    boolean isOnce();
}
