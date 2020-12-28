package com.qmth.wuda.teaching.bean.report.course;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Description: 科目信息
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/12/28
 */
public class CourseInfoBean implements Serializable {

    @ApiModelProperty(value = "考试代码")
    private String examCode;

    @ApiModelProperty(value = "是否缺考")
    private Boolean isAbsent;

    @ApiModelProperty(value = "科目代码")
    private String paperCode;

    @ApiModelProperty(value = "科目名称")
    private String paperName;

    @ApiModelProperty(value = "状态")
    private String status;

    public CourseInfoBean() {

    }

    public CourseInfoBean(String examCode, Boolean isAbsent, String paperCode, String paperName, String status) {
        this.examCode = examCode;
        this.isAbsent = isAbsent;
        this.paperCode = paperCode;
        this.paperName = paperName;
        this.status = status;
    }

    public String getExamCode() {
        return examCode;
    }

    public void setExamCode(String examCode) {
        this.examCode = examCode;
    }

    public Boolean getAbsent() {
        return isAbsent;
    }

    public void setAbsent(Boolean absent) {
        isAbsent = absent;
    }

    public String getPaperCode() {
        return paperCode;
    }

    public void setPaperCode(String paperCode) {
        this.paperCode = paperCode;
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
