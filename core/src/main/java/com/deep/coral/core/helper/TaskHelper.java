package com.deep.coral.core.helper;

import com.deep.coral.core.manager.TaskManager;
import com.deep.coral.core.task.*;
import com.deep.coral.enums.enums.CalendarEnum;
import com.deep.coral.enums.enums.HolidayEnum;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <h2>辅助，快速的生成一个定时任务</h2>
 *
 * @author Create by liuwenhao on 2022/4/5 1:35
 */
public class TaskHelper {

    // ===================================BasicTask====================================

    public static BasicTask rateBuilder(TimeUnit timeUnit, long initialDelay, long interval) {
        return new RateTask(timeUnit, initialDelay, interval);
    }

    public static BasicTask cronBuilder(String cron) {
        return new CronTask(cron);
    }

    public static BasicTask holidayBuilder(HolidayEnum holiday, boolean isOnce) {
        return new HolidayTask(holiday, isOnce);
    }

    public static BasicTask calendarBuilder(CalendarEnum calendarEnum, int year, int month, int day, int hour, int minute, int second) {
        return new CalendarTask(calendarEnum, year, month, day, hour, day, second);
    }

    public static BasicTask calendarBuilder(CalendarEnum calendarEnum, int month, int day, int hour, int minute, int second) {
        return new CalendarTask(calendarEnum, month, day, hour, minute, second);
    }

    public static BasicTask calendarBuilder(CalendarEnum calendarEnum, int day, int hour, int minute, int second, boolean isMonth) {
        return new CalendarTask(calendarEnum, day, hour, minute, second, isMonth);
    }

    public static BasicTask calendarBuilder(CalendarEnum calendarEnum, int hour, int minute, int second) {
        return new CalendarTask(calendarEnum, hour, minute, second);
    }

    public static BasicTask calendarBuilder(CalendarEnum calendarEnum, int minute, int second) {
        return new CalendarTask(calendarEnum, minute, second);
    }

    public static BasicTask calendarBuilder(CalendarEnum calendarEnum, int second) {
        return new CalendarTask(calendarEnum, second);
    }

    // ====================================TaskManager=======================================

    public static TaskManager builder() {
        return new TaskManager();
    }

    // ====================================TaskGuardian=======================================

    /**
     * <h2>name 在一个TaskManager必须是唯一的</h2>
     * 如果放入相同的name，默认配置为 覆盖
     *
     * @param executor 线程池
     * @param name     任务标识
     * @return com.deep.coral.core.task.TaskGuardian
     * @author liuwenhao
     * @date 2022/4/5 10:23
     */
    public static TaskGuardian builder(ScheduledThreadPoolExecutor executor, String name) {
        return TaskGuardian.build(executor, name);
    }
}