package com.qmth.wuda.teaching.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description: 学生维度dto
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/12/25
 */
public class StudentDimensionDto implements Serializable {

    @ApiModelProperty(value = "维度")
    private String dimension;

    @ApiModelProperty(value = "维度分数")
    private BigDecimal dimensionScore;

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public BigDecimal getDimensionScore() {
        return dimensionScore;
    }

    public void setDimensionScore(BigDecimal dimensionScore) {
        this.dimensionScore = dimensionScore;
    }
}
