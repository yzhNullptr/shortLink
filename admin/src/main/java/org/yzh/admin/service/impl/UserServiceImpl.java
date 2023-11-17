package org.yzh.admin.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.yzh.admin.common.convention.exception.ClientException;
import org.yzh.admin.common.enums.UserErrorCodeEnum;
import org.yzh.admin.dao.entity.UserDO;
import org.yzh.admin.dao.mapper.UserMapper;
import org.yzh.admin.dto.request.UserLoginReqDTO;
import org.yzh.admin.dto.request.UserRegisterReqDTO;
import org.yzh.admin.dto.request.UserUpdateReqDTO;
import org.yzh.admin.dto.response.UserLoginRespDTO;
import org.yzh.admin.dto.response.UserRespDTO;
import org.yzh.admin.service.UserService;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.yzh.admin.common.constant.RedisCacheConstant.LOCK_USER_REGISTER_KEY;
import static org.yzh.admin.common.constant.RedisCacheConstant.USER_LOGIN_KEY;

/**
* 用户接口实现层
*/
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO>
    implements UserService {
    private final RBloomFilter<String> userRegisterCachePenetrationBloomFilter;
    private final RedissonClient redissonClient;
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public UserRespDTO getUserByUsername(String username) {
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, username);
        UserDO userDO = baseMapper.selectOne(queryWrapper);
        if (userDO==null){
            throw new ClientException(UserErrorCodeEnum.USER_NULL);
        }else{
            UserRespDTO result = new UserRespDTO();
            BeanUtils.copyProperties(userDO,result);
            return result;
        }
    }

    @Override
    public Boolean hasUsername(String username) {
        return !userRegisterCachePenetrationBloomFilter.contains(username);
    }

    @Override
    public void register(UserRegisterReqDTO requestParma) {
        if (!hasUsername(requestParma.getUsername())){
            throw new ClientException(UserErrorCodeEnum.USER_NAME_EXIST);
        }
        RLock lock = redissonClient.getLock(LOCK_USER_REGISTER_KEY+requestParma.getUsername());
        try {
            if (lock.tryLock()) {
                int insert = baseMapper.insert(BeanUtil.toBean(requestParma, UserDO.class));
                if (insert < 1) {
                    throw new ClientException(UserErrorCodeEnum.USER_SAVE_ERROR);
                }
                userRegisterCachePenetrationBloomFilter.add(requestParma.getUsername());

            }else{
                throw new ClientException(UserErrorCodeEnum.USER_NAME_EXIST);
            }

        }finally {
            lock.unlock();
        }
    }

    @Override
    public void update(UserUpdateReqDTO requestParma) {
        //TODO 验证当前用户明是否为登录用户
        LambdaUpdateWrapper<UserDO> updateWrapper = Wrappers.lambdaUpdate(UserDO.class)
                .eq(UserDO::getUsername, requestParma.getUsername());
        baseMapper.update(BeanUtil.toBean(requestParma, UserDO.class),updateWrapper);
    }

    @Override
    public UserLoginRespDTO login(UserLoginReqDTO requestParma) {
        String key=USER_LOGIN_KEY+requestParma.getUsername();
        LambdaQueryWrapper<UserDO> lambdaQueryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, requestParma.getUsername())
                .eq(UserDO::getPassword, requestParma.getPassword())
                .eq(UserDO::getDelFlag, 0);
        UserDO userDO = baseMapper.selectOne(lambdaQueryWrapper);
        if (userDO==null){
            throw new ClientException("用户不存在");
        }
        Boolean hasLogin = stringRedisTemplate.hasKey(key);
        if(hasLogin!=null&&hasLogin){
            throw new ClientException("用户已登录");
        }else{
            /**
             * Hash
             * Key:shortLink:user:login_用户名
             * Value：
             *  key：token
             *  val：JSON字符串（用户信息）
             */
            String uuid = UUID.randomUUID().toString();
            stringRedisTemplate.opsForHash().put(key,uuid,JSON.toJSONString(userDO));
            stringRedisTemplate.expire(key,30L, TimeUnit.DAYS);
            return new UserLoginRespDTO(uuid);
        }
    }

    @Override
    public Boolean checkLogin(String username,String token) {
        String key=USER_LOGIN_KEY+username;
        return  stringRedisTemplate.opsForHash().get(key,token)!=null ;
    }

    @Override
    public void logout(String username, String token) {
        String key=USER_LOGIN_KEY+username;
        if (checkLogin(username,token)){
            stringRedisTemplate.delete(key);
            return;
        }
        throw new ClientException("用户token不存在或用户未登录");
    }
}




