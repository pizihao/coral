package com.deep.coral.enums.enums;

/**
 * <h2>任务类型</h2>
 *
 * @author Create by liuwenhao on 2022/3/25 10:08
 */
public enum TaskType {

    /**
     * 循环定时任务，通过一个具体的间隔时间来引导任务的寻循环执行
     */
    RATE,

    /**
     * 按照节日执行的任务，通常为一年一次
     */
    HOLIDAY,

    /**
     * 通过一个cron表达式来执行定时任务
     */
    CRON,

    /**
     * 按照最普通的方式确定一个任务需要执行的时间点，需要准确的年月日时分秒
     */
    SIMPLE,
    ;

}