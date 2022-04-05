package com.deep.coral.web.conf;

import com.deep.coral.core.manager.TaskManager;
import com.deep.coral.core.task.TaskGuardian;
import com.deep.coral.web.explain.Explain;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <h2>扫描注解，并创建出对应的TaskConfig</h2>
 *
 * @author Create by liuwenhao on 2022/2/10 10:55
 */
//@Configuration
public class ScheduleConfig {

    private static final String[] PACKAGE_NAME = {"com.deep.coral.web.task"};

    /**
     * <h2>扫描对应的包并根据不同的规则生成</h2>
     *
     * @param explains 任务解析器
     * @author liuwenhao
     * @date 2022/2/10 11:04
     */
//    @Bean("taskManager")
//    @ConditionalOnMissingBean
    public TaskManager scanSchedule(List<Explain> explains) {
        TaskManager taskManager = new TaskManager();
        Set<TaskGuardian> tasks = explains.stream()
            .map(e -> e.explain(PACKAGE_NAME))
            .flatMap(Collection::stream)
            .collect(Collectors.toSet());
        taskManager.addAllTask(tasks).begin();
        return taskManager;
    }

}