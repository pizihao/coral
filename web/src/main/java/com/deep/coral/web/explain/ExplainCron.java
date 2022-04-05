package com.deep.coral.web.explain;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ReflectUtil;
import com.deep.coral.annotations.ScheduleCron;
import com.deep.coral.core.task.BasicTask;
import com.deep.coral.core.task.CronTask;
import com.deep.coral.core.task.TaskGuardian;
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
 * <h2>解析 ScheduleCron 注解</h2>
 *
 * @author Create by liuwenhao on 2022/2/10 11:24
 */
@Component
@ConditionalOnBean(ScheduledThreadPoolExecutor.class)
public class ExplainCron implements Explain {

    private static final Logger logger = LoggerFactory.getLogger(ExplainCron.class);
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
        Set<Method> methods = reflections.getMethodsAnnotatedWith(ScheduleCron.class);
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
        ScheduleCron schedule = method.getAnnotation(ScheduleCron.class);
        String cron = schedule.cron();
        String name = schedule.name();
        if (CharSequenceUtil.isBlank(name)) {
            name = method.getClass().getSimpleName() + "#" + method.getName();
        }
        // 任务周期或循环配置
        BasicTask taskConfig = new CronTask(cron);
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

}