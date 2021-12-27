package com.flab.doorrush.config;

import com.flab.doorrush.domain.user.api.AutoLoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfigurer implements WebMvcConfigurer {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(autoLoginInterceptor())
        .addPathPatterns("/users/autoLogin", "/users/login");
  }

  @Bean
  public AutoLoginInterceptor autoLoginInterceptor() {
    return new AutoLoginInterceptor();
  }
}
