package com.woowacourse.taggle;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

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
public class JpaTestConfiguration {
}
