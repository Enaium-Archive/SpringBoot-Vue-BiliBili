package cn.enaium.misc.bilibili.springboot.configuration;

import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author Enaium
 */
@Configuration
public class SaTokenConfiguration implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaRouteInterceptor((request, response, handler) -> {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Methods", "*");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "*");
            if ("OPTIONS".equals(request.getMethod())) return;

            SaRouter.match(Collections.singletonList("/**"),
                    Arrays.asList("/api/login", "/api/register", "/api/isLogin", "/api/getId",
                            "/file/img/avatar/**", "/api/static/**", "/file/img/pic/**", "/file/video/userVideo/**"),
                    StpUtil::checkLogin);


//            SaRouter.match("/delete/**", () -> StpUtil.checkRoleOr("admin"));
        })).addPathPatterns("/**");
    }
}
