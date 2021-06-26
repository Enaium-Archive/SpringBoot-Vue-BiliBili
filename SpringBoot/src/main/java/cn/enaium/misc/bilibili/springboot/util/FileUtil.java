package cn.enaium.misc.bilibili.springboot.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author Enaium
 */
@Component
public class FileUtil {
    @Value("${cn.enaium.BiliBili.dir}")
    private String dir;

    public String getDir() {
        return dir;
    }

    public String getImgDir() {
        return dir + "/img";
    }

    public String getVideoDir() {
        return dir + "/video";
    }
}
