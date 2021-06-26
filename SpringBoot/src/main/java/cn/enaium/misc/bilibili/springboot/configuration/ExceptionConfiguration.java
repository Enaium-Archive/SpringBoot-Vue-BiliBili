package cn.enaium.misc.bilibili.springboot.configuration;

import cn.dev33.satoken.exception.NotLoginException;
import cn.enaium.misc.bilibili.springboot.struct.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Enaium
 */
@ControllerAdvice
public class ExceptionConfiguration {
    @ResponseBody
    @ExceptionHandler
    public Result<String> handlerException(Exception e, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (e instanceof NotLoginException) {
            return new Result<>(Result.TOKEN_ERROR, e.getMessage(), getCurrentTime());
        } else {
            e.printStackTrace();
        }
        return new Result<>(Result.FAIL, e.getMessage(), getCurrentTime());
    }

    private String getCurrentTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}
