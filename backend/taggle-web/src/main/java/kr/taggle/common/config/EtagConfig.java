package kr.taggle.common.config;

import java.time.Duration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class EtagConfig implements WebMvcConfigurer {

    private static final int CACHE_PERIOD = Math.toIntExact(Duration.ofDays(365).getSeconds());

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(CACHE_PERIOD)
                .setCacheControl(CacheControl.noCache().mustRevalidate().cachePublic());
    }

    @Bean
    public FilterRegistrationBean<ShallowEtagHeaderFilter> filterRegistrationBean() {
        FilterRegistrationBean<ShallowEtagHeaderFilter> registration = new FilterRegistrationBean<>(
                new ShallowEtagHeaderFilter());
        registration.addUrlPatterns("/*");
        return registration;
    }
}
