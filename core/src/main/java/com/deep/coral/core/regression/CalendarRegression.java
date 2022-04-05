package com.deep.coral.core.regression;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.deep.coral.core.calendar.CalendarFactory;
import com.deep.coral.enums.enums.CalendarEnum;
import com.deep.coral.enums.enums.CycleUnit;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <h2>Calendar 任务回归计时器</h2>
 *
 * @author Create by liuwenhao on 2022/3/30 15:08
 */
public class CalendarRegression implements Regression {

    /**
     * 循环单位，年月周日时分秒，一次循环的最小单位，如每年，每月，每周，每天等
     */
    CycleUnit cycleUnit;

    /**
     * 秒[0,60)，如：<br>
     * 每分的30秒执行任务 cycleUnit = MINUTE,second = 30
     */
    int second = 0;

    /**
     * 分钟[0,60)，如：<br>
     * 每小时的15分执行任务 cycleUnit = HOUR,minute = 15<br>
     * 每小时的15分30秒执行任务 cycleUnit = HOUR,minute = 15,second = 30
     */
    int minute = 0;

    /**
     * 小时[0,24)，如：<br>
     * 每天的6时执行任务 cycleUnit = DAY,minute = 30<br>
     * 每天的6时15分执行任务 cycleUnit = DAY,minute = 15<br>
     * 每天的6时15分30秒执行任务 cycleUnit = DAY,minute = 15,second = 30
     */
    int hour = 0;

    /**
     * 天[0,∞)，对于天的范围在年和月为单位时会做特殊处理，如：<br>
     * 每周的第一天(周一)执行任务 cycleUnit = WEEK,day = 1<br>
     * 每月的第2天(2号)执行任务 cycleUnit = MONTH,day = 2<br>
     * 每年的第180天执行任务 cycleUnit = YEAR,day = 180<br>
     */
    int day = 0;

    /**
     * 月[0,12)，如：<br>
     * 每年的2月执行任务 cycleUnit = YEAR, month = 2
     */
    int month = 0;

    /**
     * 年，一个未来的时间点，可以定制在未来的某一年的某个时间执行任务
     */
    int year = 0;

    /**
     * 历制类型，整个配置依照公历或者农历，范围覆盖到 month，day，hour，minute，second
     */
    CalendarEnum calendarEnum;

    /**
     * 上一次执行的时间
     */
    LocalDateTime lastTime = LocalDateTime.now();

    /**
     * 下一次执行的时间
     */
    LocalDateTime nextTime;


    /**
     * 任务执行的初始时间
     */
    LocalDateTime firstTime;

    /**
     * 记录次数
     */
    AtomicInteger count = new AtomicInteger(0);


    public CalendarRegression(CycleUnit cycleUnit, int second, int minute, int hour, int day, int month, int year, CalendarEnum calendarEnum) {
        this.cycleUnit = cycleUnit;
        LocalDateTime now = CalendarFactory.calendarBuilder(calendarEnum).getDateTime();
        this.second = second;
        this.minute = minute > 0 ? minute : now.getMinute();
        this.hour = hour > 0 ? hour : now.getHour();
        this.day = day > 0 ? day : now.getDayOfMonth();
        this.month = month > 0 ? month : now.getMonthValue();
        this.year = year > 0 ? year : now.getYear();
        this.calendarEnum = calendarEnum;
        this.nextTime = LocalDateTime.of(this.year, this.month, this.day, this.hour, this.minute, this.second);
        this.firstTime = LocalDateTime.of(this.year, this.month, this.day, this.hour, this.minute, this.second);
    }

    @Override
    public Long getInitialDelay() {
        this.lastTime = LocalDateTime.now();
        return LocalDateTimeUtil.between(lastTime, nextTime, ChronoUnit.NANOS);
    }

    @Override
    public Long getInterval() {
        // 偏移时间
        LocalDateTime dateTime = restTime();
        // 重置时间
        init(dateTime);
        // 两次的时间间隔
        return LocalDateTimeUtil.between(lastTime, nextTime, ChronoUnit.NANOS);
    }

    /**
     * <h2>根据不同的cycleUnit重构配置中的时间点</h2>
     *
     * @author liuwenhao
     * @date 2022/1/24 17:50
     */
    private LocalDateTime restTime() {
        // 通过配置项对当前的时间进行偏移，得到一个偏移后的时间
        return cycleUnit.recast(firstTime, count.incrementAndGet());
    }

    /**
     * <h2>初始化属性</h2>
     *
     * @param localDateTime 日期
     * @author liuwenhao
     * @date 2022/2/9 14:17
     */
    private void init(LocalDateTime localDateTime) {
        this.year = localDateTime.getYear();
        this.month = localDateTime.getMonthValue();
        this.day = localDateTime.getDayOfMonth();
        this.hour = localDateTime.getHour();
        this.minute = localDateTime.getMinute();
        this.second = localDateTime.getSecond();
        this.lastTime = this.nextTime;
        this.nextTime = localDateTime;
    }


}