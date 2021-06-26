package cn.enaium.misc.bilibili.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Enaium
 */
@Data
@TableName("video_list")
public class VideoEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String videoUrl;
}
