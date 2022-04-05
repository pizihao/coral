package com.deep.coral.core.task;


import com.deep.coral.core.regression.Regression;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * <h2></h2>
 * 所有的时间单元都会转化为纳秒进行运算
 *
 * @author Create by liuwenhao on 2022/3/28 17:21
 */
public abstract class AbstractTask extends SwitchTask implements BasicTask {

    /**
     * 单位，固定延时时间和任务间隔时间的时间刻度单位，最大为天
     */
    TimeUnit timeUnit;

    /**
     * 计时器
     */
    Regression regression;

    /**
     * 计时器，任务执行次数
     */
    AtomicLong counter = new AtomicLong(0);

    /**
     * 总量,代表任务等待释放的时间总长度,初始化时为当前的纳秒量
     */
    long total = System.nanoTime();

    @Override
    public Long count() {
        return counter.get();
    }

    @Override
    public void countIncrease() {
        counter.incrementAndGet();
    }

    @Override
    public long getInterval() {
        long delay = 0;
        if (count() == 0) {
            // 任务首次执行
            delay = regression.getInitialDelay();
        } else {
            delay = regression.getInterval();
        }
        total = total + delay;
        return delay;
    }

    @Override
    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(total - System.nanoTime(), NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        if (o == this) {
            return 0;
        }
        long diff = this.getDelay(timeUnit) - o.getDelay(timeUnit);
        if (diff == 0) {
            return 0;
        }
        return diff > 0L ? 1 : -1;
    }

}