package cn.enaium.misc.bilibili.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author Enaium
 */
@Data
@TableName("user_info_list")
public class UserInfoEntity {
    @TableId
    private Long id;
    private String avatarUrl = "";
    private String nickname;
    private String description = "";
    private byte gender = -1;
}
