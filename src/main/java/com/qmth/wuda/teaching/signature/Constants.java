package com.qmth.wuda.teaching.signature;

import java.nio.charset.Charset;

/** 
* @Description: 鉴权constants 
* @Param:  
* @return:  
* @Author: wangliang
* @Date: 2020/12/11 
*/ 
public interface Constants {

    public static final int SIGNATURE_EXPIRE_SECONDS = 15;

    public static final int SIGNATURE_AHEAD_SECONDS = 5;

    public static final int VERIFY_TOKEN_EXPIRE_SECONDS = 30;

    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String CHARSET_NAME = "UTF-8";

    public static final Charset CHARSET = Charset.forName(CHARSET_NAME);

    public static final String HEADER_AUTHORIZATION = "Authorization";

    public static final String HEADER_TIME = "time";

    public static final String HEADER_PLATFORM = "platform";

    public static final String HEADER_DEVICE_ID = "deviceId";

    public static final String HEADER_RECORD_ID = "recordId";

    public static final String HEADER_SOURCE = "source";

    public static final String HEADER_USER_ID = "userId";

    /**
     * aes相关
     */
    public static final String AES = "AES";
    public static final String AES_MODE_PKCS5 = "AES/CBC/PKCS5Padding";//用这个模式，规则必须为16位
    public static final String AES_MODE_PKCS7 = "AES/CBC/PKCS7Padding";//用这个模式，规则必须为16位
    public static final String AES_RULE = "1234567890123456";//aes密钥

    public static final String MD5 = "MD5";

}
