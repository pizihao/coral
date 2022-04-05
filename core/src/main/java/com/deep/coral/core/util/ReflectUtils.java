package com.deep.coral.core.util;

import org.springframework.objenesis.Objenesis;
import org.springframework.objenesis.ObjenesisStd;

/**
 * <h2>实例工具类</h2>
 *
 * @author Create by liuwenhao on 2022/3/27 11:40
 */
public class ReflectUtils {

    private ReflectUtils() {
    }

    /**
     * 判断对象是否为null
     *
     * @param t 需要判断的实例对象
     * @return Boolean 为null返回true，反之返回false
     */
    public static <T> Boolean isNull(T t) {
        return t == null;
    }

    /**
     * 判断对象是否为null，如果为null则实例化一个新的并返回
     *
     * @param t 需要判断的实例对象
     * @return T 不为null的实例或新的实例
     */
    @SuppressWarnings("unchecked")
    public static <T> T newInstanceIsNull(T t) {
        if (Boolean.FALSE.equals(isNull(t))) {
            return t;
        }
        // 优先使用反射调用无参构造器
        try {
            return (T) t.getClass().newInstance();
        } catch (InstantiationException | IllegalAccessException ignored) {
            //没有合适的构造器的情况,使用Objenesis来构建
            Objenesis objenesis = new ObjenesisStd();
            return (T) objenesis.newInstance(t.getClass());
        }
    }

    /**
     * 实例化一个对象并返回
     *
     * @param t 需要判断的实例对象
     * @return T 不为null的实例或新的实例
     */
    public static <T> T newInstance(Class<T> t) {
        // 优先使用反射调用无参构造器
        try {
            return t.newInstance();
        } catch (InstantiationException | IllegalAccessException ignored) {
            //没有合适的构造器的情况,使用Objenesis来构建
            Objenesis objenesis = new ObjenesisStd();
            return objenesis.newInstance(t);
        }
    }

}