package com.deep.coral.web.explain;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ReflectUtil;
import com.deep.coral.annotations.Schedule;
import com.deep.coral.core.task.BasicTask;
import com.deep.coral.core.task.CalendarTask;
import com.deep.coral.core.task.TaskGuardian;
import com.deep.coral.enums.enums.CalendarEnum;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * <h2>解析 Schedule 注解</h2>
 *
 * @author Create by liuwenhao on 2022/2/10 11:23
 */
@Component
@ConditionalOnBean(ScheduledThreadPoolExecutor.class)
public class ExplainSimple implements Explain {

    private static final Logger logger = LoggerFactory.getLogger(ExplainSimple.class);
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    ScheduledThreadPoolExecutor executor;

    @Override
    public List<TaskGuardian> explain(String[] packageName) {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
            .forPackages(packageName)
            .addScanners(new SubTypesScanner())
            .addScanners(new MethodAnnotationsScanner()));
        Set<Method> methods = reflections.getMethodsAnnotatedWith(Schedule.class);
        return methods.stream().map(this::getTask).collect(Collectors.toList());
    }

    /**
     * <h2>生成task</h2>
     *
     * @param method 被标注的方法
     * @return com.example.juc.timer.scale.Task
     * @author liuwenhao
     * @date 2022/2/10 11:44
     */
    private TaskGuardian getTask(Method method) {
        // 获取注解
        Schedule schedule = method.getAnnotation(Schedule.class);
        String name = schedule.name();
        if (CharSequenceUtil.isBlank(name)) {
            name = method.getClass().getSimpleName() + "#" + method.getName();
        }
        BasicTask taskConfig = getTaskConf(schedule);

        // 任务体
        try {
            Object obj = applicationContext.getBean(method.getDeclaringClass());
            Runnable runnable = () -> ReflectUtil.invoke(obj, method);
            return TaskGuardian.build(executor, name).task(taskConfig).runnable(runnable);
        } catch (Exception e) {
            logger.info("定时任务启动失败，{}#{}", method.getClass().getName(), method.getName());
        }
        return null;
    }

    /**
     * <h2>生成 TaskConfig</h2>
     *
     * @param schedule Schedule
     * @return cn.net.nova.pim.component.timer.scale.TaskConfig
     * @author liuwenhao
     * @date 2022/2/11 16:43
     */
    private BasicTask getTaskConf(Schedule schedule) {
        CalendarEnum calendarEnum = schedule.calendarEnum();
        int year = schedule.year();
        int month = schedule.month();
        int day = schedule.day();
        int hour = schedule.hour();
        int minute = schedule.minute();
        int second = schedule.second();
        boolean isMonth = schedule.isMonth();

        if (minute == -1) {
            return new CalendarTask(calendarEnum, second);
        } else if (hour == -1) {
            return new CalendarTask(calendarEnum, minute, second);
        } else if (day == -1) {
            return new CalendarTask(calendarEnum, hour, minute, second);
        } else if (month == -1) {
            return new CalendarTask(calendarEnum, day, hour, minute, second, isMonth);
        } else if (year == -1) {
            return new CalendarTask(calendarEnum, month, day, hour, minute, second);
        } else {
            return new CalendarTask(calendarEnum, year, month, day, hour, minute, second);
        }
    }
}