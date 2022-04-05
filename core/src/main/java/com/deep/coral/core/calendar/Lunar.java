package com.deep.coral.core.calendar;

import cn.hutool.core.text.CharSequenceUtil;
import com.deep.coral.enums.enums.CalendarEnum;
import com.google.common.base.Joiner;
import com.nlf.calendar.Solar;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * <h2>农历</h2>
 *
 * @author Create by liuwenhao on 2022/1/19 10:18
 */
public class Lunar implements Calendar {

    /**
     * 枚举
     */
    CalendarEnum calendarEnum = CalendarEnum.LUNAR;

    /**
     * 当前的 日期
     */
    com.nlf.calendar.Lunar lunar;

    public Lunar(Date date) {
        lunar = new com.nlf.calendar.Lunar(date);
    }

    public Lunar(LocalDate d) {
        this(Date.from(d.atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }

    public Lunar(LocalDateTime d) {
        this(Date.from(d.atZone(ZoneId.systemDefault()).toInstant()));
    }

    /**
     * <h2>构造器，农历的年月日</h2>
     *
     * @param y 年
     * @param m 月
     * @param d 日
     * @author liuwenhao
     * @date 2022/1/19 11:28
     */
    public Lunar(int y, int m, int d) {
        lunar = new com.nlf.calendar.Lunar(y, m, d);
    }

    /**
     * <h2>构造器，公历的年月日时分秒</h2>
     *
     * @param year   年
     * @param month  月
     * @param day    日
     * @param hour   时
     * @param minute 分
     * @param second 秒
     * @author Lunar
     * @date 2022/1/19 11:28
     */
    public Lunar(int year, int month, int day, int hour, int minute, int second) {
        lunar = new com.nlf.calendar.Lunar(year, month, day, hour, minute, second);
    }

    public Lunar() {
        lunar = new com.nlf.calendar.Lunar();
    }

    @Override
    public List<String> getHoliday() {
        List<String> holiday = lunar.getFestivals();
        holiday.addAll(lunar.getOtherFestivals());
        String jieQi = lunar.getJieQi();
        if (CharSequenceUtil.isNotBlank(jieQi)) {
            holiday.add(jieQi);
        }
        return holiday;
    }

    @Override
    public LocalDate getDate() {
        Solar solar = lunar.getSolar();
        return LocalDate.of(solar.getYear(), solar.getMonth(), solar.getDay());
    }

    @Override
    public LocalDateTime getDateTime() {
        Solar solar = lunar.getSolar();
        return LocalDateTime.of(
            solar.getYear(),
            solar.getMonth(),
            solar.getDay(),
            solar.getHour(),
            solar.getMinute(),
            solar.getSecond());
    }

    @Override
    public String getLunarDate() {
        int year = lunar.getYear();
        String month = String.format("%02d", lunar.getMonth());
        String day = String.format("%02d", lunar.getDay());
        return Joiner.on("-").join(year, month, day);
    }

    @Override
    public String getLunarDateTime() {
        int year = lunar.getYear();
        String month = String.format("%02d", lunar.getMonth());
        String day = String.format("%02d", lunar.getDay());
        String hour = String.format("%02d", lunar.getHour());
        String minute = String.format("%02d", lunar.getMinute());
        String second = String.format("%02d", lunar.getSecond());
        return Joiner.on("-").join(year, month, day, hour, minute, second);
    }

    @Override
    public int getYear() {
        return lunar.getYear();
    }

    @Override
    public int getMonth() {
        return lunar.getMonth();
    }

    @Override
    public int getDay() {
        return lunar.getDay();
    }

    @Override
    public int getHour() {
        return lunar.getHour();
    }

    @Override
    public int getMinute() {
        return lunar.getMinute();
    }

    @Override
    public int getSecond() {
        return lunar.getSecond();
    }

    @Override
    public int getWeek() {
        return lunar.getWeek();
    }

    @Override
    public CalendarEnum getCalendar() {
        return calendarEnum;
    }

}