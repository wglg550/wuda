package com.qmth.wuda.teaching.signature;

public enum SignatureType {

    SECRET("Secret"), TOKEN("Token");

    private String name;

    private SignatureType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
