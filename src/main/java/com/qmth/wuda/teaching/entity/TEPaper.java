package com.qmth.wuda.teaching.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 * 试卷信息表
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@ApiModel(value = "t_e_paper", description = "试卷信息表")
public class TEPaper implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "考试批次id")
    @TableField(value = "exam_id")
    private Long examId;

    @ApiModelProperty(value = "科目名称")
    @TableField(value = "course_name")
    private String courseName;

    @ApiModelProperty(value = "科目编码")
    @TableField(value = "course_code")
    private String courseCode;

    @ApiModelProperty(value = "名称")
    @TableField(value = "name")
    private String name;

    @ApiModelProperty(value = "编码")
    @TableField(value = "code")
    private String code;

    @ApiModelProperty(value = "试卷总分")
    @TableField(value = "total_score")
    private Double totalScore;

    public TEPaper() {

    }

    public TEPaper(Long examId, String courseName, String courseCode, String name, String code, Double totalScore) {
        this.examId = examId;
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.name = name;
        this.code = code;
        this.totalScore = totalScore;
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

    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }
}
