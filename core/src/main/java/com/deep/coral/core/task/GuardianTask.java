package com.deep.coral.core.task;

/**
 * <h2>可控任务</h2>
 *
 * @author Create by liuwenhao on 2022/3/25 10:06
 */
public interface GuardianTask extends PeriodicTask, RecordTask {

    /**
     * <h2>停止任务</h2>
     *
     * @author liuwenhao
     * @date 2022/3/28 11:48
     */
    void stop();

    /**
     * <h2>开启任务</h2>
     *
     * @author liuwenhao
     * @date 2022/3/28 11:49
     */
    void start();

    /**
     * <h2>任务是否已经停止</h2>
     *
     * @return true：已经停止
     * false：正在执行
     * @author liuwenhao
     * @date 2022/3/28 11:48
     */
    boolean isStop();

    /**
     * <h2>任务是否已经开始</h2>
     *
     * @return true：已经开始， false：未开始
     * @author liuwenhao
     * @date 2022/3/28 11:49
     */
    boolean isStart();

}