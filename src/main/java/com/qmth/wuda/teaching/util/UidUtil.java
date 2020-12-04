package com.qmth.wuda.teaching.util;

import com.qmth.wuda.teaching.constant.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class UidUtil {
    private static final Logger log = LoggerFactory.getLogger(UidUtil.class);

    private UidGenerator uid;

    @PostConstruct
    public void init() {
        uid = new UidGenerator(0L, 0L);
    }

    public long getId() {
        return uid.next();
    }

    public static long nextId() {
        return SpringContextHolder.getBean(UidUtil.class).getId();
    }
}
