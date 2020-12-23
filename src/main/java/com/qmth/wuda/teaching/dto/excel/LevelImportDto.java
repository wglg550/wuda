package com.qmth.wuda.teaching.dto.excel;

import com.qmth.wuda.teaching.annotation.ExcelNotNull;
import com.qmth.wuda.teaching.annotation.ExcelNote;

import java.io.Serializable;

/**
 * @Description: 等级导入dto
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/12/10
 */
public class LevelImportDto implements Serializable {

    @ExcelNote(value = "等级")
    @ExcelNotNull
    private String code;

    @ExcelNote(value = "换算公式")
    @ExcelNotNull
    private String formula;

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

    @ExcelNote(value = "科目编码")
    @ExcelNotNull
    private String courseCode;

    @ExcelNote(value = "试卷类型")
    @ExcelNotNull
    private String paperCode;

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getPaperCode() {
        return paperCode;
    }

    public void setPaperCode(String paperCode) {
        this.paperCode = paperCode;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getLevelDegree() {
        return levelDegree;
    }

    public void setLevelDegree(String levelDegree) {
        this.levelDegree = levelDegree;
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
