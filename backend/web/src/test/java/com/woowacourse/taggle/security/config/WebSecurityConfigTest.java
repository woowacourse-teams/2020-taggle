package com.woowacourse.taggle.security.config;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import io.restassured.RestAssured;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "spring.config.location=classpath:/config/oauth.properties"
)
class WebSecurityConfigTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    // @TODO 인증 해제로 인한 임시 해제 조치
    // @formatter:off
    // @DisplayName("로그인이 되어있지 않다면 로그인 페이지로 리다이렉트한다.")
    // @Test
    // void notLoginReturnRedirect() {
    //     given()
    //     .when()
    //             .redirects().follow(false)
    //             .get("/api/v1/tags")
    //     .then()
    //             .log().all()
    //             .statusCode(HttpStatus.FOUND.value())
    //             .header("Location", Matchers.containsString("/login"));
    // }
}
