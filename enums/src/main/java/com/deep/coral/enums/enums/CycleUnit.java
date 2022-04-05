package com.deep.coral.enums.enums;

import java.time.LocalDateTime;

/**
 * <h2>循环单位枚举</h2>
 *
 * @author Create by liuwenhao on 2022/1/20 11:40
 */
public enum CycleUnit {

    /**
     * 任务只执行一次
     */
    ONCE,

    /**
     * 年
     */
    YEAR {
        @Override
        public LocalDateTime recast(LocalDateTime c, int n) {
            // 每年执行一次，获取n个年之后的时间
            return c.plusYears(n);
        }
    },

    /**
     * 月
     */
    MONTH {
        @Override
        public LocalDateTime recast(LocalDateTime c, int n) {
            // 每月执行一次，获取n个月之后的时间
            return c.plusMonths(n);
        }
    },

    /**
     * 周
     */
    WEEK {
        @Override
        public LocalDateTime recast(LocalDateTime c, int n) {
            // 每周执行一次，获取n个周之后的时间
            return c.plusWeeks(n);
        }
    },

    /**
     * 日
     */
    DAY {
        @Override
        public LocalDateTime recast(LocalDateTime c, int n) {
            // 每天执行一次，获取n个天之后的时间
            return c.plusDays(n);
        }
    },

    /**
     * 时
     */
    HOUR {
        @Override
        public LocalDateTime recast(LocalDateTime c, int n) {
            // 每小时执行一次，获取n个小时之后的时间
            return c.plusHours(n);
        }
    },

    /**
     * 分
     */
    MINUTE {
        @Override
        public LocalDateTime recast(LocalDateTime c, int n) {
            // 每分钟执行一次，获取n个分钟之后的时间
            return c.plusMinutes(n);
        }
    },
    ;

    /**
     * <h2>对 t 中的数据进行重置更新</h2>
     * 重置更新的量有：年，月，日，时，分，秒
     *
     * @param c 一个已经定位好的时间LocalDateTime
     * @param n 各个单位向后延迟的次数，如：单位为月，就会向后数 n 个月
     * @return 重新定位好的时间
     * @author liuwenhao
     * @date 2022/2/17 14:48
     */
    public LocalDateTime recast(LocalDateTime c, int n) {
        // not do
        return c;
    }


}