package com.qmth.wuda.teaching.enums;

import java.util.Objects;

/**
 * @Description: 科目enum
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2021/1/21
 */
public enum CourseEnum {

    PHYSICS("大学物理B（下）", "1001"),

    MATH("线性代数A", "1006");

    private String name;

    private String code;

    private CourseEnum(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    /**
     * 状态转换 toName
     *
     * @param name
     * @return
     */
    public static String convertToCode(String name) {
        for (CourseEnum e : CourseEnum.values()) {
            if (Objects.equals(name, e.getName())) {
                return e.getCode();
            }
        }
        return null;
    }
}
