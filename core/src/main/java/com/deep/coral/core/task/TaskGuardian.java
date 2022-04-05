package com.deep.coral.core.task;

import com.deep.coral.core.exception.TaskConfigException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <h2>任务控制器</h2>
 * 和任务一对一对应，是任务的监护者，同时也是任务的控制器<br>
 * TaskGuardian需要和TaskManager配合才会生效，仅使用TaskGuardian无法生成定时任务
 *
 * @author Create by liuwenhao on 2022/3/28 15:46
 */
public class TaskGuardian {

    private static final Logger logger = LoggerFactory.getLogger(TaskGuardian.class);

    Lock lock = new ReentrantLock();

    /**
     * name 每个任务的唯一标识，默认为：当前方法所在的类名#方法名
     */
    String name;

    /**
     * 任务实体
     */
    Runnable runnable;

    /**
     * 任务配置，每个配置都会对应一个任务调度周期
     */
    BasicTask task;

    /**
     * 线程池
     */
    ScheduledThreadPoolExecutor executor;

    /**
     * <h2>任务开始执行</h2>
     *
     * @author liuwenhao
     * @date 2022/4/5 1:44
     */
    public void begin() {
        schedule();
    }

    /**
     * <h2>将从任务配置中解析出的任务放入执行器中准备执行</h2>
     * 或是重新开始
     *
     * @author liuwenhao
     * @date 2022/1/21 13:37
     */
    public void schedule() {

        if (runnable == null){
            TaskConfigException.of("任务实体不能为null，请指明可执行的Runnable");
        }
        if (task == null){
            TaskConfigException.of("任务配置不能为null，请指明可执行的BasicTask");
        }

        lock.lock();
        try {
            if (Objects.nonNull(task)) {
                if (task.count() != 0 &&
                    !task.isPeriodic()) {
                    return;
                }
                getTaskRunnable();
                task.start();
            }
        } finally {
            lock.unlock();
        }

    }

    /**
     * <h2>结束执行</h2>
     *
     * @author liuwenhao
     * @date 2022/2/9 15:58
     */
    public void stop() {
        lock.lock();
        try {
            if (Objects.nonNull(task)) {
                task.stop();
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * <h2>根据当前的配置项生成定时任务</h2>
     *
     * @author liuwenhao
     * @date 2022/1/24 15:12
     */
    private void getTaskRunnable() {
        long delay = task.getInterval();
        logger.info("定时任务 - 【{}】 已执行过【{}】次 -- {}", getName(), task.count(), LocalDateTime.now());
        task.countIncrease();
        executor.schedule(runnable, delay, TimeUnit.NANOSECONDS);
    }

    private TaskGuardian(ScheduledThreadPoolExecutor executor, String name) {
        this.executor = executor;
        this.name = name;
    }

    public static TaskGuardian build(ScheduledThreadPoolExecutor executor, String name) {
        return new TaskGuardian(executor, name);
    }

    public TaskGuardian runnable(Runnable runnable) {
        this.runnable = runnable;
        return this;
    }

    public TaskGuardian task(BasicTask task) {
        this.task = task;
        return this;
    }

    public String getName() {
        return name;
    }

    public BasicTask getTask() {
        return task;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TaskGuardian that = (TaskGuardian) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}