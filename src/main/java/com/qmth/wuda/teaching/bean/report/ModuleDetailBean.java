package com.qmth.wuda.teaching.bean.report;

import com.qmth.wuda.teaching.constant.SystemConstant;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * @Description: 模块详情bean
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/12/7
 */
public class ModuleDetailBean implements Serializable {

    @ApiModelProperty(value = "模块名称")
    private String name;

    @ApiModelProperty(value = "个人比例")
    private BigDecimal rate;

    @ApiModelProperty(value = "学院比例")
    private BigDecimal collegeRate;

    @ApiModelProperty(value = "理解说明")
    private String interpretation;

    public String getInterpretation() {
        return interpretation;
    }

    public void setInterpretation(String interpretation) {
        this.interpretation = interpretation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getRate() {
        if (Objects.nonNull(rate)) {
            return rate.setScale(SystemConstant.FINAL_SCALE, BigDecimal.ROUND_HALF_UP);
        } else {
            return new BigDecimal(0);
        }
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getCollegeRate() {
        if (Objects.nonNull(collegeRate)) {
            return collegeRate.setScale(SystemConstant.FINAL_SCALE, BigDecimal.ROUND_HALF_UP);
        } else {
            return new BigDecimal(0);
        }
    }

    public void setCollegeRate(BigDecimal collegeRate) {
        this.collegeRate = collegeRate;
    }
}
