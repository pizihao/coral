package com.deep.coral.core.calendar;

import com.deep.coral.enums.enums.CalendarEnum;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.nlf.calendar.Lunar;
import com.nlf.calendar.Solar;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;


/**
 * <h2>公历</h2>
 *
 * @author Create by liuwenhao on 2022/1/19 10:19
 */
public class Gregorian implements Calendar {

    /**
     * 枚举
     */
    CalendarEnum calendarEnum = CalendarEnum.GREGORIAN;

    /**
     * 当前的 日期
     */
    Solar solar;

    public Gregorian(Date date){
        solar = new Solar(date);
    }

    public Gregorian(LocalDate d) {
        this(Date.from(d.atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }

    public Gregorian(LocalDateTime d) {
        this(Date.from(d.atZone(ZoneId.systemDefault()).toInstant()));
    }

    /**
     * <h2>构造器，公历的年月日</h2>
     *
     * @param y 年
     * @param m 月
     * @param d 日
     * @author liuwenhao
     * @date 2022/1/19 11:28
     */
    public Gregorian(int y, int m, int d) {
        solar = new Solar(y, m, d);
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
     * @author liuwenhao
     * @date 2022/1/19 11:28
     */
    public Gregorian(int year, int month, int day, int hour, int minute, int second) {
        solar = new Solar(year, month, day, hour, minute, second);
    }

    public Gregorian() {
        solar = new Solar();
    }

    @Override
    public List<String> getHoliday() {
        List<String> holiday = Lists.newArrayList(solar.getOtherFestivals());
        holiday.addAll(solar.getFestivals());
        return holiday;
    }

    @Override
    public LocalDate getDate() {
        return LocalDate.of(solar.getYear(), solar.getMonth(), solar.getDay());
    }

    @Override
    public LocalDateTime getDateTime() {
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
        Lunar lunar = solar.getLunar();
        int year = lunar.getYear();
        String month = String.format("%02d", lunar.getMonth());
        String day = String.format("%02d", lunar.getDay());
        return Joiner.on("-").join(year, month, day);
    }

    @Override
    public String getLunarDateTime() {
        Lunar lunar = solar.getLunar();
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
        return solar.getYear();
    }

    @Override
    public int getMonth() {
        return solar.getMonth();
    }

    @Override
    public int getDay() {
        return solar.getDay();
    }

    @Override
    public int getHour() {
        return solar.getHour();
    }

    @Override
    public int getMinute() {
        return solar.getMinute();
    }

    @Override
    public int getSecond() {
        return solar.getSecond();
    }

    @Override
    public int getWeek() {
        return solar.getWeek();
    }

    @Override
    public CalendarEnum getCalendar() {
        return calendarEnum;
    }

}