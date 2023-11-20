package org.yzh.admin.common.biz.user;


import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.yzh.admin.common.constant.RedisCacheConstant;
import org.yzh.admin.common.constant.UserConstant;
import org.yzh.admin.common.convention.exception.ClientException;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static org.yzh.admin.common.enums.UserErrorCodeEnum.USER_TOKEN_FAIL;

/**
 * 用户信息传输过滤器
 */
@RequiredArgsConstructor
public class UserTransmitFilter implements Filter {

    private final StringRedisTemplate stringRedisTemplate;
    private static final List<String> IGNORE_URI= Lists.newArrayList(
            "/api/shortLink/admin/v1/user/login",
            "/api/shortLink/admin/v1/user/has-username"
    );
    @Override
    @SneakyThrows
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String requestURI = httpServletRequest.getRequestURI();
        if (!IGNORE_URI.contains(requestURI)){
            String method=httpServletRequest.getMethod();
            if (!(Objects.equals(requestURI,"/api/shortLink/admin/v1/user")&&Objects.equals(method,"POST"))){
                String userName = httpServletRequest.getHeader(UserConstant.USER_NAME_KEY);
                String token = httpServletRequest.getHeader(UserConstant.USER_TOKEN_KEY);
                if (!StrUtil.isAllNotBlank(userName,token)){
                    throw new ClientException(USER_TOKEN_FAIL);
                }
                String key= RedisCacheConstant.USER_LOGIN_KEY+userName;
                Object userInfoJsonStr =null;
                try{
                    userInfoJsonStr=stringRedisTemplate.opsForHash().get(key, token);
                    if (userInfoJsonStr==null){
                        throw new ClientException(USER_TOKEN_FAIL);
                    }
                }catch (Exception ex){
                    throw new ClientException(USER_TOKEN_FAIL);
                }
                    UserInfoDTO userInfoDTO = JSON.parseObject(userInfoJsonStr.toString(), UserInfoDTO.class);
                    UserContext.setUser(userInfoDTO);
            }
        }
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            UserContext.removeUser();
        }
    }
}
