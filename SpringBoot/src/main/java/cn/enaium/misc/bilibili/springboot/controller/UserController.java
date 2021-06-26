package cn.enaium.misc.bilibili.springboot.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.enaium.misc.bilibili.springboot.entity.UserInfoEntity;
import cn.enaium.misc.bilibili.springboot.mapper.UserInfoMapper;
import cn.enaium.misc.bilibili.springboot.struct.Result;
import cn.enaium.misc.bilibili.springboot.util.FileUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

/**
 * @author Enaium
 */
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserInfoMapper userInfoMapper;
    private final FileUtil fileUtil;

    public UserController(UserInfoMapper userInfoMapper, FileUtil fileUtil) {
        this.userInfoMapper = userInfoMapper;
        this.fileUtil = fileUtil;
    }


    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("/static/getUserInfo")
    private Result<UserInfoEntity> getUserInfo(@RequestParam("userId") Long userId) {
        var userInfoEntity = userInfoMapper.selectById(userId);
        if (userInfoEntity == null) {
            return new Result<>(Result.FAIL, "未找到此用户");
        }
        return new Result<>(Result.SUCCESS, "获取用户信息成功", userInfoEntity);
    }

    /**
     * 设置用户信息
     *
     * @param userInfoEntity 用户信息
     * @return 用户信息
     */
    @PostMapping("/setUserInfo")
    private Result<UserInfoEntity> setUserInfo(@RequestBody UserInfoEntity userInfoEntity) {
        userInfoEntity.setId(StpUtil.getLoginIdAsLong());//设置当前会话ID
        userInfoMapper.updateById(userInfoEntity);//根据ID更新信息
        return new Result<>(Result.SUCCESS, "更新成功", userInfoEntity);
    }

    /**
     * 上传头像
     *
     * @return 头像路径
     */
    @PutMapping("/uploadAvatar")
    private Result<String> uploadAvatar(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        var avatarPath = fileUtil.getImgDir() + "/avatar/" + StpUtil.getLoginIdAsLong() + "/avatar.jpg";
        try {
            FileUtils.writeByteArrayToFile(new File(".", avatarPath), file.getBytes());
            var url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/" + avatarPath;
            return new Result<>(Result.SUCCESS, "上传成功", url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result<>(Result.FAIL, "上传失败");
    }
}
