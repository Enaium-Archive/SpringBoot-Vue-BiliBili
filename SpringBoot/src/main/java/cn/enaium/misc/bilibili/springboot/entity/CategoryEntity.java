package cn.enaium.misc.bilibili.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Enaium
 */
@Data
@TableName("category_list")
public class CategoryEntity {
    @TableId
    private Long id;
    private String title;
}
