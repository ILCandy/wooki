package com.wooki.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/7
 * Time : 上午1:18
 */
@Configuration
public class Adapter extends WebMvcConfigurerAdapter {

    @Bean
    public CustomInterceptor customInterceptor(){
        return new CustomInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(customInterceptor())
                .excludePathPatterns("/error/**", "/403/**", "/404/**", "/405/**","/**/*.html");
//                .excludePathPatterns("/error/**", "/403/**", "/404/**", "/405/**");
        super.addInterceptors(registry);
    }

}
