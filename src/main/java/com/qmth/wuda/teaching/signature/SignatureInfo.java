package com.qmth.wuda.teaching.signature;

import com.qmth.wuda.teaching.util.Base64Util;
import com.qmth.wuda.teaching.util.ShaUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 签名数据对象，包括构造、解析、验证的通用方法
 */
public class SignatureInfo {

    private static final String PATTERN = "{0} {1}{2}{3}";

    private static final String FIELD_JOINER = ":";

    private static final String PARAM_JOINER = "&";

    private static Map<String, SignatureType> typeMap = new HashMap<>();

    static {
        for (SignatureType type : SignatureType.values()) {
            typeMap.put(type.getName(), type);
        }
    }

    private SignatureType type;

    private String method;

    private String uri;

    private long timestamp;

    private String ciphertext;

    private String invoker;

    private String secret;

    private SignatureInfo() {
    }

    public SignatureType getType() {
        return type;
    }

    private void setType(SignatureType type) {
        this.type = type;
    }

    public String getMethod() {
        return method;
    }

    private void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    private void setUri(String uri) {
        this.uri = uri;
    }

    public long getTimestamp() {
        return timestamp;
    }

    private void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    private String getCiphertext() {
        return ciphertext;
    }

    private void setCiphertext(String ciphertext) {
        this.ciphertext = ciphertext;
    }

    public String getInvoker() {
        return invoker;
    }

    private void setInvoker(String invoker) {
        this.invoker = invoker;
    }

    public String getSecret() {
        return secret;
    }

    private void setSecret(String secret) {
        this.secret = secret;
    }

    private static String encrypt(String... values) {
        return Base64Util.encode(ShaUtils.sha1(StringUtils.join(values, PARAM_JOINER)));
    }

    /**
     * 基于解析好的签名对象，使用传入的保密信息进行签名内容验证
     *
     * @param secret
     * @return
     */
    public boolean validate(String secret) {
        if (method != null && uri != null && timestamp >= 0 && secret != null && ciphertext != null) {
            return encrypt(method, uri, String.valueOf(timestamp), secret).equals(ciphertext);
        }
        return false;
    }

//    /**
//     * 基于解析好的签名对象，使用传入的保密信息进行签名内容验证(测试用)
//     *
//     * @param secret
//     * @return
//     */
//    public boolean validate(String secret) {
//        if (secret != null && ciphertext != null) {
//            return encrypt(secret).equals(ciphertext);
//        }
//        return false;
//    }

    /**
     * 根据标准参数构造最终的签名字符串
     *
     * @param type
     * @param method
     * @param uri
     * @param timestamp
     * @param invoker
     * @param secret
     * @return
     */
    public static String build(SignatureType type, String method, String uri, long timestamp, String invoker, String secret) {
        if (type == null || method == null || uri == null || timestamp <= 0 || invoker == null || secret == null) {
            return "";
        }
        return MessageFormat.format(PATTERN, type.getName(), invoker, FIELD_JOINER,
                encrypt(method.toLowerCase(), uri, String.valueOf(timestamp), secret));
    }

    /**
     * 根据标准参数构造最终的签名字符串(测试用)
     *
     * @param type
     * @param invoker
     * @param secret
     * @return
     */
    public static String build(SignatureType type, String invoker, String secret) {
        if (type == null || invoker == null || secret == null) {
            return "";
        }
        return MessageFormat.format(PATTERN, type.getName(), invoker, FIELD_JOINER,
                encrypt(secret));
    }

    /**
     * 根据当前接口的的基本信息、header中的时间戳与签名字符串，尝试解析并构造签名数据对象
     *
     * @param method
     * @param uri
     * @param timestamp
     * @param signature
     * @return
     */
    public static SignatureInfo parse(String method, String uri, long timestamp, String signature) {
        if (method == null || uri == null || timestamp <= 0 || signature == null) {
            return null;
        }
        String[] values = StringUtils.split(signature);
        if (values != null && values.length == 2) {
            SignatureType type = typeMap.get(values[0]);
            if (type != null) {
                String[] array = StringUtils.split(values[1], FIELD_JOINER);
                if (array != null && array.length == 2) {
                    SignatureInfo info = new SignatureInfo();
                    info.setType(type);
                    info.setMethod(method.toLowerCase());
                    info.setUri(uri);
                    info.setTimestamp(timestamp);
                    info.setInvoker(array[0]);
                    info.setCiphertext(array[1]);
                    return info;
                }
            }
        }
        return null;
    }

    /**
     * 根据当前接口的的基本信息、header中的时间戳与签名字符串，尝试解析并构造签名数据对象(测试用)
     *
     * @param signature
     * @return
     */
    public static SignatureInfo parse(String signature) {
        if (signature == null) {
            return null;
        }
        String[] values = StringUtils.split(signature);
        if (values != null && values.length == 2) {
            SignatureType type = typeMap.get(values[0]);
            if (type != null) {
                String[] array = StringUtils.split(values[1], FIELD_JOINER);
                if (array != null && array.length == 2) {
                    SignatureInfo info = new SignatureInfo();
                    info.setType(type);
//                    info.setMethod(method.toLowerCase());
//                    info.setUri(uri);
//                    info.setTimestamp(timestamp);
                    info.setInvoker(array[0]);
                    info.setCiphertext(array[1]);
                    return info;
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
//        SignatureInfo source = new SignatureInfo();
//        source.setType(SignatureType.TOKEN);
////        source.setMethod("POST");
//        source.setMethod("GET");
//        source.setUri("");
//        source.setTimestamp(System.currentTimeMillis());
//        source.setInvoker("3_STUDENT_pc");
//        source.setSecret("R4IYArPwnU9hbeBev6CDDNC0S126sdb3");
//        System.out.println(source.getTimestamp());
//        long start = System.currentTimeMillis();
//        String signature = SignatureInfo
//                .build(source.getType(), source.getMethod(), source.getUri(), source.getTimestamp(), source.getInvoker(),
//                        source.getSecret());
//        System.out.println("signature:" + signature + "\ntime cost=" + (System.currentTimeMillis() - start));
//
//        start = System.currentTimeMillis();
//        SignatureInfo parse = SignatureInfo.parse(source.getMethod(), source.getUri(), source.getTimestamp(), signature);
//        System.out.println(
//                "validate:" + (parse != null && parse.validate(source.getSecret())) + "\ntime cost=" + (System.currentTimeMillis()
//                        - start));
    }

}
