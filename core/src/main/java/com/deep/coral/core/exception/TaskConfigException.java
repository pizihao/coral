package com.deep.coral.core.exception;

import cn.hutool.core.text.StrFormatter;

/**
 * <h2>定制化异常</h2>
 *
 * @author Create by liuwenhao on 2022/1/24 16:33
 */
public class TaskConfigException extends RuntimeException {
    private static final long serialVersionUID = 4220666905005394823L;

    public TaskConfigException() {
        super();
    }

    public TaskConfigException(String message) {
        super(message);
    }

    public static TaskConfigException exception(String message) {
        return new TaskConfigException(message);
    }

    public static TaskConfigException exception(String format, Object... elements) {
        return new TaskConfigException(StrFormatter.format(format, elements));
    }

    public static TaskConfigException of(String msg) {
        throw TaskConfigException.exception(msg);
    }

    public static TaskConfigException of(String format, Object... elements) {
        throw TaskConfigException.exception(format, elements);
    }

}