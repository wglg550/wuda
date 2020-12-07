package com.qmth.wuda.teaching.bean.report;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

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
        return myScore;
    }

    public void setMyScore(BigDecimal myScore) {
        this.myScore = myScore;
    }

    public BigDecimal getOverRate() {
        return overRate;
    }

    public void setOverRate(BigDecimal overRate) {
        this.overRate = overRate;
    }

    public BigDecimal getCollegeMinScore() {
        return collegeMinScore;
    }

    public void setCollegeMinScore(BigDecimal collegeMinScore) {
        this.collegeMinScore = collegeMinScore;
    }

    public BigDecimal getCollegeAvgScore() {
        return collegeAvgScore;
    }

    public void setCollegeAvgScore(BigDecimal collegeAvgScore) {
        this.collegeAvgScore = collegeAvgScore;
    }

    public BigDecimal getCollegeMaxScore() {
        return collegeMaxScore;
    }

    public void setCollegeMaxScore(BigDecimal collegeMaxScore) {
        this.collegeMaxScore = collegeMaxScore;
    }

    public BigDecimal getClazzMinScore() {
        return clazzMinScore;
    }

    public void setClazzMinScore(BigDecimal clazzMinScore) {
        this.clazzMinScore = clazzMinScore;
    }

    public BigDecimal getClazzAvgScore() {
        return clazzAvgScore;
    }

    public void setClazzAvgScore(BigDecimal clazzAvgScore) {
        this.clazzAvgScore = clazzAvgScore;
    }

    public BigDecimal getClazzMaxScore() {
        return clazzMaxScore;
    }

    public void setClazzMaxScore(BigDecimal clazzMaxScore) {
        this.clazzMaxScore = clazzMaxScore;
    }

    public BigDecimal getFullScore() {
        return fullScore;
    }

    public void setFullScore(BigDecimal fullScore) {
        this.fullScore = fullScore;
    }

    public BigDecimal getDifficult() {
        return difficult;
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
