package com.woowacourse.taggle.security.dto;

import java.util.Map;

import com.woowacourse.taggle.user.domain.Role;
import com.woowacourse.taggle.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String nickName;
    private String email;

    @Builder
    public OAuthAttributes(final Map<String, Object> attributes, final String nameAttributeKey, final String nickName,
            final String email) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.nickName = nickName;
        this.email = email;
    }

    public static OAuthAttributes of(final String userNameAttributeName,
            final Map<String, Object> attributes) {
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(final String userNameAttributeName, final Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nickName((String)attributes.get("name"))
                .email((String)attributes.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public User toEntity() {
        return User.builder()
                .nickName(nickName)
                .email(email)
                .role(Role.USER)
                .build();
    }
}
