package com.deep.coral.core.manager;

import com.deep.coral.core.exception.TaskConfigException;
import com.deep.coral.core.task.BasicTask;
import com.deep.coral.core.task.TaskGuardian;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <h2>任务管理器，存放所有的任务</h2>
 *
 * @author Create by liuwenhao on 2022/2/9 16:03
 */
public class TaskManager {

    /**
     * 全部的任务
     */
    Set<TaskGuardian> tasks = new HashSet<>();

    AtomicBoolean isBegin = new AtomicBoolean(Boolean.FALSE);

    /**
     * BasicTask  --  TaskGuardian
     */
    Map<BasicTask, TaskGuardian> guardianMap;

    /**
     * <h2>获取任务是否已经开始执行</h2>
     *
     * @return java.lang.Boolean
     * @author liuwenhao
     * @date 2022/4/1 1:44
     */
    public Boolean getIsBegin() {
        return isBegin.get();
    }

    public void close() {
        isBegin.getAndSet(Boolean.FALSE);
    }

    /**
     * <h2>开始执行任务</h2>
     * <p>所有的任务依次执行，如果遇到已经开始的任务则会抛出异常
     *
     * @author liuwenhao
     * @date 2022/2/9 16:50
     */
    public void begin() {
        List<BasicTask> basicTasks = this.tasks.stream()
            .map(TaskGuardian::getTask)
            .collect(Collectors.toList());

        this.guardianMap = tasks.stream().collect(Collectors.toMap(
            TaskGuardian::getTask,
            Function.identity(),
            (f, l) -> l));

        TaskQueue queue = new TaskQueue(basicTasks);
        boolean compareAndSet = isBegin.compareAndSet(Boolean.FALSE, Boolean.TRUE);
        if (!compareAndSet) {
            TaskConfigException.of("任务已开始执行，无法重复开始");
        }
        dispatch(queue);

    }

    /**
     * <h2>执行任务调度</h2>
     *
     * @param queue 任务队列
     * @author liuwenhao
     * @date 2022/4/1 1:47
     */
    private void dispatch(TaskQueue queue) {
        CompletableFuture.runAsync(() -> {
            while (Boolean.TRUE.equals(getIsBegin())) {
                try {
                    BasicTask take = queue.take();
                    if (!take.isStop()) {
                        TaskGuardian guardian = guardianMap.get(take);
                        if (Objects.nonNull(guardian)) {
                            guardian.schedule();
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * <h2>新增一个任务</h2>
     * 覆盖name相同的任务
     *
     * @param task 任务
     * @return com.example.juc.timer.scale.TaskManager
     * @author liuwenhao
     * @date 2022/2/9 17:39
     */
    public TaskManager addTask(TaskGuardian task) {
        this.tasks.add(task);
        return this;
    }

    /**
     * <h2>新增一个任务</h2>
     * 不覆盖name相同的任务
     *
     * @param task 任务
     * @return com.example.juc.timer.scale.TaskManager
     * @author liuwenhao
     * @date 2022/2/9 17:39
     */
    public TaskManager addTaskNoCover(TaskGuardian task) {
        if (!tasks.contains(task)){
            this.tasks.add(task);
        }
        return this;
    }

    public TaskManager addAllTask(Set<TaskGuardian> tasks) {
        this.tasks.addAll(tasks);
        return this;
    }

    public TaskManager tasks(Set<TaskGuardian> tasks) {
        this.tasks = tasks;
        return this;
    }

    public Set<TaskGuardian> getTasks() {
        return tasks;
    }

    public void setTasks(Set<TaskGuardian> tasks) {
        this.tasks = tasks;
    }

    /**
     * <h2>获取所有正在执行的任务</h2>
     *
     * @return java.util.List<cn.net.nova.pim.component.timer.scale.Task>
     * @author liuwenhao
     * @date 2022/2/14 11:33
     */
    public Set<TaskGuardian> getStartTask() {
        return tasks.stream().map(t -> {
            if (Objects.nonNull(t.getTask()) && t.getTask().isStart()) {
                return t;
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    /**
     * <h2>获取所有已经结束的任务</h2>
     *
     * @return java.util.List<cn.net.nova.pim.component.timer.scale.Task>
     * @author liuwenhao
     * @date 2022/2/14 11:33
     */
    public Set<TaskGuardian> getStopTask() {
        return tasks.stream().map(t -> {
            if (Objects.nonNull(t.getTask()) && t.getTask().isStop()) {
                return t;
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    /**
     * <h2>通过任务标识获取一个任务</h2>
     *
     * @param name 任务标识
     * @return cn.net.nova.pim.component.timer.scale.Task
     * @author liuwenhao
     * @date 2022/2/25 17:28
     */
    public TaskGuardian getTask(String name) {
        Set<TaskGuardian> copyTasks = tasks;
        for (TaskGuardian task : copyTasks) {
            if (task.getName().equals(name)) {
                return task;
            }
        }
        return null;
    }

    /**
     * <h2>判断任务是否存在</h2>
     * true:任务存在；
     * false：任务不存在。
     *
     * @param name 任务标识
     * @return boolean
     * @author liuwenhao
     * @date 2022/4/5 1:40
     */
    public boolean checkTask(String name) {
        TaskGuardian task = getTask(name);
        return Objects.nonNull(task);
    }

}