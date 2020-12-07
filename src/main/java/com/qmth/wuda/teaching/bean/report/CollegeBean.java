package com.qmth.wuda.teaching.bean.report;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Description: 学院bean
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/12/7
 */
public class CollegeBean implements Serializable {

    @ApiModelProperty(value = "综合信息")
    private SynthesisBean synthesis;

    public CollegeBean() {

    }

    public CollegeBean(SynthesisBean synthesis) {
        this.synthesis = synthesis;
    }

    public SynthesisBean getSynthesis() {
        return synthesis;
    }

    public void setSynthesis(SynthesisBean synthesis) {
        this.synthesis = synthesis;
    }
}
