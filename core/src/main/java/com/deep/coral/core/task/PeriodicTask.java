package com.deep.coral.core.task;

/**
 * 任务的执行规则，是否循环执行
 *
 * @author Create by liuwenhao on 2022/3/30 10:46
 */
public interface PeriodicTask {

    /**
     * true：循环任务；
     * false：单次任务；
     *
     * @return boolean
     * @author liuwenhao
     * @date 2022/3/30 10:47
     */
    boolean isPeriodic();

}