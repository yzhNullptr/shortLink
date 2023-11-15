package org.yzh.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.yzh.admin.common.convention.result.Result;
import org.yzh.admin.common.convention.result.Results;
import org.yzh.admin.dto.request.UserLoginReqDTO;
import org.yzh.admin.dto.request.UserRegisterReqDTO;
import org.yzh.admin.dto.request.UserUpdateReqDTO;
import org.yzh.admin.dto.response.UserActualRespDTO;
import org.yzh.admin.dto.response.UserLoginRespDTO;
import org.yzh.admin.dto.response.UserRespDTO;
import org.yzh.admin.service.UserService;

/**
 * 用户管理控制层
 */

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 根据用户名查询用户脱敏信息
     * @param username 用户名
     * @return 返回用户信息
     */
    @GetMapping("/api/shortLink/v1/user/{username}")
    public Result<UserRespDTO> getUserByUsername(@PathVariable("username") String username) {
        return Results.success(userService.getUserByUsername(username));
    }

    /**
     * 根据用户名查询用户未脱敏信息
     * @param username 用户名
     * @return 返回用户信息
     */
    @GetMapping("/api/shortLink/v1/actual/user/{username}")
    public Result<UserActualRespDTO> getActualUserByUsername(@PathVariable("username") String username) {
        return Results.success(BeanUtil.toBean(userService.getUserByUsername(username),UserActualRespDTO.class));
    }

    /**
     * 查询用户名是否已存在
     * @param username 用户户名
     * @return 是否存在
     */
    @GetMapping("/api/shortLink/v1/user/has-username")
    public Result<Boolean> hasUsername(@RequestParam("username") String username){
        return Results.success(userService.hasUsername(username));
    }

    /**
     * 注册用户
     * @param requestParam 用户参数
     * @return 注册成功或者抛出异常
     */
    @PostMapping("/api/shortLink/v1/user")
    public Result<Void> register(@RequestBody UserRegisterReqDTO requestParam){
        userService.register(requestParam);
        return Results.success();
    }

    /**
     * 修改用户
     */
    @PutMapping("/api/shortLink/v1/user")
    public Result<Void> update(@RequestBody UserUpdateReqDTO requestParam){
        userService.update(requestParam);
        return Results.success();
    }

    /**
     * 用户登录
     */
    @PostMapping("/api/shortLink/v1/user/login")
    public Result<UserLoginRespDTO> login(@RequestBody UserLoginReqDTO requestParma){
        return Results.success(userService.login(requestParma));
    }

    /**
     * 检查用户是否登录
     */
    @GetMapping("/api/shortLink/v1/user/check-login")
    public Result<Boolean> checkLogin(@RequestParam("username")String username,@RequestParam("token")String token){
        return Results.success( userService.checkLogin(username,token));
    }
}

