package com.deep.coral.core.task;

import com.deep.coral.core.regression.HolidayRegression;
import com.deep.coral.enums.enums.CalendarEnum;
import com.deep.coral.enums.enums.CycleUnit;
import com.deep.coral.enums.enums.HolidayEnum;
import com.deep.coral.enums.enums.TaskType;

/**
 * <h2>对应{@link TaskType}中的HOLIDAY</h2>
 * 通过配置的节气信息来确定任务的执行时间
 *
 * @author Create by liuwenhao on 2022/3/29 16:03
 */
public class HolidayTask extends AbstractTask {

    /**
     * 循环单位，年月周日时分秒，一次循环的最小单位，如每年，每月，每周，每天等
     */
    CycleUnit cycleUnit;

    /**
     * 历制类型，整个配置依照公历或者农历，范围覆盖到 month，day，hour，minute，second
     */
    CalendarEnum calendarEnum;

    /**
     * 节日
     */
    HolidayEnum holiday;

    public HolidayTask(HolidayEnum holiday, boolean isOnce) {
        this.cycleUnit = isOnce ? CycleUnit.ONCE : CycleUnit.YEAR;
        this.holiday = holiday;
        this.calendarEnum = holiday.getCode() == 0 ? CalendarEnum.LUNAR : CalendarEnum.GREGORIAN;
        this.regression = new HolidayRegression(calendarEnum, holiday);
    }

    @Override
    public boolean isPeriodic() {
        return !cycleUnit.equals(CycleUnit.ONCE);
    }
}