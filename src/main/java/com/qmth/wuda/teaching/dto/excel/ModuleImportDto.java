package com.qmth.wuda.teaching.dto.excel;

import com.qmth.wuda.teaching.annotation.ExcelNotNull;
import com.qmth.wuda.teaching.annotation.ExcelNote;

import java.io.Serializable;

/**
 * @Description: 模块导入dto
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/12/10
 */
public class ModuleImportDto implements Serializable {

    @ExcelNote(value = "名称")
    @ExcelNotNull
    private String name;

    @ExcelNote(value = "描述")
    private String description;

    @ExcelNote(value = "备注")
    private String remark;

    @ExcelNote(value = "熟练度定义")
    private String proficiency;

    @ExcelNote(value = "熟练度范围")
    private String proficiencyDegree;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
