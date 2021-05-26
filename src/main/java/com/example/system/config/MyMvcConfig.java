package com.example.system.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/main.html").setViewName("teaindex");
        registry.addViewController("/teaindex.html").setViewName("teaindex");
        registry.addViewController("/forms").setViewName("forms");

        registry.addViewController("/test").setViewName("test");
        registry.addViewController("/user/student").setViewName("stuindex.html");

    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**").excludePathPatterns("/user/*","/login","/","/css/*","/js/*","/assets/**");
    }
}
