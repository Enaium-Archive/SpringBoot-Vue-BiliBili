package cn.enaium.misc.bilibili.springboot.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.enaium.misc.bilibili.springboot.entity.UserEntity;
import cn.enaium.misc.bilibili.springboot.entity.UserInfoEntity;
import cn.enaium.misc.bilibili.springboot.mapper.UserInfoMapper;
import cn.enaium.misc.bilibili.springboot.mapper.UserMapper;
import cn.enaium.misc.bilibili.springboot.struct.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Enaium
 */

@RestController
@RequestMapping("/api")
public class LoginController {

    /**
     * 用户映射
     */
    private final UserMapper userMapper;
    /**
     * 用户信息映射
     */
    private final UserInfoMapper userInfoMapper;

    public LoginController(UserMapper userMapper, UserInfoMapper userInfoMapper) {
        this.userMapper = userMapper;
        this.userInfoMapper = userInfoMapper;
    }

    @PostMapping("/register")
    private Result<String> register(@RequestBody UserEntity userEntity) {
        var queryWrapper = new QueryWrapper<UserEntity>();
        queryWrapper.eq("username", userEntity.getUsername());
        if (userMapper.selectOne(queryWrapper) != null) {
            return new Result<>(Result.FAIL, "用户名已存在");
        }

        userEntity.setId(null);

        //插入用户
        userMapper.insert(userEntity);

        var userInfoEntity = new UserInfoEntity();
        //获取用户id
        userInfoEntity.setId(userEntity.getId());
        //昵称
        userInfoEntity.setNickname(userEntity.getNickname());
        //插入用户信息
        userInfoMapper.insert(userInfoEntity);

        return new Result<>(Result.SUCCESS, "注册成功");
    }

    @PostMapping("/login")
    private Result<String> login(@RequestBody UserEntity userEntity) {
        var queryWrapper = new QueryWrapper<UserEntity>();

        queryWrapper.eq("username", userEntity.getUsername());
        queryWrapper.eq("password", userEntity.getPassword());
        var select = userMapper.selectOne(queryWrapper);
        if (select == null) {
            return new Result<>(Result.FAIL, "登录失败");
        }
        StpUtil.login(select.getId());

        return new Result<>(Result.SUCCESS, "登录成功", StpUtil.getTokenInfo().getTokenValue());
    }

    @GetMapping("/logout")
    private Result<String> logout() {
        StpUtil.logout();
        return new Result<>(Result.SUCCESS, "登出成功");
    }

    @GetMapping("/isLogin")
    private Boolean isLogin() {
        return StpUtil.isLogin();
    }

    @GetMapping("/getId")
    private Long getId() {
        return StpUtil.getLoginIdAsLong();
    }
}
