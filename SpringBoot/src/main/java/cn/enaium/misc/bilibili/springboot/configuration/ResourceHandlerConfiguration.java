package cn.enaium.misc.bilibili.springboot.configuration;

import cn.enaium.misc.bilibili.springboot.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/**
 * @author Enaium
 */
@Configuration
public class ResourceHandlerConfiguration implements WebMvcConfigurer {

    @Value("${cn.enaium.BiliBili.dir}")
    private String dir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/file/**").
                addResourceLocations("file:" + new File(dir).getAbsolutePath() + "/");
    }
}
