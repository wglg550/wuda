package com.qmth.wuda.teaching.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.qmth.wuda.teaching.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 * 等级信息表
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@ApiModel(value = "t_b_level", description = "等级信息表")
public class TBLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "主键")
    @TableId(value = "id")
    private Long id;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "模块id")
    @TableId(value = "module_id")
    private Long moduleId;

    @ApiModelProperty(value = "等级")
    @TableField(value = "code")
    private String code;

    @ApiModelProperty(value = "层次")
    @TableField(value = "level")
    private String level;

    @ApiModelProperty(value = "说明")
    @TableField(value = "description")
    private String description;

    @ApiModelProperty(value = "规则")
    @TableField(value = "rule")
    private String rule;

    @ApiModelProperty(value = "分数范围")
    @TableField(value = "degree")
    private String degree;

    @ApiModelProperty(value = "诊断结果")
    @TableField(value = "diagnose_result")
    private String diagnoseResult;

    @ApiModelProperty(value = "学习建议")
    @TableField(value = "learn_advice")
    private String learnAdvice;

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public String getDiagnoseResult() {
        return diagnoseResult;
    }

    public void setDiagnoseResult(String diagnoseResult) {
        this.diagnoseResult = diagnoseResult;
    }

    public String getLearnAdvice() {
        return learnAdvice;
    }

    public void setLearnAdvice(String learnAdvice) {
        this.learnAdvice = learnAdvice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }
}
