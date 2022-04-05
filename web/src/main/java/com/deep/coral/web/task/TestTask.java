package com.deep.coral.web.task;

import com.deep.coral.annotations.Schedule;
import com.deep.coral.core.helper.TaskHelper;
import com.deep.coral.core.manager.TaskManager;
import com.deep.coral.core.task.BasicTask;
import com.deep.coral.core.task.TaskGuardian;
import com.deep.coral.enums.enums.CalendarEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <h2></h2>
 *
 * @author Create by liuwenhao on 2022/4/1 1:56
 */
@Component
public class TestTask {
    @Autowired
    TaskManager taskManager;
    @Autowired
    ScheduledThreadPoolExecutor executor;
    Integer integer = 1;

    private static final Logger logger = LoggerFactory.getLogger(TestTask.class);

    //        @ScheduleRate(name = TestConstant.STRING, timeUnit = TimeUnit.SECONDS, initialDelay = 2, interval = 10)
//    @ScheduleCron(name = TestConstant.STRING, cron = "0,10,20,30,40,50 * * * * ? ")
//    @ScheduleHoliday(name = TestConstant.STRING, holiday = HolidayEnum.THE_RAIN, isOnce = true)
    @Schedule(name = TestConstant.STRING, second = 20)
    public void refresh() {
        System.out.println(LocalDateTime.now());


        if (integer > 3) {
            TaskGuardian task = taskManager.getTask(TestConstant.STRING);
            task.stop();
        }
        integer = integer + 1;

        System.out.println(integer);

        TaskManager builder = TaskHelper.builder();
        BasicTask basicTask = TaskHelper.calendarBuilder(CalendarEnum.GREGORIAN, 5);
        TaskGuardian guardian = TaskHelper.builder(executor, TestConstant.STRING)
            .runnable(() -> System.out.println(LocalDateTime.now()))
            .task(basicTask);
        builder.addTask(guardian).begin();
    }

    public void refresh(String s) {
        AtomicInteger num = new AtomicInteger();

        TaskManager builder = TaskHelper.builder();
        BasicTask basicTask = TaskHelper.calendarBuilder(CalendarEnum.GREGORIAN, 5);
        TaskGuardian guardian = TaskHelper.builder(executor, TestConstant.STRING)
            .runnable(num::incrementAndGet)
            .task(basicTask);
        builder.addTask(guardian).begin();

        if (num.get() > 3) {
            builder.close();
        }

    }

}