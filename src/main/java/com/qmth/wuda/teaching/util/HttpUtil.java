package com.qmth.wuda.teaching.util;

import com.qmth.wuda.teaching.constant.SystemConstant;
import com.qmth.wuda.teaching.signature.Constants;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: http util
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/12/11
 */
public class HttpUtil {

    /**
     * post 请求
     *
     * @param url
     * @param params
     * @param secret
     * @param timestamp
     * @return
     */
    public static String post(String url, Map<String, String> params, String secret, Long timestamp) {
        // 构建post请求
        HttpPost post = new HttpPost(url);
        post.setHeader(Constants.HEADER_AUTHORIZATION, secret);
        post.setHeader(Constants.HEADER_TIME, String.valueOf(timestamp));
        // 构建请求参数
        List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
        if (params != null) {
            for (String key : params.keySet()) {
                pairs.add(new BasicNameValuePair(key, params.get(key)));
            }
        }
        HttpEntity entity = null;
        try {
            entity = new UrlEncodedFormEntity(pairs, Constants.CHARSET_NAME);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        post.setEntity(entity);
        // 执行请求，获取响应
        return getRespString(post);
    }

    /**
     * 获取响应信息
     *
     * @param request
     * @return
     */
    public static String getRespString(HttpUriRequest request) {
        // 获取响应流
        InputStream in = getRespInputStream(request);
        // 流转字符串
        StringBuffer sb = new StringBuffer();
        byte[] b = new byte[SystemConstant.BYTE_LEN];
        int len = 0;
        try {
            while ((len = in.read(b)) != -1) {
                sb.append(new String(b, 0, len, Constants.CHARSET_NAME));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 获取输入流
     *
     * @param request
     * @return
     */
    public static InputStream getRespInputStream(HttpUriRequest request) {
        // 获取响应对象
        HttpResponse response = null;
        try {
            response = HttpClients.createDefault().execute(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (response == null) {
            return null;
        }
        // 获取Entity对象
        HttpEntity entity = response.getEntity();
        // 获取响应信息流
        InputStream in = null;
        if (entity != null) {
            try {
                in = entity.getContent();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return in;
    }
}
