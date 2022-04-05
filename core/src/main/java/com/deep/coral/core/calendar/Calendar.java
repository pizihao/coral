package com.deep.coral.core.calendar;

import com.deep.coral.enums.enums.CalendarEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <h2>日历,仅代表某一天在日历上的定位</h2>
 *
 * @author Create by liuwenhao on 2022/1/19 10:19
 */
public interface Calendar {

    /**
     * <h2>获取当前日的(节气/节日)信息</h2>
     *
     * @return java.lang.String
     * @author liuwenhao
     * @date 2022/1/19 10:31
     */
    List<String> getHoliday();

    /**
     * <h2>获取当前日期</h2>
     * <p>转化为公历</p>
     *
     * @return java.time.LocalDate
     * @author liuwenhao
     * @date 2022/1/19 10:35
     */
    LocalDate getDate();

    /**
     * <h2>获取当前日期</h2>
     * <p>转化为公历时间，精确到秒</p>
     *
     * @return java.time.LocalDateTime
     * @author liuwenhao
     * @date 2022/1/20 9:05
     */
    LocalDateTime getDateTime();

    /**
     * <h2>获取当前日期公历的字符串形式</h2>
     *
     * @return java.time.LocalDate
     * @author liuwenhao
     * @date 2022/1/19 10:35
     */
    default String getGregorianDate() {
        return getDate().toString();
    }

    /**
     * <h2>获取当前日期公历的字符串形式</h2>
     *
     * @return java.time.LocalDateTime
     * @author liuwenhao
     * @date 2022/1/19 10:35
     */
    default String getGregorianDateTime() {
        return getDateTime().toString();
    }

    /**
     * <h2>获取当前日期农历的字符串形式</h2>
     *
     * @return java.time.LocalDate
     * @author liuwenhao
     * @date 2022/1/19 10:35
     */
    String getLunarDate();

    /**
     * <h2>获取当前日期农历的字符串形式</h2>
     *
     * @return java.time.LocalDateTime
     * @author liuwenhao
     * @date 2022/1/19 10:35
     */
    String getLunarDateTime();

    /**
     * <h2>获取年</h2>
     *
     * @return int
     * @author liuwenhao
     * @date 2022/1/20 9:43
     */
    int getYear();

    /**
     * <h2>获取月</h2>
     *
     * @return int
     * @author liuwenhao
     * @date 2022/1/20 9:43
     */
    int getMonth();

    /**
     * <h2>获取日</h2>
     *
     * @return int
     * @author liuwenhao
     * @date 2022/1/20 9:43
     */
    int getDay();

    /**
     * <h2>获取时</h2>
     *
     * @return int
     * @author liuwenhao
     * @date 2022/1/20 9:43
     */
    int getHour();

    /**
     * <h2>获取分</h2>
     *
     * @return int
     * @author liuwenhao
     * @date 2022/1/20 9:43
     */
    int getMinute();

    /**
     * <h2>获取秒</h2>
     *
     * @return int
     * @author liuwenhao
     * @date 2022/1/20 9:43
     */
    int getSecond();

    /**
     * <h2>获取当天是周几</h2>
     * 0代表周日，1代表周一,最大值为6
     *
     * @return int
     * @author liuwenhao
     * @date 2022/1/20 10:18
     */
    int getWeek();

    /**
     * <h2>获取日历枚举类型</h2>
     *
     * @return com.example.juc.timer.calendar.CalendarEnum
     * @author liuwenhao
     * @date 2022/1/19 11:06
     */
    CalendarEnum getCalendar();

}
