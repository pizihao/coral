package com.deep.coral.core.task;

import com.deep.coral.core.exception.TaskConfigException;
import com.deep.coral.core.regression.CalendarRegression;
import com.deep.coral.enums.enums.CalendarEnum;
import com.deep.coral.enums.enums.CycleUnit;
import com.deep.coral.enums.enums.TaskType;

import java.time.LocalDate;

/**
 * <h2>对应{@link TaskType}中的SIMPLE
 * 直接对接日历表，
 *
 * @author Create by liuwenhao on 2022/3/28 16:46
 */
public class CalendarTask extends AbstractTask {

    /**
     * 占位符，用于作为时间单位不合格时的校验对象
     */
    static int place = -1;

    /**
     * 循环单位，年月周日时分秒，一次循环的最小单位，如每年，每月，每周，每天等
     */
    CycleUnit cycleUnit;

    /**
     * 历制类型，整个配置依照公历或者农历，范围覆盖到 month，day，hour，minute，second
     */
    CalendarEnum calendarEnum;

    public CalendarTask(CalendarEnum calendarEnum, int year, int month, int day, int hour, int minute, int second) {
        this.cycleUnit = CycleUnit.ONCE;
        this.calendarEnum = calendarEnum;
        if (LocalDate.now().getYear() > year || month <= 0 || day <= 0) {
            TaskConfigException.of("请注意时间格式 --> year：{}，month：{}，day：{}", year, month, day);
        }
        this.regression = new CalendarRegression(cycleUnit, second, minute, hour, day, month, year, calendarEnum);
    }

    public CalendarTask(CalendarEnum calendarEnum, int month, int day, int hour, int minute, int second) {
        this.cycleUnit = CycleUnit.YEAR;
        this.calendarEnum = calendarEnum;
        if (month <= 0 || day <= 0) {
            TaskConfigException.of("请注意时间格式 --> month：{}，day：{}", month, day);
        }
        this.regression = new CalendarRegression(cycleUnit, second, minute, hour, day, month, place, calendarEnum);
    }

    public CalendarTask(CalendarEnum calendarEnum, int day, int hour, int minute, int second, boolean isMonth) {
        this.cycleUnit = isMonth ? CycleUnit.MONTH : CycleUnit.WEEK;
        this.calendarEnum = calendarEnum;
        if (day <= 0) {
            TaskConfigException.of("请注意时间格式 --> day：{}", day);
        }
        this.regression = new CalendarRegression(cycleUnit, second, minute, hour, day, place, place, calendarEnum);
    }

    public CalendarTask(CalendarEnum calendarEnum, int hour, int minute, int second) {
        this.cycleUnit = CycleUnit.DAY;
        this.calendarEnum = calendarEnum;

        this.regression = new CalendarRegression(cycleUnit, second, minute, hour, place, place, place, calendarEnum);
    }

    public CalendarTask(CalendarEnum calendarEnum, int minute, int second) {
        this.cycleUnit = CycleUnit.HOUR;
        this.calendarEnum = calendarEnum;
        this.regression = new CalendarRegression(cycleUnit, second, minute, place, place, place, place, calendarEnum);
    }

    public CalendarTask(CalendarEnum calendarEnum, int second) {
        this.cycleUnit = CycleUnit.MINUTE;
        this.calendarEnum = calendarEnum;
        this.regression = new CalendarRegression(cycleUnit, second, place, place, place, place, place, calendarEnum);
    }

    @Override
    public boolean isPeriodic() {
        return !cycleUnit.equals(CycleUnit.ONCE);
    }
}