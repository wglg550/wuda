package com.qmth.wuda.teaching.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 系统配置
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/4/10
 */
public class SysDomain implements Serializable {

    private static final long serialVersionUID = 7510626406622200443L;

    private boolean oss;

    private List<String> attachmentType;

    private String serverUpload;

    private String fileHost;

    private String serverHost;

    public String getFileHost() {
        return fileHost;
    }

    public void setFileHost(String fileHost) {
        this.fileHost = fileHost;
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public boolean isOss() {
        return oss;
    }

    public void setOss(boolean oss) {
        this.oss = oss;
    }

    public List<String> getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(List<String> attachmentType) {
        this.attachmentType = attachmentType;
    }

    public String getServerUpload() {
        return serverUpload;
    }

    public void setServerUpload(String serverUpload) {
        this.serverUpload = serverUpload;
    }
}
