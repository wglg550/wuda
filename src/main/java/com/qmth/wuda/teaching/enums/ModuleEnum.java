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

    KNOWLEDGE("知识", "teps.knowledge"),

    CAPABILITY("能力", "teps.capability");

    String code;

    String sql;

    private ModuleEnum(String code, String sql) {
        this.code = code;
        this.sql = sql;
    }

    public String getCode() {
        return code;
    }

    public String getSql() {
        return sql;
    }

    /**
     * 状态转换 toSql
     *
     * @param value
     * @return
     */
    public static String convertToSql(String value) {
        for (ModuleEnum e : ModuleEnum.values()) {
            if (Objects.equals(e.name(), value.toUpperCase())) {
                return e.getSql();
            }
        }
        return null;
    }

    /**
     * 状态转换 toSql
     *
     * @param value
     * @return
     */
    public static String convertToSqlByCode(String value) {
        for (ModuleEnum e : ModuleEnum.values()) {
            if (Objects.equals(e.getCode(), value)) {
                return e.getSql();
            }
        }
        return null;
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
