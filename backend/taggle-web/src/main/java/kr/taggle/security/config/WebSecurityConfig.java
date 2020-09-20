package kr.taggle.security.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import kr.taggle.security.provider.CustomOAuth2Provider;
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

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(
            final OAuth2ClientProperties oAuth2ClientProperties,
            @Value("${spring.security.oauth2.client.registration.kakao.client-id}")final String kakaoClientId,
            @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")final String kakaoSecretId){

        final List<ClientRegistration> registrations = new ArrayList<>();

        final OAuth2ClientProperties.Registration registration = oAuth2ClientProperties.getRegistration().get("google");

        registrations.add(
                CommonOAuth2Provider.GOOGLE.getBuilder("google")
                .clientId(registration.getClientId())
                .clientSecret(registration.getClientSecret())
                .scope("email", "profile")
                .build()
        );

        registrations.add(
                CustomOAuth2Provider.KAKAO.getBuilder("kakao")
                .clientId(kakaoClientId)
                .clientSecret(kakaoSecretId)
                .build()
        );

        return new InMemoryClientRegistrationRepository(registrations);
    }
}
