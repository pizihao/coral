package com.deep.coral.core.task;

/**
 * <h2>带有开关的任务实现</h2>
 * 通过两个布尔值的变量来绑定某个任务的开启和关闭<br>
 * 任务开启后不能重复开启，任务关闭后不能重复关闭<br>
 * 已经开启的任务可以关闭，已经关闭的任务也可以重复开启<br>
 *
 * @author Create by liuwenhao on 2022/3/30 10:20
 */
public abstract class SwitchTask implements GuardianTask {

    /**
     * 任务是否已经开始，isStart被置为true时无法更换为false，直到任务被抛弃
     */
    private volatile Boolean isStart = false;

    /**
     * 任务是否已被停止
     */
    private volatile Boolean isStop = false;

    @Override
    public void stop() {
        synchronized (isStop) {
            if (isStop()) {
                return;
            }
            isStop = true;
        }
    }

    @Override
    public void start() {
        synchronized (isStop) {
            if (isStart()) {
                return;
            }
            isStop = false;
            isStart = true;
        }
    }

    @Override
    public boolean isStop() {
        return isStop;
    }

    @Override
    public boolean isStart() {
        return isStart;
    }
}