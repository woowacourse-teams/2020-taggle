package com.woowacourse.taggle.security.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.woowacourse.taggle.security.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    // @formatter:off
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable();

        http
                .authorizeRequests()
                        .antMatchers("/", "/h2-console/**").permitAll()
                        .antMatchers("/", "/api/**").permitAll() // @FIXME: 테스트를 위해 spring security off
                .anyRequest().authenticated()
                .and()
                        .cors()
                .and()
                        .logout()
                        .logoutSuccessUrl("/")
                .and()
                        .oauth2Login()
                        .userInfoEndpoint()
                        .userService(customOAuth2UserService);
    }

    // @FIXME: 테스트를 위해 spring security off
    // @Bean
    // public CorsConfigurationSource corsConfigurationSource() {
    //     CorsConfiguration configuration = new CorsConfiguration();
    //
    //     configuration.addAllowedOrigin("http://localhost:3000");
    //     configuration.addAllowedHeader("*");
    //     configuration.addAllowedMethod("*");
    //     configuration.setAllowCredentials(true);
    //
    //     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    //     source.registerCorsConfiguration("/**", configuration);
    //     return source;
    // }
}
