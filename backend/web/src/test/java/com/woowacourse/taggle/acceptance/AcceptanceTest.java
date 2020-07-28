package com.woowacourse.taggle.acceptance;

import static io.restassured.RestAssured.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AcceptanceTest {

    @LocalServerPort
    int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    // @formatter:off
    public <T> T post(final String path, final Map<String, String> params, final Class<T> responseType) {
        return
                given()
                        .body(params)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                        .post(path)
                .then()
                        .log().all()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract().as(responseType);
    }

    public <T> List<T> getAsList(final String path, final Class<T> responseType) {
        return
                given()
                        .accept(MediaType.TEXT_HTML_VALUE)
                .when()
                        .get(path)
                .then()
                        .log().all()
                        .statusCode(HttpStatus.OK.value())
                        .extract()
                        .jsonPath().getList(".", responseType);
    }

    public void delete(final String path) {
        given()
        .when()
                .delete(path)
        .then()
                .log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
    // @formatter:on
}
