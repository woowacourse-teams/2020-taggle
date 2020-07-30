package com.woowacourse.taggle.security.config;

import static io.restassured.RestAssured.*;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

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

    // @formatter:off
    @DisplayName("로그인이 되어있지 않는다면 로그인페이지로 리다이렉트")
    @Test
    void notLoginReturnRedirect() {
        given()
        .when()
                .redirects().follow(false)
                .get("/tag")
        .then()
                .log().all()
                .statusCode(HttpStatus.FOUND.value())
                .header("Location", Matchers.containsString("/login"));
    }
}
