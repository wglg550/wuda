package com.qmth.wuda.teaching.constant;

import java.io.File;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 系统constant
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/12/11
 */
public class SystemConstant {

    public static final String CHARSET_NAME = "UTF-8";
    public static final String HEADER_AUTHORIZATION = "authorization";
    public static final String TYPE = "type";
    public static final String LOCAL = "local";
    public static final String OSS = "oss";
    public static final String PATH = "path";
    public static final String UPLOAD_TYPE = "uploadType";
    public static final String USER_DIR = "user.dir";
    public static final String MD5 = "md5";
    public static final String SUCCESS = "success";
    public static final String EXTEND_COLUMN = "extendColumn";
    public static final String MAP = "Map";
    public static final int MAX_IMPORT_SIZE = 500;
    public static final int BYTE_LEN = 102400; // 100KB

    public static String TEMP_FILES_DIR;

    /**
     * ehcache配置
     */
    public static final String roleCache = "role_cache";

    /**
     * 初始化附件文件路径
     */
    public static void initTempFiles() {
        StringJoiner localPath = new StringJoiner("").add(System.getProperty(SystemConstant.USER_DIR));
        String mkdir = localPath.toString().substring(0, localPath.toString().lastIndexOf(File.separator));
        File tempdir = new File(mkdir + File.separator + "wuda-files-temp");
        if (!tempdir.exists()) {
            tempdir.mkdirs();
        }
        TEMP_FILES_DIR = tempdir.getPath();
    }

    /**
     * 过滤题号
     *
     * @param value
     * @return
     */
    public static String filterQuestion(String value) {
        String reg = "[^(0-9)-]";
        Pattern pat = Pattern.compile(reg);
        Matcher mat = pat.matcher(value);
        return mat.replaceAll("");
    }
}
