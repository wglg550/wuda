package com.qmth.wuda.teaching.enums;

/**
 * @Description: 试卷难易度
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/12/8
 */
public enum PaperDifficultEnum {

    HARD("困难", 0D, 0.2D),

    MEDIUM("一般", 0.3D, 0.7D),

    EASY("容易", 0.8D, 1.0D);

    String code;
    Double min, max;

    private PaperDifficultEnum(String code, Double min, Double max) {
        this.code = code;
        this.min = min;
        this.max = max;
    }

    public String getCode() {
        return code;
    }

    public Double getMin() {
        return min;
    }

    public Double getMax() {
        return max;
    }

    /**
     * 状态转换 toName
     *
     * @param value
     * @return
     */
    public static String convertToCode(Double value) {
        for (PaperDifficultEnum e : PaperDifficultEnum.values()) {
            if (value.doubleValue() >= e.getMin() && value.doubleValue() <= e.getMax()) {
                return e.getCode();
            }
        }
        return null;
    }
}
