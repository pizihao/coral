package com.deep.coral.core.calendar;

import com.deep.coral.enums.enums.CalendarEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <h2>日历工厂</h2>
 *
 * @author Create by liuwenhao on 2022/1/24 15:54
 */
public class CalendarFactory {

    private CalendarFactory() {
    }

    /**
     * <h2>根据特有的历制以当前时间为基础生成日历信息</h2>
     *
     * @param calendarEnum 历制枚举
     * @return com.example.juc.timer.calendar.Calendar
     * @author liuwenhao
     * @date 2022/2/7 10:37
     */
    public static Calendar calendarBuilder(CalendarEnum calendarEnum) {
        return calendarEnum.equals(CalendarEnum.LUNAR) ? new Lunar() : new Gregorian();
    }

    /**
     * <h2>根据特有的历制和指定的年月日生成日历信息</h2>
     * <p>时分秒区各个时间段的第一个值</p>
     *
     * @param calendarEnum 历制枚举
     * @param year         年
     * @param month        月
     * @param day          日，相对于月的日，即DayOfMonth
     * @return com.example.juc.timer.calendar.Calendar
     * @author liuwenhao
     * @date 2022/2/7 10:38
     */
    public static Calendar calendarBuilder(CalendarEnum calendarEnum, int year, int month, int day) {
        return calendarEnum.equals(CalendarEnum.LUNAR) ?
            new Lunar(year, month, day) : new Gregorian(year, month, day);
    }

    /**
     * <h2>根据特有的历制和指定的年月日时分秒生成日历信息</h2>
     *
     * @param calendarEnum 历制
     * @param year         年
     * @param month        月
     * @param day          日，相对于月的日，即DayOfMonth
     * @param hour         时
     * @param minute       分
     * @param second       秒
     * @return com.example.juc.timer.calendar.Calendar
     * @author liuwenhao
     * @date 2022/2/7 10:43
     */
    public static Calendar calendarBuilder(CalendarEnum calendarEnum, int year, int month, int day, int hour, int minute, int second) {
        return calendarEnum.equals(CalendarEnum.LUNAR) ?
            new Lunar(year, month, day, hour, minute, second) : new Gregorian(year, month, day, hour, minute, second);
    }

    /**
     * <h2>根据特有的历制和指定的时间生成日历信息</h2>
     *
     * @param calendarEnum 历制
     * @param localDate    LocalDate
     * @return com.example.juc.timer.calendar.Calendar
     * @author liuwenhao
     * @date 2022/2/7 10:44
     */
    public static Calendar calendarBuilder(CalendarEnum calendarEnum, LocalDate localDate) {
        return calendarEnum.equals(CalendarEnum.LUNAR) ?
            new Lunar(localDate) : new Gregorian(localDate);
    }

    /**
     * <h2>根据特有的历制和指定的时间生成日历信息</h2>
     *
     * @param calendarEnum  历制
     * @param localDateTime LocalDateTime
     * @return com.example.juc.timer.calendar.Calendar
     * @author liuwenhao
     * @date 2022/2/7 10:44
     */
    public static Calendar calendarBuilder(CalendarEnum calendarEnum, LocalDateTime localDateTime) {
        return calendarEnum.equals(CalendarEnum.LUNAR) ?
            new Lunar(localDateTime) : new Gregorian(localDateTime);
    }

    /**
     * <h2>根据特有的历制和指定的时间生成日历信息</h2>
     *
     * @param calendarEnum  历制
     * @param date date
     * @return com.example.juc.timer.calendar.Calendar
     * @author liuwenhao
     * @date 2022/2/7 10:44
     */
    public static Calendar calendarBuilder(CalendarEnum calendarEnum, Date date) {
        return calendarEnum.equals(CalendarEnum.LUNAR) ?
            new Lunar(date) : new Gregorian(date);
    }
}