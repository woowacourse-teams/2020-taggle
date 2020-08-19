package com.woowacourse.taggle.acceptance;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.reset;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;

import com.woowacourse.taggle.authentication.UserArgumentResolver;
import com.woowacourse.taggle.user.domain.Role;
import com.woowacourse.taggle.user.domain.User;
import com.woowacourse.taggle.user.domain.UserRepository;
import com.woowacourse.taggle.user.dto.SessionUser;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.module.mockmvc.config.RestAssuredMockMvcConfig;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AcceptanceTest {

    @LocalServerPort
    int port;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private UserArgumentResolver userArgumentResolver;

    @BeforeEach
    public void setUp() throws Exception {
        webAppContextSetup(webApplicationContext);
        config = new RestAssuredMockMvcConfig()
                .encoderConfig(new EncoderConfig("UTF-8", "UTF-8"));
        RestAssured.port = port;
        final User user = User.builder()
                .id(1L)
                .nickName("tigger")
                .email("tigger@aroundthirty.com")
                .phoneNumber("010-1234-5678")
                .picture("https://www.github.com")
                .role(Role.ADMIN)
                .build();
        final User saved = userRepository.save(user);
        final SessionUser sessionUser = new SessionUser(saved);
        when(userArgumentResolver.supportsParameter(any())).thenReturn(true);
        when(userArgumentResolver.resolveArgument(any(), any(), any(), any())).thenReturn(sessionUser);
    }

    // @formatter:off
    public <T> T get(final String path, final Class<T> responseType) {
        return
                given()
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .when()
                        .get(path)
                        .then()
                        .log().all()
                        .statusCode(HttpStatus.OK.value())
                        .extract().as(responseType);
    }
    // @formatter:on

    // @formatter:off
    public <T> List<T> getAsList(final String path, final Class<T> responseType) {
        return
                given()
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .when()
                        .get(path)
                        .then()
                        .log().all()
                        .statusCode(HttpStatus.OK.value())
                        .extract()
                        .jsonPath().getList(".", responseType);
    }
    // @formatter:on

    // @formatter:off
    public <T> T post(final String path, final Map<String, Object> params, final Class<T> responseType, final String expectLocation) {
        return given()
                .body(params)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(path)
                .then()
                .log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", containsString(expectLocation))
                .extract().as(responseType);
    }
    // @formatter:on

    // @formatter:off
    public void post(final String path, final Map<String, Object> params, final String expectLocation) {
        given()
                .body(params)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(path)
                .then()
                .log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", expectLocation);
    }
    // @formatter:on

    // @formatter:off
    public void put(final String path, final Map<String, Object> params) {
        given()
                .body(params)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .put(path)
                .then()
                .log().all()
                .statusCode(HttpStatus.OK.value())
                .assertThat();
    }
    // @formatter:on

    // @formatter:off
    public void delete(final String path) {
        given()
                .when()
                .delete(path)
                .then()
                .log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
    // @formatter:on

    @AfterEach
    public void tearDown() {
        reset();
    }
}

