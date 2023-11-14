package org.yzh.admin.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.yzh.admin.common.convention.exception.ClientException;
import org.yzh.admin.common.enums.UserErrorCodeEnum;
import org.yzh.admin.dao.entity.UserDO;
import org.yzh.admin.dao.mapper.UserMapper;
import org.yzh.admin.dto.response.UserRespDTO;
import org.yzh.admin.service.UserService;

/**
* 用户接口实现层
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO>
    implements UserService {


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
}




