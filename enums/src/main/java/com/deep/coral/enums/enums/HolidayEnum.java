package com.deep.coral.enums.enums;

/**
 * <h2>节日和节气</h2>
 * 通过cron表达式和一个类型来确定节日在日历上的位置
 *
 * @author Create by liuwenhao on 2022/1/20 14:35
 */
public enum HolidayEnum {

    /**
     * 大寒
     */
    GREAT_COLD(1, "大寒", "0 0 0 20 1 ? *"),
    /**
     * 雨水
     */
    THE_RAIN(2, "雨水", "0,10,20,30,40,50 * * * * ?"),

    /**
     * 母亲节
     */
    MOTHER_DAY(1, "母亲节", "0 0 0 ? 5 1#2 *"),

    /**
     * 春节
     */
    SPRING_FESTIVAL(0, "春节", "0 0 0 1 1 ? *"),

    /**
     * 元宵节
     */
    LANTERN_FESTIVAL(0, "元宵节", "0 0 0 15 1 ? *");

    /**
     * 历制 0: 农历，1：公历
     */
    private final int code;

    /**
     * 节日或节气名称
     */
    private final String value;

    /**
     * cron表达式,仅适用于公历，通过code进行同等时间的转化
     */
    private final String cron;

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public String getCron() {
        return cron;
    }

    HolidayEnum(int code, String value, String cron) {
        this.code = code;
        this.value = value;
        this.cron = cron;
    }
}
