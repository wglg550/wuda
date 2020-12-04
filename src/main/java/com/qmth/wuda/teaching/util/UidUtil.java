package com.qmth.wuda.teaching.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UidUtil {

    private static final Logger log = LoggerFactory.getLogger(UidUtil.class);

    private UidGenerator uid;

    public long getId() {
        return uid.next();
    }
}
