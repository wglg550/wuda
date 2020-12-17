package com.qmth.wuda.teaching.enums;

/**
 * @Description: 缺考enum
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/12/7
 */
public enum MissEnum {

    /**
     * 正常
     */
    NORMAL(1),

    /**
     * 缺考
     */
    MISS(2),

    /**
     * 违纪
     */
    BREACH(3);

    Integer value;

    private MissEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
