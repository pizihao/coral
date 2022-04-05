package com.deep.coral.enums.enums;

/**
 * <h2>日历枚举</h2>
 *
 * @author Create by liuwenhao on 2022/1/19 10:53
 */
public enum CalendarEnum {

    /**
     * 农历
     */
    LUNAR(0, "农历"),

    /**
     * 公历
     */
    GREGORIAN(1, "公历");

    private final Integer code;

    private final String value;

    CalendarEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}