package kr.taggle.security.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import kr.taggle.security.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    public void configure(final WebSecurity webSecurity) {
        webSecurity.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
        webSecurity.ignoring().antMatchers("/taggle-favicon.ico", "/img/**");
    }

    // @formatter:off
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable();

        http
                .authorizeRequests()
                        .antMatchers("/", "/signin","/h2-console/**","/api/v1//me").permitAll()
                        .antMatchers("/api/**").hasRole("USER")
                        .antMatchers("/docs/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                        .cors()
                .and()
                        .logout()
                        .logoutUrl("/oauth2/logout")
                        .logoutSuccessUrl("/signin")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                .and()
                        .oauth2Login()
                        .loginPage("/")
                        .userInfoEndpoint()
                        .userService(customOAuth2UserService);
    }

    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        corsConfiguration.addAllowedMethod(HttpMethod.PUT);
        corsConfiguration.addAllowedMethod(HttpMethod.DELETE);
        corsConfiguration.addExposedHeader("Location");
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
