package com.qmth.wuda.teaching.bean.report;

import com.qmth.wuda.teaching.constant.SystemConstant;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

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

    @ApiModelProperty(value = "熟练度等级")
    private List<DimensionMasterysBean> masterys;

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

    public BigDecimal getMasteryRate() {
        if (Objects.nonNull(masteryRate)) {
            return masteryRate.setScale(SystemConstant.FINAL_SCALE, BigDecimal.ROUND_HALF_UP);
        } else {
            return new BigDecimal(0);
        }
    }

    public void setMasteryRate(BigDecimal masteryRate) {
        this.masteryRate = masteryRate;
    }

    public BigDecimal getDioFullScore() {
        if (Objects.nonNull(dioFullScore)) {
            return dioFullScore.setScale(SystemConstant.FINAL_SCALE, BigDecimal.ROUND_HALF_UP);
        } else {
            return new BigDecimal(0);
        }
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

    public List<DimensionMasterysBean> getMasterys() {
        return masterys;
    }

    public void setMasterys(List<DimensionMasterysBean> masterys) {
        this.masterys = masterys;
    }
}
