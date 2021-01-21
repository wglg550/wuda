package com.qmth.wuda.teaching.bean.report;

import com.qmth.wuda.teaching.constant.SystemConstant;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * @Description: 维度详情bean
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/12/7
 */
public class DimensionDetailBean implements Serializable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "维度编码")
    private String code;

    @ApiModelProperty(value = "维度名称")
    private String name;

    @ApiModelProperty(value = "熟练度")
    private String proficiency;

    @ApiModelProperty(value = "分数比例")
    private BigDecimal scoreRate;

    @ApiModelProperty(value = "学院平均分")
    private BigDecimal collegeAvgScore;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProficiency() {
        return proficiency;
    }

    public void setProficiency(String proficiency) {
        this.proficiency = proficiency;
    }

    public BigDecimal getScoreRate() {
        if (Objects.nonNull(scoreRate)) {
            return scoreRate.setScale(SystemConstant.FINAL_SCALE, BigDecimal.ROUND_HALF_UP);
        } else {
            return new BigDecimal(0);
        }
    }

    public void setScoreRate(BigDecimal scoreRate) {
        this.scoreRate = scoreRate;
    }

    public BigDecimal getCollegeAvgScore() {
        if (Objects.nonNull(collegeAvgScore)) {
            return collegeAvgScore.setScale(SystemConstant.FINAL_SCALE, BigDecimal.ROUND_HALF_UP);
        } else {
            return new BigDecimal(0);
        }
    }

    public void setCollegeAvgScore(BigDecimal collegeAvgScore) {
        this.collegeAvgScore = collegeAvgScore;
    }
}
