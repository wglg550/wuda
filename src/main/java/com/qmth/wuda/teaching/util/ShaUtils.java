package com.qmth.wuda.teaching.util;


import com.qmth.wuda.teaching.signature.Constants;

import java.security.MessageDigest;

public class ShaUtils implements Constants {

    public static byte[] sha1(String input) {
        try {
            return MessageDigest.getInstance("SHA1").digest(input.getBytes(CHARSET));
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] sha256(String input) {
        try {
            return MessageDigest.getInstance("SHA256").digest(input.getBytes(CHARSET));
        } catch (Exception e) {
            return null;
        }
    }

}
