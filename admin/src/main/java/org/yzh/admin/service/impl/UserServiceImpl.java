package org.yzh.admin.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.yzh.admin.common.convention.exception.ClientException;
import org.yzh.admin.common.enums.UserErrorCodeEnum;
import org.yzh.admin.dao.entity.UserDO;
import org.yzh.admin.dao.mapper.UserMapper;
import org.yzh.admin.dto.request.UserRegisterReqDTO;
import org.yzh.admin.dto.response.UserRespDTO;
import org.yzh.admin.service.UserService;

import static org.yzh.admin.common.constant.RedisCacheConstant.LOCK_USER_REGISTER_KEY;

/**
* 用户接口实现层
*/
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO>
    implements UserService {
private final RBloomFilter<String> userRegisterCachePenetrationBloomFilter;
private final RedissonClient redissonClient;

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
}




