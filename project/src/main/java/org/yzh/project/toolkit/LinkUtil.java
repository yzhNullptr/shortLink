package org.yzh.project.toolkit;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import jakarta.servlet.http.HttpServletRequest;

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

    /**
     * 获取用户真实ip
     * @param request 请求
     * @return 真实ip
     */
    public static String getActualIp(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.contains(",")) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;

    }

    /**
     * 获取用户访问操作系统
     * @param request 请求
     * @return 访问操作系统
     */
    public static String getOS(HttpServletRequest request){
        String userAgent = request.getHeader("User-Agent");

        if (userAgent != null) {
            userAgent = userAgent.toLowerCase();

            if (userAgent.contains("windows")) {
                return "Windows";
            } else if (userAgent.contains("mac")) {
                return "Macintosh";
            } else if (userAgent.contains("linux")) {
                return "Linux";
            } else if (userAgent.contains("android")) {
                return "Android";
            } else if (userAgent.contains("iphone") || userAgent.contains("ipad")) {
                return "iOS";
            }
        }

        return "Unknown";
    }
}
