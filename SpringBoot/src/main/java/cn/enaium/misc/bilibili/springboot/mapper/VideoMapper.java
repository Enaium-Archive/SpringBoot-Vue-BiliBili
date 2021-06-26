package cn.enaium.misc.bilibili.springboot.mapper;

import cn.enaium.misc.bilibili.springboot.entity.VideoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Enaium
 */
@Mapper
public interface VideoMapper extends BaseMapper<VideoEntity> {
}
