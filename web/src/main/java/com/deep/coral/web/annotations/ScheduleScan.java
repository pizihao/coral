package com.deep.coral.web.annotations;

import com.deep.coral.web.registrar.ScheduleScanRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <h2>配置字典项的扫描</h2>
 *
 * @author Create by liuwenhao on 2022/3/26 10:29
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ScheduleScanRegistrar.class)
public @interface ScheduleScan {

    /**
     * <h2>配置一个枚举项的扫描</h2>
     *
     * @author liuwenhao
     * @date 2022/3/26 10:32
     */
    String[] packages();

}