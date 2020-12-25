package com.qmth.wuda.teaching.dto;

import com.qmth.wuda.teaching.bean.report.DimensionMasterysBean;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 维度熟练度bean
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/12/7
 */
public class DimensionMasterysDto extends DimensionMasterysBean implements Serializable {

    @ApiModelProperty(value = "源分数")
    private String[] sourceGrade;

    public DimensionMasterysDto() {

    }

    public DimensionMasterysDto(String level, List<Integer> grade, String[] sourceGrade) {
        setLevel(level);
        setGrade(grade);
        this.sourceGrade = sourceGrade;
    }

    public String[] getSourceGrade() {
        return sourceGrade;
    }

    public void setSourceGrade(String[] sourceGrade) {
        this.sourceGrade = sourceGrade;
    }
}
