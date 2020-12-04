package com.qmth.wuda.teaching.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.qmth.wuda.teaching.base.BaseEntity;
import com.qmth.wuda.teaching.util.UidUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description: 附件表
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020-04-17
 */
@ApiModel(value = "t_b_attachment", description = "附件表")
public class TBAttachment extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("name")
    @ApiModelProperty(value = "附件名称")
    private String name; //附件名称

    @TableField("path")
    @ApiModelProperty(value = "路径")
    private String path; //路径

    @TableField("type")
    @ApiModelProperty(value = "类型")
    private String type; //类型

    @TableField("size")
    @ApiModelProperty(value = "大小", example = "0")
    private BigDecimal size; //大小

    @TableField("md5")
    @ApiModelProperty(value = "MD5")
    private String md5; //MD5

    @TableField("remark")
    @ApiModelProperty(value = "备注")
    private String remark; //备注

    public TBAttachment() {

    }

    public TBAttachment(String path, String name, String type, BigDecimal size, String md5) {
        setId(UidUtil.nextId());
        this.path = path;
        this.name = name;
        this.type = type;
        this.size = size;
        this.md5 = md5;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getSize() {
        return size;
    }

    public void setSize(BigDecimal size) {
        this.size = size;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}