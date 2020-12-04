package com.qmth.wuda.teaching.enums;

import java.util.Objects;

/**
 * @Description: 上传文件类型
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/7/15
 */
public enum UploadFileEnum {
    /**
     * file
     */
    file(0);

    private int id;

    private UploadFileEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    /**
     * 状态转换 toName
     *
     * @param value
     * @return
     */
    public static String convertToName(int value) {
        for (UploadFileEnum e : UploadFileEnum.values()) {
            if (value == e.getId()) {
                return e.name();
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
    public static Integer convertToId(String value) {
        for (UploadFileEnum e : UploadFileEnum.values()) {
            if (Objects.equals(value,e.name())) {
                return e.getId();
            }
        }
        return null;
    }
}
