package kr.taggle.security.service;

import static java.util.Collections.*;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.taggle.user.domain.User;
import kr.taggle.user.domain.UserRepository;
import kr.taggle.user.dto.OAuthAttributes;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public OAuth2User loadUser(final OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        final OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        final OAuth2User oAuth2User = delegate.loadUser(userRequest);

        final String registrationId = userRequest.getClientRegistration().getRegistrationId();
        final String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        final OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName,
                oAuth2User.getAttributes());
        final User user = saveOrFind(attributes);

        return new DefaultOAuth2User(singleton(new SimpleGrantedAuthority(user.getRole().getKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private User saveOrFind(final OAuthAttributes attributes) {
        return userRepository.findByEmail(attributes.getEmail())
                .orElseGet(() -> initialize(attributes));
    }

    private User initialize(final OAuthAttributes attributes) {
        return userRepository.save(attributes.toEntity());
    }
}
