package com.qmth.wuda.teaching.dto.excel;

import com.qmth.wuda.teaching.annotation.ExcelNotNull;
import com.qmth.wuda.teaching.annotation.ExcelNote;

import java.io.Serializable;

/** 
* @Description: 试卷和题型导入 dto
* @Param:  
* @return:  
* @Author: wangliang
* @Date: 2020/12/2 
*/ 
public class PaperAndQuestionImportDto implements Serializable {

    @ExcelNote(value = "试卷编号")
    @ExcelNotNull
    private String paperCode;

    @ExcelNote(value = "大题号")
    @ExcelNotNull
    private String mainNumber;

    @ExcelNote(value = "小题号")
    @ExcelNotNull
    private String subNumber;

    @ExcelNote(value = "反应类型")
    @ExcelNotNull
    private String type;

    @ExcelNote(value = "题目满分")
    @ExcelNotNull
    private String score;

    @ExcelNote(value = "计分规则")
    @ExcelNotNull
    private String rule;

    @ExcelNote(value = "规则说明")
    private String description;

    @ExcelNote(value = "科目名称")
    @ExcelNotNull
    private String courseName;

    @ExcelNote(value = "科目名称")
    @ExcelNotNull
    private String courseCode;

    @ExcelNote(value = "知识维度")
    private String knowledge;

    @ExcelNote(value = "能力维度")
    private String capability;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCapability() {
        return capability;
    }

    public void setCapability(String capability) {
        this.capability = capability;
    }

    public String getPaperCode() {
        return paperCode;
    }

    public void setPaperCode(String paperCode) {
        this.paperCode = paperCode;
    }

    public String getMainNumber() {
        return mainNumber;
    }

    public void setMainNumber(String mainNumber) {
        this.mainNumber = mainNumber;
    }

    public String getSubNumber() {
        return subNumber;
    }

    public void setSubNumber(String subNumber) {
        this.subNumber = subNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(String knowledge) {
        this.knowledge = knowledge;
    }
}
