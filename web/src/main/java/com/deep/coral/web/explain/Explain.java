package com.deep.coral.web.explain;


import com.deep.coral.core.task.TaskGuardian;

import java.util.List;

/**
 * <h2>注解解析器</h2>
 *
 * @author Create by liuwenhao on 2022/2/10 11:08
 */
public interface Explain {

    /**
     * <h2>扫描指定的包名，解析并生成对应的任务</h2>
     *
     * @param packageName 要扫描的包名
     * @return com.example.juc.timer.scale.TaskConfig
     * @author liuwenhao
     * @date 2022/2/10 11:11
     */
    List<TaskGuardian> explain(String[] packageName);


}
