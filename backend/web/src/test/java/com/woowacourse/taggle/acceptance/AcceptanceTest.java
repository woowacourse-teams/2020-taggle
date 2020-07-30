package com.woowacourse.taggle.acceptance;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;

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

    @BeforeEach
    public void setUp() {
        webAppContextSetup(webApplicationContext);
        config = new RestAssuredMockMvcConfig()
                .encoderConfig(new EncoderConfig("UTF-8", "UTF-8"));
        RestAssured.port = port;
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
    public void post(final String path, final Map<String, String> params, final String expectLocation) {
        given()
                .body(params)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(path)
                .then()
                .log().all()
                .statusCode(HttpStatus.CREATED.value())
                .assertThat()
                .header("Location", containsString(expectLocation));
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
