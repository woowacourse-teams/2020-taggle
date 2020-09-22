package kr.taggle.security.config;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import io.restassured.RestAssured;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "spring.config.location=classpath:/config/application-oauth.yml"
)
class WebSecurityConfigTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    // @formatter:off
    // @DisplayName("로그인이 되어있지 않는다면 로그인페이지로 리다이렉트")
    // @Test
    // void notLoginReturnRedirect() {
    //     given()
    //     .when()
    //             .redirects().follow(false)
    //             .get("/tag")
    //     .then()
    //             .log().all()
    //             .statusCode(HttpStatus.FOUND.value())
    //             .header("Location", Matchers.containsString("/login"));
    // }
}
