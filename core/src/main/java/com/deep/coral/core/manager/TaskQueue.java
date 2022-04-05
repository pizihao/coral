package com.deep.coral.core.manager;

import com.deep.coral.core.task.BasicTask;
import java.util.Collection;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

/**
 * <h2>任务调度管理</h2>
 * 继承{@link DelayQueue}，重新移出元素的方法，之后重新加入队列
 *
 * @author Create by liuwenhao on 2022/3/31 23:19
 */
public class TaskQueue extends DelayQueue<BasicTask> {

    public TaskQueue() {
    }

    public TaskQueue(Collection<? extends BasicTask> c) {
        super(c);
    }

    @Override
    public BasicTask poll() {
        BasicTask poll = super.poll();
        return againIgnition(poll);
    }

    @Override
    public BasicTask take() throws InterruptedException {
        BasicTask take = super.take();
        return againIgnition(take);
    }

    @Override
    public BasicTask poll(long timeout, TimeUnit unit) throws InterruptedException {
        BasicTask poll = super.poll(timeout, unit);
        return againIgnition(poll);
    }

    /**
     * <h2>检查任务是否可以重新执行</h2>
     *
     * @param task BasicTask
     * @return true：当前任务可以执行，false：当前任务不可继续执行
     * @author liuwenhao
     * @date 2022/4/1 0:47
     */
    private BasicTask againIgnition(BasicTask task) {
        if (task == null) {
            return null;
        }
        // true：任务需要停止，false：任务无需停止
        boolean isStop = task.isStop();
        if (!isStop) {
            // 获取之后重新加入队列
            put(task);
        }
        return task;
    }


    @Override
    public int drainTo(Collection<? super BasicTask> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int drainTo(Collection<? super BasicTask> c, int maxElements) {
        throw new UnsupportedOperationException();
    }
}