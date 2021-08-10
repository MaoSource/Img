package com.source.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Source
 * @Date: 2021/03/16/16:06
 * @Description:
 */
@Configuration
public class GlobalCorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*")
                .allowedMethods("GET","POST","PUT","DELETE","OPTIONS","HEAD")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }
}
