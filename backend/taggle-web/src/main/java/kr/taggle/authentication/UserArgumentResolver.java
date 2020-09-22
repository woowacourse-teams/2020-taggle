package kr.taggle.authentication;

import java.util.Map;

import javax.naming.AuthenticationException;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import kr.taggle.user.domain.User;
import kr.taggle.user.domain.UserRepository;
import kr.taggle.user.dto.SessionUser;
import kr.taggle.user.exception.UserNotFoundException;

@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String EMAIL = "email";
    private static final String KAKAO = "kakao";
    private static final String ANONYMOUS_USER = "anonymousUser";

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
        final Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (authentication.getPrincipal().equals(ANONYMOUS_USER)) {
            throw new AuthenticationException("인증하지 않은 사용자입니다");
        }

        final OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken)authentication;

        return getUser(authenticationToken);
    }

    private SessionUser getUser(final OAuth2AuthenticationToken authentication) {
        final DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User)authentication.getPrincipal();

        final User user = getUserWithRegistrationId(authentication.getAuthorizedClientRegistrationId(),
                defaultOAuth2User);
        return new SessionUser(user);
    }

    private User getUserWithRegistrationId(final String registrationId, final DefaultOAuth2User defaultOAuth2User) {
        if (KAKAO.equals(registrationId)) {
            final Map<String, Object> kakaoAcount = (Map<String, Object>)defaultOAuth2User.getAttributes()
                    .get("kakao_account");

            return userRepository.findByEmail(kakaoAcount.get(EMAIL).toString())
                    .orElseThrow(() -> new UserNotFoundException("사용자가 존재하지 않습니다."));
        }

        return userRepository.findByEmail(defaultOAuth2User.getAttributes().get(EMAIL).toString())
                .orElseThrow(() -> new UserNotFoundException("사용자가 존재하지 않습니다."));
    }
}
