package com.qmth.wuda.teaching.bean.report;

import com.qmth.wuda.teaching.constant.SystemConstant;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * @Description: 综合bean
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/12/7
 */
public class SynthesisBean implements Serializable {

    @ApiModelProperty(value = "我的分数")
    private BigDecimal myScore;

    @ApiModelProperty(value = "实考人数")
    private Integer actualCount;

    @ApiModelProperty(value = "院系对比数值")
    private BigDecimal overRate;

    @ApiModelProperty(value = "学院最低分")
    private BigDecimal collegeMinScore;

    @ApiModelProperty(value = "学院平均分")
    private BigDecimal collegeAvgScore;

    @ApiModelProperty(value = "学院最高分")
    private BigDecimal collegeMaxScore;

    @ApiModelProperty(value = "班级最低分")
    private BigDecimal clazzMinScore;

    @ApiModelProperty(value = "班级平均分")
    private BigDecimal clazzAvgScore;

    @ApiModelProperty(value = "班级最高分")
    private BigDecimal clazzMaxScore;

    @ApiModelProperty(value = "试卷满分")
    private BigDecimal fullScore;

    @ApiModelProperty(value = "难度系数")
    private BigDecimal difficult;

    @ApiModelProperty(value = "难度系数说明")
    private String difficultInfo;

    public SynthesisBean() {

    }

    public SynthesisBean(BigDecimal myScore, Integer actualCount, BigDecimal fullScore) {
        this.myScore = myScore;
        this.actualCount = actualCount;
        this.fullScore = fullScore;
    }

    public void setCollegeScore(SynthesisBean synthesisBean) {
        this.collegeMinScore = synthesisBean.getCollegeMinScore();
        this.collegeAvgScore = synthesisBean.getCollegeAvgScore();
        this.collegeMaxScore = synthesisBean.getCollegeMaxScore();
    }

    public void setClassScore(SynthesisBean synthesisBean) {
        this.clazzMinScore = synthesisBean.getClazzMinScore();
        this.clazzAvgScore = synthesisBean.getClazzAvgScore();
        this.clazzMaxScore = synthesisBean.getClazzMaxScore();
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

    public BigDecimal getOverRate() {
        if (Objects.nonNull(overRate)) {
            return overRate.setScale(SystemConstant.FINAL_SCALE, BigDecimal.ROUND_HALF_UP);
        } else {
            return new BigDecimal(0);
        }
    }

    public void setOverRate(BigDecimal overRate) {
        this.overRate = overRate;
    }

    public BigDecimal getCollegeMinScore() {
        if (Objects.nonNull(collegeMinScore)) {
            return collegeMinScore.setScale(SystemConstant.FINAL_SCALE, BigDecimal.ROUND_HALF_UP);
        } else {
            return new BigDecimal(0);
        }
    }

    public void setCollegeMinScore(BigDecimal collegeMinScore) {
        this.collegeMinScore = collegeMinScore;
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

    public BigDecimal getCollegeMaxScore() {
        if (Objects.nonNull(collegeMaxScore)) {
            return collegeMaxScore.setScale(SystemConstant.FINAL_SCALE, BigDecimal.ROUND_HALF_UP);
        } else {
            return new BigDecimal(0);
        }
    }

    public void setCollegeMaxScore(BigDecimal collegeMaxScore) {
        this.collegeMaxScore = collegeMaxScore;
    }

    public BigDecimal getClazzMinScore() {
        if (Objects.nonNull(clazzMinScore)) {
            return clazzMinScore.setScale(SystemConstant.FINAL_SCALE, BigDecimal.ROUND_HALF_UP);
        } else {
            return new BigDecimal(0);
        }
    }

    public void setClazzMinScore(BigDecimal clazzMinScore) {
        this.clazzMinScore = clazzMinScore;
    }

    public BigDecimal getClazzAvgScore() {
        if (Objects.nonNull(clazzAvgScore)) {
            return clazzAvgScore.setScale(SystemConstant.FINAL_SCALE, BigDecimal.ROUND_HALF_UP);
        } else {
            return new BigDecimal(0);
        }
    }

    public void setClazzAvgScore(BigDecimal clazzAvgScore) {
        this.clazzAvgScore = clazzAvgScore;
    }

    public BigDecimal getClazzMaxScore() {
        if (Objects.nonNull(clazzMaxScore)) {
            return clazzMaxScore.setScale(SystemConstant.FINAL_SCALE, BigDecimal.ROUND_HALF_UP);
        } else {
            return new BigDecimal(0);
        }
    }

    public void setClazzMaxScore(BigDecimal clazzMaxScore) {
        this.clazzMaxScore = clazzMaxScore;
    }

    public BigDecimal getFullScore() {
        if (Objects.nonNull(fullScore)) {
            return fullScore.setScale(SystemConstant.FINAL_SCALE, BigDecimal.ROUND_HALF_UP);
        } else {
            return new BigDecimal(0);
        }
    }

    public void setFullScore(BigDecimal fullScore) {
        this.fullScore = fullScore;
    }

    public BigDecimal getDifficult() {
        if (Objects.nonNull(difficult)) {
            return difficult.setScale(SystemConstant.FINAL_SCALE, BigDecimal.ROUND_HALF_UP);
        } else {
            return new BigDecimal(0);
        }
    }

    public void setDifficult(BigDecimal difficult) {
        this.difficult = difficult;
    }

    public Integer getActualCount() {
        return actualCount;
    }

    public void setActualCount(Integer actualCount) {
        this.actualCount = actualCount;
    }

    public String getDifficultInfo() {
        return difficultInfo;
    }

    public void setDifficultInfo(String difficultInfo) {
        this.difficultInfo = difficultInfo;
    }
}
