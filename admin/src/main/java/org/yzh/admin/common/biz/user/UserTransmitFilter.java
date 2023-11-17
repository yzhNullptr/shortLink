package org.yzh.admin.common.biz.user;


import com.alibaba.fastjson2.JSON;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.yzh.admin.common.constant.RedisCacheConstant;
import org.yzh.admin.common.constant.UserConstant;

import java.io.IOException;

/**
 * 用户信息传输过滤器
 */
@RequiredArgsConstructor
public class UserTransmitFilter implements Filter {

    private final StringRedisTemplate stringRedisTemplate;
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
//        String userId = httpServletRequest.getHeader(UserConstant.USER_ID_KEY);
//        if (StringUtils.hasText(userId)) {
            String userName = httpServletRequest.getHeader(UserConstant.USER_NAME_KEY);
//            String realName = httpServletRequest.getHeader(UserConstant.REAL_NAME_KEY);
//            if (StringUtils.hasText(userName)) {
//                userName = URLDecoder.decode(userName, UTF_8);
//            }
//            if (StringUtils.hasText(realName)) {
//                realName = URLDecoder.decode(realName, UTF_8);
//            }
            String token = httpServletRequest.getHeader(UserConstant.USER_TOKEN_KEY);
            String key= RedisCacheConstant.USER_LOGIN_KEY+userName;
            Object userInfoJsonStr = stringRedisTemplate.opsForHash().get(key, token);
            if (userInfoJsonStr!=null){
                UserInfoDTO userInfoDTO = JSON.parseObject(userInfoJsonStr.toString(), UserInfoDTO.class);
                UserContext.setUser(userInfoDTO);
            }
//        }
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            UserContext.removeUser();
        }
    }
}
