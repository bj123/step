package com.aiwsport.web;

import com.aiwsport.web.interceptor.AccessHandlerInteceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
@ComponentScan(basePackages = {"com.aiwsport.web", "com.aiwsport.core"})
@MapperScan(basePackages = {"com.aiwsport.core.mapper"})
@EnableTransactionManagement
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    @Configuration
    static class WebMvc implements WebMvcConfigurer {
        //增加拦截器
        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(new AccessHandlerInteceptor())    //指定拦截器类
                    .addPathPatterns("/step/**");        //指定该类拦截的url
        }
    }
}