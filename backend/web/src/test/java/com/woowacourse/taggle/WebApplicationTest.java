package com.woowacourse.taggle;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.config.location="
        + "classpath:/config/oauth.properties, "
        + "classpath:/application.properties")
class WebApplicationTest {

    @Test
    void contextLoads() {

    }
}
