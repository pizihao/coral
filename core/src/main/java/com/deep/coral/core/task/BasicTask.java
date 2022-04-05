package com.deep.coral.core.task;

import java.util.concurrent.Delayed;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <h2>基本的任务，目的是对接{@link ScheduledThreadPoolExecutor}</h2>
 * 首次延迟时间，间隔时间和计时单位<br>
 * 对于大部分计时任务来说，首次延迟时间是一个很重要的量，因为该任务存在一个相对的时间量来作为间隔时间<br>
 * 而对于另一部分，使用一个绝对时间来确定每次任务执行的时间点，则可以通过当前时间来计算出下次执行的时间<br>
 * 这一切都是独立于{@link ScheduledThreadPoolExecutor}进行的，需要保持其实时性，必要时需使用新的线程进行
 *
 * @author Create by liuwenhao on 2022/3/30 10:54
 */
public interface BasicTask extends GuardianTask, Delayed {

    /**
     * <h2>两次相邻任务之间的间隔时间</h2>
     * 如果返回值 <0,则表示任务应剔除队列<br>
     * 时间单元取{@link #getTimeUnit()}<br>
     *
     * @return long interval
     * @author liuwenhao
     * @date 2022/3/30 11:01
     */
    long getInterval();

    /**
     * <h2>获取定时任务的计时单元</h2>
     *
     * @return java.util.concurrent.TimeUnit
     * @author liuwenhao
     * @date 2022/3/28 14:23
     */
    TimeUnit getTimeUnit();

}
