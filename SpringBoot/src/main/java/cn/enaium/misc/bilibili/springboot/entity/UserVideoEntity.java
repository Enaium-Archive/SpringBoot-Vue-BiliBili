package cn.enaium.misc.bilibili.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Enaium
 */
@Data
@TableName("user_video_list")
public class UserVideoEntity {
    @TableId
    private Long id;
    private String videoIdList;
}
