package com.qmth.wuda.teaching.dto;

import com.qmth.wuda.teaching.constant.SystemConstant;
import com.qmth.wuda.teaching.entity.TBDimension;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * @Description: 一级维度dto
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/12/8
 */
public class DimensionSecondDto extends TBDimension implements Serializable {

    @ApiModelProperty(value = "知识点二级分数")
    private BigDecimal sumScore;

    @ApiModelProperty(value = "个人知识点二级分数")
    private BigDecimal myScore;

    @ApiModelProperty(value = "模块编码")
    private String moduleCode;

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public BigDecimal getSumScore() {
        if (Objects.nonNull(sumScore)) {
            return sumScore.setScale(SystemConstant.FINAL_SCALE, BigDecimal.ROUND_HALF_UP);
        } else {
            return new BigDecimal(0);
        }
    }

    public void setSumScore(BigDecimal sumScore) {
        this.sumScore = sumScore;
    }

    public BigDecimal getMyScore() {
        if (Objects.nonNull(myScore)) {
            return myScore.setScale(SystemConstant.FINAL_SCALE, BigDecimal.ROUND_HALF_UP);
        } else {
            return new BigDecimal(0);
        }
    }

    public void setMyScore(BigDecimal myScore) {
        this.myScore = myScore;
    }
}
