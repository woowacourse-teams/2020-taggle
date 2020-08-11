package com.woowacourse.taggle.security.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.woowacourse.taggle.authentication.UserArgumentResolver;

@Configuration
public class UserConfig implements WebMvcConfigurer {

    @Autowired
    private UserArgumentResolver userArgumentResolver;

    @Override
    public void addArgumentResolvers(final List argumentResolvers) {
        argumentResolvers.add(userArgumentResolver);
    }
}
