package com.deep.coral.core.util;


import com.cronutils.descriptor.CronDescriptor;
import com.cronutils.model.Cron;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;
import org.quartz.TriggerUtils;
import org.quartz.impl.triggers.CronTriggerImpl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.cronutils.model.CronType.QUARTZ;

/**
 * <h2></h2>
 *
 * @author Create by liuwenhao on 2022/3/27 10:33
 */
public class CronUtils {

    private CronUtils() {

    }

    /**
     * 解释cron表达式
     */
    public static String describeCron(String exp) {
        CronDefinition cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(QUARTZ);
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse(exp);
        //设置语言
        CronDescriptor descriptor = CronDescriptor.instance(Locale.CHINESE);
        return descriptor.describe(cron);
    }

    /**
     * 检查cron表达式的合法性
     *
     * @param cron cron exp
     * @return true if valid
     */
    public static boolean checkValid(String cron) {
        try {
            CronDefinition cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(QUARTZ);
            CronParser parser = new CronParser(cronDefinition);
            parser.parse(cron);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    /**
     * <h2>获取任务后续执行的时间点</h2>
     *
     * @param cronExpression cron表达式
     * @param num            获取未来的几次执行
     * @return List<Date>
     * @author liuwenhao
     * @date 2022/3/30 11:50
     */
    public static List<Date> getNextExecTime(String cronExpression, Integer num) {
        CronTriggerImpl cronTriggerImpl = new CronTriggerImpl();
        try {
            cronTriggerImpl.setCronExpression(cronExpression);
        } catch (Exception exception) {
            // 注入失败等同于任务未来不会执行
            return Collections.emptyList();
        }
        // 该方法返回一个不可修改的视图对象
        return TriggerUtils.computeFireTimes(cronTriggerImpl, null, num);
    }

    /**
     * <h2>获取任务n次执行后的的时间点</h2>
     *
     * @param cronExpression cron表达式
     * @param startTime      开始时间
     * @param num            获取未来的几次执行
     * @return List<Date>
     * @author liuwenhao
     * @date 2022/3/30 11:50
     */
    public static Date getNumTimeExecTime(String cronExpression, LocalDateTime startTime, Integer num) {
        Date date = Date.from(startTime.atZone(ZoneId.systemDefault()).toInstant());
        CronTriggerImpl cronTriggerImpl = new CronTriggerImpl();
        try {
            cronTriggerImpl.setCronExpression(cronExpression);
            cronTriggerImpl.setStartTime(date);
        } catch (Exception exception) {
            // 注入失败等同于任务未来不会执行
            return null;
        }
        // 该方法返回一个不可修改的视图对象
        return TriggerUtils.computeEndTimeToAllowParticularNumberOfFirings(cronTriggerImpl, null, num);
    }

    /**
     * <h2>获取下一次任务的执行时间</h2>
     * 如果无法获取下次任务的执行时间则返回null
     *
     * @param cronExpression cron表达式
     * @return java.util.Date
     * @author liuwenhao
     * @date 2022/3/30 13:12
     */
    public static Date getNextExecTime(String cronExpression) {
        return getNextExecTime(cronExpression, 1).get(0);
    }


}