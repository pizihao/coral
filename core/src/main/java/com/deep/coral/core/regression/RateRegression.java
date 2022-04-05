package com.deep.coral.core.regression;

import java.util.concurrent.TimeUnit;

/**
 * <h2>rate 回归时间计时器</h2>
 *
 * @author Create by liuwenhao on 2022/3/25 9:49
 */
public class RateRegression implements Regression {

    private final long interval;

    private final long initialDelay;

    public RateRegression(long interval, long initialDelay, TimeUnit timeUnit) {
        this.interval = timeUnit.toNanos(interval);
        this.initialDelay = timeUnit.toNanos(initialDelay);
    }

    @Override
    public Long getInitialDelay() {
        return initialDelay;
    }

    @Override
    public Long getInterval() {
        return interval;
    }

}