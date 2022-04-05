package com.deep.coral.core.task;

import com.deep.coral.core.regression.RateRegression;
import com.deep.coral.enums.enums.TaskType;

import java.util.concurrent.TimeUnit;

/**
 * <h2>对应{@link TaskType}中的RATE</h2>
 * 一个周期性极强的定时任务，按照严格的时间间隔循环执行
 *
 * @author Create by liuwenhao on 2022/3/28 14:14
 */
public class RateTask extends AbstractTask {

    /**
     * 延迟时间，任务被调度到任务第一次执行等待的时间，单位取timeUnit
     */
    long initialDelay;

    /**
     * 间隔时间，两次任务执行的时间间隔，单位取timeUnit，指定周期循环时起效
     */
    long interval;

    public RateTask(TimeUnit timeUnit, long initialDelay, long interval) {
        this.timeUnit = timeUnit;
        this.initialDelay = initialDelay;
        this.interval = interval;
        this.regression = new RateRegression(interval, initialDelay, timeUnit);
    }

    @Override
    public boolean isPeriodic() {
        return true;
    }

}