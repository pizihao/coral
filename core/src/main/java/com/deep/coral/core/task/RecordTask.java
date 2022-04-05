package com.deep.coral.core.task;

/**
 * <h2>记录任务的执行次数</h2>
 *
 * @author Create by liuwenhao on 2022/3/30 10:51
 */
public interface RecordTask {

    /**
     * <h2>任务的执行次数</h2>
     *
     * @return java.lang.Long
     * @author liuwenhao
     * @date 2022/3/27 19:15
     */
    Long count();

    /**
     * <h2>任务执行次数+1</h2>
     *
     * @author liuwenhao
     * @date 2022/3/27 19:15
     */
    void countIncrease();

}