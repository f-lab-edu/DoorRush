package com.flab.doorrush.global.config;

import com.flab.doorrush.domain.authentication.api.AutoLoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

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
