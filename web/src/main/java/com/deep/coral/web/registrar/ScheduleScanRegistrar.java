package com.deep.coral.web.registrar;

import cn.hutool.core.util.ArrayUtil;
import com.deep.coral.core.manager.TaskManager;
import com.deep.coral.core.task.TaskGuardian;
import com.deep.coral.web.annotations.ScheduleScan;
import com.deep.coral.web.explain.Explain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <h2>注册器</h2>
 *
 * @author Create by liuwenhao on 2022/4/5 10:30
 */
public class ScheduleScanRegistrar implements BeanFactoryAware, ImportBeanDefinitionRegistrar {

    Logger logger = LoggerFactory.getLogger(ScheduleScanRegistrar.class);
    BeanFactory beanFactory;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Map<String, Object> attributes = importingClassMetadata.getAnnotationAttributes(ScheduleScan.class.getName());
        AnnotationAttributes mapperScanAttrs = AnnotationAttributes.fromMap(attributes);
        if (mapperScanAttrs != null) {
            List<String> packages = Arrays.stream(mapperScanAttrs.getStringArray("packages"))
                .collect(Collectors.toList());
            RootBeanDefinition definition = new RootBeanDefinition(TaskManager.class, () -> doScan(packages));
            registry.registerBeanDefinition("taskManager", definition);
        }
    }

    /**
     * <h2>扫描Explain</h2>
     *
     * @return java.util.Map<java.lang.String, cn.net.nova.pim.common.enums.$DictEnumNode < ?, ?>>
     * @author liuwenhao
     * @date 2022/1/6 11:36
     */
    public TaskManager doScan(List<String> packages) {
        ObjectProvider<Explain> explains = beanFactory.getBeanProvider(Explain.class);
        String[] strings = ArrayUtil.toArray(packages, String.class);
        TaskManager taskManager = new TaskManager();
        Set<TaskGuardian> tasks = explains.stream()
            .map(e -> e.explain(strings))
            .flatMap(Collection::stream)
            .collect(Collectors.toSet());
        taskManager.addAllTask(tasks).begin();
        return taskManager;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}