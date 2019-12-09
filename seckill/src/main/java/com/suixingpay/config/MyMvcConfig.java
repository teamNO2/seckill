package com.suixingpay.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 *@Author 孙克强
 */
@Configuration
class MyMvcConfig implements WebMvcConfigurer {

    //配置映射路径
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    //配置拦截器
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyHandlerIntercepter())
                .excludePathPatterns("/login/**")
                .excludePathPatterns("/swagger-ui.html")
                .excludePathPatterns("swagger-ui.html")
                .excludePathPatterns("/webjars/**")
                .excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg", "/**/*.jpeg", "/*.html", "/**/*.html","/swagger-resources/**")
                .addPathPatterns("/**");
    }

}
