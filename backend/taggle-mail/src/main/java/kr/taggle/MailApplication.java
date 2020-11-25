package kr.taggle;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class MailApplication {
    public static void main(final String[] args) {
        SpringApplication.run(MailApplication.class, args);
    }
}
