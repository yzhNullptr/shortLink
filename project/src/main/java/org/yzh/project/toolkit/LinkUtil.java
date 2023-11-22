package org.yzh.project.toolkit;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;

import java.util.Date;
import java.util.Optional;

import static org.yzh.project.common.constant.ShortLinkConstant.DEFAULT_CACHE_VALID_TIME;

/**
 * 短链接工具类
 */
public class LinkUtil {
    /**
     * 获取短链接缓存时间
     * @param date 有效期时间
     * @return 有效期时间戳
     */
    public static long getLinkCacheValidDate(Date date){
        return Optional.ofNullable(date)
                .map(each-> DateUtil.between(new Date(),each, DateUnit.MS))
                .orElse(DEFAULT_CACHE_VALID_TIME);
    }
}
