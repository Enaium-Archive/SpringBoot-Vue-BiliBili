package cn.enaium.misc.bilibili.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Enaium
 */
@Data
@TableName("video_info_list")
public class VideoInfoEntity {
    @TableId
    private Long id;
    private Long userId;
    private String title;
    private String description;
    private Long category;
    private String picUrl;
}
