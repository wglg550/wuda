package com.qmth.wuda.teaching.bean;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class YyjSourceDataBean implements Serializable {

    @ApiModelProperty(name = "科目代码")
    private String subjectCode;

    @ApiModelProperty(name = "科目名称")
    private String subjectName;

    @ApiModelProperty(name = "准考证号")
    private String examNumber;

    @ApiModelProperty(name = "学号")
    private String studentCode;

    @ApiModelProperty(name = "姓名")
    private String name;

    @ApiModelProperty(name = "试卷类型")
    private String paperType;

    @ApiModelProperty(name = "学院")
    private String college;

    @ApiModelProperty(name = "班级")
    private String className;

    @ApiModelProperty(name = "老师")
    private String teacher;

    @ApiModelProperty(name = "状态")
    private int status;

    @ApiModelProperty(name = "总分")
    private String totalScore;

    @ApiModelProperty(name = "客观分")
    private String objectiveScore;

    @ApiModelProperty(name = "主观分")
    private String subjectiveScore;

    @ApiModelProperty(name = "客观分集合")
    private List<YyjObjectiveScoreBean> objectiveScoreDetail;

    @ApiModelProperty(name = "主观分集合")
    List<YyjSubjectiveScoreBean> subjectiveScoreDetail;

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getExamNumber() {
        return examNumber;
    }

    public void setExamNumber(String examNumber) {
        this.examNumber = examNumber;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPaperType() {
        return paperType;
    }

    public void setPaperType(String paperType) {
        this.paperType = paperType;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public String getObjectiveScore() {
        return objectiveScore;
    }

    public void setObjectiveScore(String objectiveScore) {
        this.objectiveScore = objectiveScore;
    }

    public String getSubjectiveScore() {
        return subjectiveScore;
    }

    public void setSubjectiveScore(String subjectiveScore) {
        this.subjectiveScore = subjectiveScore;
    }

    public List<YyjObjectiveScoreBean> getObjectiveScoreDetail() {
        return objectiveScoreDetail;
    }

    public void setObjectiveScoreDetail(List<YyjObjectiveScoreBean> objectiveScoreDetail) {
        this.objectiveScoreDetail = objectiveScoreDetail;
    }

    public List<YyjSubjectiveScoreBean> getSubjectiveScoreDetail() {
        return subjectiveScoreDetail;
    }

    public void setSubjectiveScoreDetail(List<YyjSubjectiveScoreBean> subjectiveScoreDetail) {
        this.subjectiveScoreDetail = subjectiveScoreDetail;
    }

    public class YyjObjectiveScoreBean implements Serializable {

        @ApiModelProperty(name = "分数")
        private BigDecimal score;

        @ApiModelProperty(name = "答案")
        private String answer;

        @ApiModelProperty(name = "大题号")
        private Integer mainNumber;

        @ApiModelProperty(name = "小题号")
        private Integer subNumber;

        public BigDecimal getScore() {
            return score;
        }

        public void setScore(BigDecimal score) {
            this.score = score;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public Integer getMainNumber() {
            return mainNumber;
        }

        public void setMainNumber(Integer mainNumber) {
            this.mainNumber = mainNumber;
        }

        public Integer getSubNumber() {
            return subNumber;
        }

        public void setSubNumber(Integer subNumber) {
            this.subNumber = subNumber;
        }
    }

    public class YyjSubjectiveScoreBean implements Serializable {

        @ApiModelProperty(name = "分数")
        private BigDecimal score;

        @ApiModelProperty(name = "大题号")
        private Integer mainNumber;

        @ApiModelProperty(name = "小题号")
        private Integer subNumber;

        public BigDecimal getScore() {
            return score;
        }

        public void setScore(BigDecimal score) {
            this.score = score;
        }

        public Integer getMainNumber() {
            return mainNumber;
        }

        public void setMainNumber(Integer mainNumber) {
            this.mainNumber = mainNumber;
        }

        public Integer getSubNumber() {
            return subNumber;
        }

        public void setSubNumber(Integer subNumber) {
            this.subNumber = subNumber;
        }
    }
}
