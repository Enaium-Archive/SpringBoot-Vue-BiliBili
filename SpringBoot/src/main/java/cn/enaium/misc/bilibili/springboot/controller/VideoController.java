package cn.enaium.misc.bilibili.springboot.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.enaium.misc.bilibili.springboot.entity.CategoryEntity;
import cn.enaium.misc.bilibili.springboot.entity.UserVideoEntity;
import cn.enaium.misc.bilibili.springboot.entity.VideoEntity;
import cn.enaium.misc.bilibili.springboot.entity.VideoInfoEntity;
import cn.enaium.misc.bilibili.springboot.mapper.CategoryMapper;
import cn.enaium.misc.bilibili.springboot.mapper.UserVideoMapper;
import cn.enaium.misc.bilibili.springboot.mapper.VideoInfoMapper;
import cn.enaium.misc.bilibili.springboot.mapper.VideoMapper;
import cn.enaium.misc.bilibili.springboot.struct.Result;
import cn.enaium.misc.bilibili.springboot.util.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.apache.commons.io.FileUtils.writeByteArrayToFile;

/**
 * @author Enaium
 */
@RestController
@RequestMapping("/api")
public class VideoController {

    private final VideoMapper videoMapper;
    private final VideoInfoMapper videoInfoMapper;
    private final UserVideoMapper userVideoMapper;
    private final CategoryMapper categoryMapper;

    private final FileUtil fileUtil;

    public VideoController(VideoMapper videoMapper, VideoInfoMapper videoInfoMapper, UserVideoMapper userVideoMapper, CategoryMapper categoryMapper, FileUtil fileUtil) {
        this.videoMapper = videoMapper;
        this.videoInfoMapper = videoInfoMapper;
        this.userVideoMapper = userVideoMapper;
        this.categoryMapper = categoryMapper;
        this.fileUtil = fileUtil;
    }


    /**
     * 创建视
     */
    @PostMapping("/createVideo")
    private Result<Long> createVideo() {
        var videoEntity = new VideoEntity();//视频
        videoEntity.setId(null);
        videoEntity.setVideoUrl("");//视频链接为空
        var userVideoEntity = userVideoMapper.selectById(StpUtil.getLoginIdAsLong());//获取该用户的视频列表

        boolean exist = false;
        long existId = -1;

        if (userVideoEntity != null) {
            for (String id : userVideoEntity.getVideoIdList().split(",")) {
                var videoEntity1 = videoMapper.selectById(id);
                if (videoEntity1 != null && videoEntity1.getVideoUrl().isEmpty()) {
                    existId = Long.parseLong(id);
                    exist = true;
                }
            }
        }


        var i = 0L;

        if (exist) {
            i = existId;
        } else {
            videoMapper.insert(videoEntity);
            i = videoEntity.getId();
        }

        if (userVideoEntity != null) {
            if (!exist) {
                userVideoEntity.setVideoIdList(userVideoEntity.getVideoIdList() + "," + videoEntity.getId());
                userVideoMapper.updateById(userVideoEntity);//更新视频列表
            }
        } else {
            var newUserVideoEntity = new UserVideoEntity();//如果 该用户没有视频列表 就 创建新的视频列表
            newUserVideoEntity.setId(StpUtil.getLoginIdAsLong());
            newUserVideoEntity.setVideoIdList(videoEntity.getId().toString());
            userVideoMapper.insert(newUserVideoEntity);//插入视频列表

            var videoInfoEntity = new VideoInfoEntity();
            videoInfoEntity.setId(i);
            videoInfoEntity.setUserId(StpUtil.getLoginIdAsLong());
            videoInfoEntity.setTitle("");
            videoInfoEntity.setDescription("");
            videoInfoEntity.setPicUrl("");
            videoInfoEntity.setCategory(-1L);
            videoInfoMapper.insert(videoInfoEntity);
        }
        return new Result<>(Result.FAIL, "创建成功", i);
    }

    /**
     * @param file    视频
     * @param videoId 视频id
     * @return 是否上传成功
     */
    @PutMapping("/uploadVideo")
    private Result<String> uploadVideo(@RequestParam("file") MultipartFile file, @RequestParam("videoId") Long videoId, HttpServletRequest request) throws IOException {
        var userVideoEntity = userVideoMapper.selectById(StpUtil.getLoginIdAsLong());
        if (userVideoEntity != null) {
            if (checkVideo(videoId)) {
                var videoEntity = videoMapper.selectById(videoId);
                if (videoEntity != null) {
                    var videoPath = fileUtil.getVideoDir() + "/userVideo/" + videoId + "/video.mp4";
                    writeByteArrayToFile(new File(".", videoPath), file.getBytes());
                    var url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/" + videoPath;
                    videoEntity.setVideoUrl(url);
                    videoMapper.updateById(videoEntity);
                    return new Result<>(Result.SUCCESS, "上传成功", url);
                } else {
                    return new Result<>(Result.FAIL, "未创建视频");
                }
            } else {
                return new Result<>(Result.FAIL, "只能上传自己的视频");
            }
        } else {
            return new Result<>(Result.FAIL, "未创建视频");
        }
    }

    /**
     * 设置视频信息
     *
     * @param videoInfoEntity 视频信息
     * @return 是否修改成功
     */
    @PostMapping("/setVideoInfo")
    private Result<String> setVideoInfo(@RequestBody VideoInfoEntity videoInfoEntity) {
        System.out.println(videoInfoEntity.toString());

        videoInfoEntity.setUserId(StpUtil.getLoginIdAsLong());
        if (checkVideo(videoInfoEntity.getId())) {
            var videoInfoEntity1 = videoInfoMapper.selectById(videoInfoEntity.getId());
            if (videoInfoEntity1 != null) {
                videoInfoMapper.updateById(videoInfoEntity);
                return new Result<>(Result.FAIL, "设置成功");
            } else {
                return new Result<>(Result.FAIL, "未创建视频");
            }
        } else {
            return new Result<>(Result.FAIL, "只能修改自己的视频信息");
        }
    }

    /**
     * 上传封面
     *
     * @param file    图片
     * @param videoId 视频id
     * @param request request
     * @return 是否上传成功
     */
    @PutMapping("/uploadPic")
    private Result<String> uploadPic(@RequestParam("file") MultipartFile file, @RequestParam("videoId") Long videoId, HttpServletRequest request) throws IOException {
        if (checkVideo(videoId)) {
            var picPath = fileUtil.getImgDir() + "/pic/" + videoId + "/pic.jpg";
            writeByteArrayToFile(new File(".", picPath), file.getBytes());
            var url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/" + picPath;
            return new Result<>(Result.SUCCESS, "上传成功", url);
        } else {
            return new Result<>(Result.FAIL, "只能上传自己视频的封面");
        }
    }

    @GetMapping("/getMyVideoList")
    private Result<String[]> getMyVideoList() {
        var userVideoEntity = userVideoMapper.selectById(StpUtil.getLoginIdAsLong());
        if (userVideoEntity != null) {
            return new Result<>(Result.SUCCESS, "获取成功", userVideoEntity.getVideoIdList().split(","));
        }
        return new Result<>(Result.FAIL, "获取失败");
    }

    @GetMapping("/getVideoUrl")
    private Result<String> getVideoUrl(@RequestParam("videoId") Long videoId) {
        var videoEntity = videoMapper.selectById(videoId);
        if (videoEntity != null) {
            return new Result<>(Result.SUCCESS, "获取成功", videoEntity.getVideoUrl());
        } else {
            return new Result<>(Result.FAIL, "没有这个视频");
        }
    }

    /**
     * 检查是否是自己的视频
     *
     * @param videoId 视频id
     * @return 是不是自己的视频
     */
    private boolean checkVideo(Long videoId) {
        var userVideoEntity = userVideoMapper.selectById(StpUtil.getLoginIdAsLong());
        if (userVideoEntity != null) {
            var split = new ArrayList<String>();
            Collections.addAll(split, userVideoEntity.getVideoIdList().split(","));
            return split.contains(videoId.toString());
        }
        return false;
    }


    /**
     * 获取所有分类
     *
     * @return 所有分类
     */
    @GetMapping("/static/getCategory")
    private Result<List<CategoryEntity>> getCategory() {
        return new Result<>(Result.SUCCESS, "获取分类成功", categoryMapper.selectList(null));
    }

    /**
     * 获取视频数量
     *
     * @return 视频数量
     */
    @GetMapping("/static/getVideoCount")
    private Result<Long> getVideoCount() {
        return new Result<>(Result.SUCCESS, "获取成功", (long) videoInfoMapper.selectList(null).size());
    }

    /**
     * 根据视频分类获取视频数量
     *
     * @param category 分类
     * @return 视频数量
     */
    @GetMapping("/static/getVideoCountByCategory")
    private Result<Long> getVideoCountByCategory(@RequestParam("category") Long category) {
        var q = new QueryWrapper<VideoInfoEntity>();
        q.eq("category", category);
        return new Result<>(Result.SUCCESS, "获取成功", (long) videoInfoMapper.selectList(q).size());
    }

    /**
     * 根据视频分类获取视频信息
     *
     * @param category 分类
     * @return 视频数量
     */
    @GetMapping("/static/getVideoInfoByCategory")
    private Result<List<VideoInfoEntity>> getVideoInfoByCategory(@RequestParam("category") Long category) {
        var q = new QueryWrapper<VideoInfoEntity>();
        q.eq("category", category);
        return new Result<>(Result.SUCCESS, "获取成功", videoInfoMapper.selectList(q));
    }

    /**
     * 获取视频信息
     *
     * @param videoId 视频Id
     * @return 视频信息
     */
    @GetMapping("/static/getVideoInfo")
    private Result<VideoInfoEntity> getVideoInfo(@RequestParam("videoId") Long videoId) {
        var videoInfoEntity = videoInfoMapper.selectById(videoId);
        if (videoInfoEntity != null) {
            return new Result<>(Result.SUCCESS, "获取成功", videoInfoEntity);
        } else {
            return new Result<>(Result.FAIL, "获取失败");
        }
    }

    /**
     * 获取用户视频列表
     *
     * @param userId 用户id
     * @return 视频列表
     */
    @GetMapping("/static/getUserVideoList")
    private Result<String[]> getUserVideoList(@RequestParam("userId") Long userId) {
        var userVideoEntity = userVideoMapper.selectById(userId);
        if (userVideoEntity != null) {
            return new Result<>(Result.SUCCESS, "获取成功", userVideoEntity.getVideoIdList().split(","));
        } else {
            return new Result<>(Result.FAIL, "获取失败");
        }
    }
}
