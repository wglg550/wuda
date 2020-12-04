package com.qmth.wuda.teaching.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Objects;

/**
 * @Description: ehcache util
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/6/23
 */
public class EhcacheUtil {
    private final static Logger log = LoggerFactory.getLogger(EhcacheUtil.class);

    private static final String path = "/ehcache.xml";

    private URL url;
    private static CacheManager manager;
    private volatile static EhcacheUtil ehCache;

    private EhcacheUtil(String path) {
        url = getClass().getResource(path);
        manager = CacheManager.create(url);
    }

    public static EhcacheUtil getInstance() {
        if (Objects.isNull(ehCache)) {
            synchronized (EhcacheUtil.class) {
                if (Objects.isNull(ehCache)) {
                    ehCache = new EhcacheUtil(path);
                }
            }
        }
        return ehCache;
    }

    static {
        getInstance();
    }

    public static void put(String cacheName, Object key, Object value) {
        Cache cache = manager.getCache(cacheName);
        Element element = new Element(key, value);
        cache.put(element);
    }

    public static Object get(String cacheName, Object key) {
        Cache cache = manager.getCache(cacheName);
        Element element = cache.get(key);
        if (Objects.nonNull(element)) {
            log.info("Element:{}", JacksonUtil.parseJson(element));
        }
        return element == null ? null : element.getObjectValue();
    }

    public static Cache get(String cacheName) {
        return manager.getCache(cacheName);
    }

    public static void remove(String cacheName, Object key) {
        Cache cache = manager.getCache(cacheName);
        cache.remove(key);
    }
}