package org.yzh.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.yzh.admin.dao.entity.UserDO;
import org.yzh.admin.dto.request.UserRegisterReqDTO;
import org.yzh.admin.dto.response.UserRespDTO;

/**
 * 用户接口
*/
public interface UserService extends IService<UserDO> {
    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 返回用户信息
     */
    UserRespDTO getUserByUsername(String username);

    /**
     * 查询用户名是否已存在
     *
     * @param username 用户名
     * @return 用户名存在返回 true，  不存在返回 false
     */
    Boolean hasUsername(String username);

    /**
     *注册用户
     *
     * @param requestParma 用户注册请求参数
     */
    void register(UserRegisterReqDTO requestParma);
}
