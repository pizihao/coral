package com.deep.coral.core.regression;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.deep.coral.core.calendar.Calendar;
import com.deep.coral.core.calendar.CalendarFactory;
import com.deep.coral.core.exception.TaskConfigException;
import com.deep.coral.core.util.CronUtils;
import com.deep.coral.enums.enums.CalendarEnum;
import com.deep.coral.enums.enums.HolidayEnum;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <h2>日历任务回归计时器</h2>
 *
 * @author Create by liuwenhao on 2022/3/29 16:20
 */
public class HolidayRegression implements Regression {

    /**
     * 历制类型，整个配置依照公历或者农历，范围覆盖到 month，day，hour，minute，second
     */
    CalendarEnum calendarEnum;

    /**
     * 节日
     */
    HolidayEnum holiday;

    /**
     * 上一次的时间
     */
    LocalDateTime lastTime;

    /**
     * 下一次执行的时间点。
     */
    LocalDateTime nextTime;

    /**
     * 任务执行的初始时间
     */
    final LocalDateTime firstTime;

    /**
     * 记录次数
     */
    AtomicInteger count = new AtomicInteger(0);


    public HolidayRegression(CalendarEnum calendarEnum, HolidayEnum holiday) {
        this.calendarEnum = calendarEnum;
        this.holiday = holiday;
        this.lastTime = LocalDateTime.now();
        this.firstTime = CalendarFactory.calendarBuilder(calendarEnum).getDateTime();
        this.nextTime = explainCron(holiday.getCron());
    }

    @Override
    public Long getInitialDelay() {
        this.lastTime = LocalDateTime.now();
        return LocalDateTimeUtil.between(lastTime, nextTime, ChronoUnit.NANOS);
    }

    @Override
    public Long getInterval() {
        // 重新同步时间
        this.lastTime = this.nextTime;
        this.nextTime = explainCron(holiday.getCron());
        // 当前时间距表达式解析后的时间的间隔
        return LocalDateTimeUtil.between(lastTime, nextTime, ChronoUnit.NANOS);
    }

    /**
     * <h2>解析cron表达式</h2>
     * cron表达式只能达到秒级的准确度
     *
     * @param exp 表达式
     * @return long
     * @author liuwenhao
     * @date 2022/2/9 14:23
     */
    private LocalDateTime explainCron(String exp) {
        boolean checkValid = CronUtils.checkValid(exp);
        if (!checkValid) {
            TaskConfigException.of("表达式 - 【{}】 不合法", exp);
        }
        // 下次执行的时间点
        int incrementAndGet = count.incrementAndGet();
        Date execTime = CronUtils.getNumTimeExecTime(exp, this.firstTime, incrementAndGet);

        // 根据日历类型改造成对应的时间
        Calendar calendar = CalendarFactory.calendarBuilder(calendarEnum, execTime);
        return calendar.getDateTime();
    }

}