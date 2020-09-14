package kr.taggle;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestPropertySource;

@Configuration
@ComponentScan(basePackages = {
        "kr.taggle"
})
@EntityScan(basePackages = {
        "kr.taggle"
})
@EnableJpaRepositories(basePackages = {
        "kr.taggle"
})
@TestPropertySource(locations = {
        "classpath:application-test.yml"
})
public class JpaTestConfiguration {
}
