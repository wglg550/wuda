package com.qmth.wuda.teaching.bean.report;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Description: 维度bean
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/12/7
 */
public class DimensionBean implements Serializable {

    @ApiModelProperty(value = "我的分数")
    private BigDecimal myScore;

    @ApiModelProperty(value = "熟练度")
    private BigDecimal masteryRate;

    @ApiModelProperty(value = "知识维度总分")
    private BigDecimal dioFullScore;

    @ApiModelProperty(value = "维度详情")
    private List<DimensionDetailBean> subDios;

    @ApiModelProperty(value = "维度熟练度")
    private List<LevelBean> masterys;

    public BigDecimal getMyScore() {
        return myScore;
    }

    public void setMyScore(BigDecimal myScore) {
        this.myScore = myScore;
    }

    public BigDecimal getMasteryRate() {
        return masteryRate;
    }

    public void setMasteryRate(BigDecimal masteryRate) {
        this.masteryRate = masteryRate;
    }

    public BigDecimal getDioFullScore() {
        return dioFullScore;
    }

    public void setDioFullScore(BigDecimal dioFullScore) {
        this.dioFullScore = dioFullScore;
    }

    public List<DimensionDetailBean> getSubDios() {
        return subDios;
    }

    public void setSubDios(List<DimensionDetailBean> subDios) {
        this.subDios = subDios;
    }

    public List<LevelBean> getMasterys() {
        return masterys;
    }

    public void setMasterys(List<LevelBean> masterys) {
        this.masterys = masterys;
    }
}
