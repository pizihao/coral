package com.deep.coral.core.task;


import com.deep.coral.core.regression.CronRegression;
import com.deep.coral.enums.enums.TaskType;

import java.util.concurrent.TimeUnit;

/**
 * <h2>对应{@link TaskType}中的CRON</h2>
 * 通过Cron表达式来确定任务的执行时间
 *
 * @author Create by liuwenhao on 2022/3/28 16:46
 */
public class CronTask extends AbstractTask {

    /**
     * cron表达式
     */
    String cron;

    public CronTask(String cron) {
        this.cron = cron;
        regression = new CronRegression(cron);
        this.timeUnit = TimeUnit.SECONDS;
    }

    @Override
    public boolean isPeriodic() {
        return true;
    }

}