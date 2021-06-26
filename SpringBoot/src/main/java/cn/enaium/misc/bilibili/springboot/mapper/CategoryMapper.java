package cn.enaium.misc.bilibili.springboot.mapper;

import cn.enaium.misc.bilibili.springboot.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Enaium
 */
@Mapper
public interface CategoryMapper extends BaseMapper<CategoryEntity> {
}
