package org.yzh.project.common.constant;

/**
 * Redis Key 常量类
 */
public class RedisKeyConstant {
    /**
     * 短链接跳转 前缀 Key
     */
    public static final String GOTO_SHORT_LINK_KEY="shortLink:goto:{}";
    /**
     * 短链接跳转空值 前缀 Key
     */
    public static final String GOTO_IS_NULL_SHORT_LINK_KEY="shortLink:is_null:goto:{}";
    /**
     * 短链接跳转锁前缀 key
     */
    public static final String LOCK_GOTO_SHORT_LINK_KEY="shortLink:lock:goto:{}";
}
