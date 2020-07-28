package com.woowacourse.taggle;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestPropertySource;

@Configuration
@ComponentScan(basePackages = {
        "com.woowacourse.taggle"
})
@EntityScan(basePackages = {
        "com.woowacourse.taggle"
})
@EnableJpaRepositories(basePackages = {
        "com.woowacourse.taggle"
})
@TestPropertySource(locations = {
        "classpath:application-test.properties"
})
public class JpaTestConfiguration {
}
