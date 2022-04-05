package com.deep.coral.core.regression;

/**
 * <h2>计算下一次任务的执行时间</h2>
 * 即任务的回归时间，
 *
 * @author Create by liuwenhao on 2022/3/24 16:36
 */
public interface Regression {

    /**
     * <h2>获取任务首次执行的等待时间</h2>
     * 该方法会在初次执行时被调用，上下文中会记录当前时间 + 结果的时间值
     *
     * @return java.lang.Long
     * @author liuwenhao
     * @date 2022/3/24 16:38
     */
    Long getInitialDelay();

    /**
     * <h2>获取任务下次执行距当前时间的间隔</h2>
     *
     * @return java.lang.Long
     * @author liuwenhao
     * @date 2022/3/24 16:38
     */
    Long getInterval();


}