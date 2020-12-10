package com.qmth.wuda.teaching.dto.excel;

import com.qmth.wuda.teaching.annotation.ExcelNotNull;
import com.qmth.wuda.teaching.annotation.ExcelNote;

import java.io.Serializable;

/**
 * @Description: 维度导入 dto
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/12/1
 */
public class DimensionImportDto implements Serializable {

    @ExcelNote(value = "科目代码")
    @ExcelNotNull
    private String courseCode;

    @ExcelNote(value = "科目名称")
    @ExcelNotNull
    private String courseName;

    @ExcelNote(value = "一级知识维度")
    @ExcelNotNull
    private String knowledgeFirst;

    @ExcelNote(value = "一级维度编号")
    @ExcelNotNull
    private String identifierFirst;

    @ExcelNote(value = "二级知识维度")
    private String knowledgeSecond;

    @ExcelNote(value = "二级维度编号")
    private String identifierSecond;

    @ExcelNote(value = "一级维度术语解释")
    private String description;

    @ExcelNotNull
    @ExcelNote(value = "模块")
    private String moduleName;

    public String getKnowledgeSecond() {
        return knowledgeSecond;
    }

    public void setKnowledgeSecond(String knowledgeSecond) {
        this.knowledgeSecond = knowledgeSecond;
    }

    public String getIdentifierSecond() {
        return identifierSecond;
    }

    public void setIdentifierSecond(String identifierSecond) {
        this.identifierSecond = identifierSecond;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
