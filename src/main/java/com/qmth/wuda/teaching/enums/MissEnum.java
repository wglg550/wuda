package com.qmth.wuda.teaching.enums;

/**
 * @Description: 缺考enum
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/12/7
 */
public enum MissEnum {

    TRUE(1),

    FALSE(0);

    Integer value;

    private MissEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
