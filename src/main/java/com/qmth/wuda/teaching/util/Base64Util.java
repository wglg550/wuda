package com.qmth.wuda.teaching.util;

import com.qmth.wuda.teaching.signature.Constants;

import java.util.Base64;

public class Base64Util implements Constants {

    public static String encode(byte[] input) {
        return new String(Base64.getEncoder().encode(input), CHARSET);
    }

    public static byte[] decode(String input) {
        return Base64.getDecoder().decode(input.getBytes(CHARSET));
    }
}
