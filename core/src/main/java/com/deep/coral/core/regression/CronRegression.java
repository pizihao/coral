package com.deep.coral.core.regression;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.deep.coral.core.exception.TaskConfigException;
import com.deep.coral.core.util.CronUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <h2>Cron 任务回归时间计时器</h2>
 *
 * @author Create by liuwenhao on 2022/3/28 17:08
 */
public class CronRegression implements Regression {

    /**
     * cron表达式
     */
    final String cron;

    /**
     * 上一次的时间
     */
    LocalDateTime lastTime;

    /**
     * 下一次执行的时间点。
     */
    LocalDateTime nextTime;

    /**
     * 任务执行的初始时间
     */
    LocalDateTime firstTime = LocalDateTime.now();

    /**
     * 记录次数
     */
    AtomicInteger count = new AtomicInteger(0);

    public CronRegression(String cron) {
        this.cron = cron;
        // 首次执行，和初始化相同
        this.lastTime = LocalDateTime.now();
        this.nextTime = explainCron(cron);
    }

    @Override
    public Long getInitialDelay() {
        this.lastTime = LocalDateTime.now();
        this.firstTime = LocalDateTime.now();
        return LocalDateTimeUtil.between(lastTime, nextTime, ChronoUnit.NANOS);
    }

    @Override
    public Long getInterval() {
        // 重新同步时间
        this.lastTime = this.nextTime;
        this.nextTime = explainCron(cron);
        // 当前时间距表达式解析后的时间的间隔
        return LocalDateTimeUtil.between(lastTime, nextTime, ChronoUnit.NANOS);
    }


    /**
     * <h2>解析cron表达式</h2>
     * cron表达式只能达到秒级的准确度
     *
     * @param exp 表达式
     * @return long
     * @author liuwenhao
     * @date 2022/2/9 14:23
     */
    private LocalDateTime explainCron(String exp) {
        boolean checkValid = CronUtils.checkValid(exp);
        if (!checkValid) {
            TaskConfigException.of("表达式 - 【{}】 不合法", exp);
        }
        // 下次执行的时间点
        int incrementAndGet = count.incrementAndGet();
        Date execTime = CronUtils.getNumTimeExecTime(exp, this.firstTime, incrementAndGet);
        return LocalDateTimeUtil.of(execTime);
    }

}