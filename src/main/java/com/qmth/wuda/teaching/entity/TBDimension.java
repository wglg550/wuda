package com.qmth.wuda.teaching.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 * 维度信息表
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@ApiModel(value = "t_b_dimension", description = "维度信息表")
public class TBDimension implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "模块id")
    @TableField(value = "module_id")
    private Long moduleId;

    @ApiModelProperty(value = "科目名称")
    @TableField(value = "course_name")
    private String courseName;

    @ApiModelProperty(value = "科目代码")
    @TableField(value = "course_code")
    private String courseCode;

    @ApiModelProperty(value = "知识点一级")
    @TableField(value = "knowledge_first")
    private String knowledgeFirst;

    @ApiModelProperty(value = "编号一级")
    @TableField(value = "identifier_first")
    private String identifierFirst;

    @ApiModelProperty(value = "知识点二级")
    @TableField(value = "knowledge_second")
    private String knowledgeSecond;

    @ApiModelProperty(value = "编号一级")
    @TableField(value = "identifier_second")
    private String identifierSecond;

    @ApiModelProperty(value = "备注")
    @TableField(value = "description")
    private String description;

    public TBDimension() {

    }

    public TBDimension(String courseName, String courseCode, String knowledgeFirst, String identifierFirst, String description) {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.knowledgeFirst = knowledgeFirst;
        this.identifierFirst = identifierFirst;
        this.description = description;
    }

    public TBDimension(String courseName, String courseCode, String knowledgeFirst, String identifierFirst, String knowledgeSecond, String identifierSecond, String description) {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.knowledgeFirst = knowledgeFirst;
        this.identifierFirst = identifierFirst;
        this.knowledgeSecond = knowledgeSecond;
        this.identifierSecond = identifierSecond;
        this.description = description;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
