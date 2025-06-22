package com.example.project.config;

import com.example.project.controller.AuthInterceptor;
import com.example.project.controller.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

    @Autowired
    private UserInterceptor userInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/login", "/logout", "/css/**", "/js/**", "/images/**");

        registry.addInterceptor(userInterceptor)
                .addPathPatterns("/client/**")
                .excludePathPatterns("/login", "/logout", "/css/**", "/js/**", "/images/**");
    }

}
