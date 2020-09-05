package com.woowacourse.taggle.authentication;

import javax.naming.AuthenticationException;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.woowacourse.taggle.user.domain.User;
import com.woowacourse.taggle.user.domain.UserRepository;
import com.woowacourse.taggle.user.dto.SessionUser;
import com.woowacourse.taggle.user.exception.UserNotFoundException;

@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    private final UserRepository userRepository;

    public UserArgumentResolver(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal().equals("anonymousUser")) {
            throw new AuthenticationException("인증하지 않은 사용자입니다");
        }

        return getUser(authentication);
    }

    private SessionUser getUser(final Authentication authentication) {
        final DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User)authentication.getPrincipal();
        final User user = userRepository.findByEmail(defaultOAuth2User.getAttributes().get("email").toString())
                .orElseThrow(() -> new UserNotFoundException("사용자가 존재하지 않습니다."));
        return new SessionUser(user);
    }
}
