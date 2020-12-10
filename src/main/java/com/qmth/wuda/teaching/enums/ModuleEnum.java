package com.qmth.wuda.teaching.enums;

import java.util.Objects;

/**
 * @Description: 模块enum
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/12/8
 */
public enum ModuleEnum {

    KNOWLEDGE("知识"),

    CAPABILITY("能力");

    String code;

    private ModuleEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    /**
     * 状态转换 toName
     *
     * @param value
     * @return
     */
    public static String convertToName(String value) {
        for (ModuleEnum e : ModuleEnum.values()) {
            if (Objects.equals(e.getCode(), value)) {
                return e.name().toLowerCase();
            }
        }
        return null;
    }

    /**
     * 状态转换 toCode
     *
     * @param value
     * @return
     */
    public static String convertToCode(String value) {
        for (ModuleEnum e : ModuleEnum.values()) {
            if (Objects.equals(e.name(), value.toUpperCase())) {
                return e.getCode();
            }
        }
        return null;
    }
}
