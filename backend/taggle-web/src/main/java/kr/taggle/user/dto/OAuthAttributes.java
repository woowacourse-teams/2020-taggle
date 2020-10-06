package kr.taggle.user.dto;

import java.util.Map;
import java.util.Objects;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.URL;

import kr.taggle.user.domain.Role;
import kr.taggle.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttributes {

    private static final String KAKAO_REGISTRATION_ID = "kakao";
    private static final String KAKAO_USER_NAME_ATTRIBUTE_NAME = "id";

    @NotNull
    private final Map<String, Object> attributes;

    @NotEmpty
    private final String nameAttributeKey;

    @NotEmpty
    private final String nickName;

    @Email
    private final String email;

    @URL
    private final String picture;

    @Builder
    public OAuthAttributes(final Map<String, Object> attributes, final String nameAttributeKey, final String nickName,
            final String email, final String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.nickName = nickName;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(final String registrationId, final String userNameAttributeName,
            final Map<String, Object> attributes) {
        if (KAKAO_REGISTRATION_ID.equals(registrationId)) {
            return ofKakao(KAKAO_USER_NAME_ATTRIBUTE_NAME, attributes);
        }

        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(final String userNameAttributeName, final Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nickName((String)attributes.get("name"))
                .email((String)attributes.get("email"))
                .picture((String)attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofKakao(final String userNameAttributeName, final Map<String, Object> attributes) {
        final Map<String, Object> response = (Map<String, Object>)attributes.get("kakao_account");
        final Map<String, Object> profile = (Map<String, Object>)response.get("profile");
        return OAuthAttributes.builder()
                .nickName((String)profile.get("nickname"))
                .email((String)response.get("email"))
                .picture(getPicture((String)profile.get("profile_image_url")))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static String getPicture(final String profileImageUrl) {
        if (Objects.isNull(profileImageUrl)) {
            return "https://cdn.pixabay.com/photo/2016/08/08/09/17/avatar-1577909_1280.png";
        }

        return profileImageUrl;
    }

    public User toEntity() {
        return User.builder()
                .nickName(nickName)
                .email(email)
                .notificationEmail(email)
                .picture(picture)
                .notificationEnabled(false)
                .role(Role.USER)
                .build();
    }
}
