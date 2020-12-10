package com.qmth.wuda.teaching.dto.excel;

import com.qmth.wuda.teaching.annotation.ExcelNotNull;
import com.qmth.wuda.teaching.annotation.ExcelNote;

import java.io.Serializable;

/**
* @Description: 模块和等级导入dto
* @Param:
* @return:
* @Author: wangliang
* @Date: 2020/12/10
*/
public class ModuleAndLevelImportDto implements Serializable {

    @ExcelNote(value = "等级")
    @ExcelNotNull
    private String code;

    @ExcelNote(value = "划分规则")
    @ExcelNotNull
    private String rule;

    @ExcelNote(value = "分数范围")
    @ExcelNotNull
    private String levelDegree;

    @ExcelNote(value = "水平层次")
    @ExcelNotNull
    private String level;

    @ExcelNote(value = "诊断结果")
    private String diagnoseResult;

    @ExcelNote(value = "学习建议")
    private String learnAdvice;

    @ExcelNotNull
    @ExcelNote(value = "模块")
    private String moduleName;

    @ExcelNote(value = "描述")
    private String description;

    @ExcelNote(value = "备注")
    private String remark;

    @ExcelNote(value = "熟练度定义")
    private String proficiency;

    @ExcelNote(value = "熟练度范围")
    private String proficiencyDegree;

    public String getLevelDegree() {
        return levelDegree;
    }

    public void setLevelDegree(String levelDegree) {
        this.levelDegree = levelDegree;
    }

    public String getProficiencyDegree() {
        return proficiencyDegree;
    }

    public void setProficiencyDegree(String proficiencyDegree) {
        this.proficiencyDegree = proficiencyDegree;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProficiency() {
        return proficiency;
    }

    public void setProficiency(String proficiency) {
        this.proficiency = proficiency;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
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
}
