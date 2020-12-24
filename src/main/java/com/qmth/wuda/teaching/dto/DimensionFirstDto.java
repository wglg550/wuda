package com.qmth.wuda.teaching.dto;

import com.qmth.wuda.teaching.constant.SystemConstant;
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
public class DimensionFirstDto implements Serializable {

    @ApiModelProperty(value = "模块id")
    private Long moduleId;

    @ApiModelProperty(value = "模块编码")
    private String moduleCode;

    @ApiModelProperty(value = "科目编码")
    private String courseCode;

    @ApiModelProperty(value = "知识点一级")
    private String knowledgeFirst;

    @ApiModelProperty(value = "编号一级")
    private String identifierFirst;

    @ApiModelProperty(value = "知识点一级分数")
    private BigDecimal sumScore;

    @ApiModelProperty(value = "个人知识点一级分数")
    private BigDecimal myScore;

    @ApiModelProperty(value = "标题")
    private String info;

    @ApiModelProperty(value = "备注")
    private String remark;

    public DimensionFirstDto() {

    }

    public DimensionFirstDto(Long moduleId, String moduleCode, String courseCode, String knowledgeFirst, String identifierFirst, String info, String remark) {
        this.moduleId = moduleId;
        this.moduleCode = moduleCode;
        this.courseCode = courseCode;
        this.knowledgeFirst = knowledgeFirst;
        this.identifierFirst = identifierFirst;
        this.info = info;
        this.remark = remark;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getKnowledgeFirst() {
        return knowledgeFirst;
    }

    public void setKnowledgeFirst(String knowledgeFirst) {
        this.knowledgeFirst = knowledgeFirst;
    }

    public String getIdentifierFirst() {
        return identifierFirst;
    }

    public void setIdentifierFirst(String identifierFirst) {
        this.identifierFirst = identifierFirst;
    }

    public BigDecimal getSumScore() {
        if (Objects.nonNull(sumScore)) {
            return sumScore.setScale(SystemConstant.SCALE, BigDecimal.ROUND_HALF_UP);
        } else {
            return new BigDecimal(0);
        }
    }

    public void setSumScore(BigDecimal sumScore) {
        this.sumScore = sumScore;
    }

    public BigDecimal getMyScore() {
        if (Objects.nonNull(myScore)) {
            return myScore.setScale(SystemConstant.SCALE, BigDecimal.ROUND_HALF_UP);
        } else {
            return new BigDecimal(0);
        }
    }

    public void setMyScore(BigDecimal myScore) {
        this.myScore = myScore;
    }
}
