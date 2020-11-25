package kr.taggle.mail.config;

import org.springframework.batch.item.mail.javamail.MimeMessageItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class MailConfig {
    private final JavaMailSender javaMailSender;

    @Bean
    public MimeMessageItemWriter mimeMessageItemWriter() {
        final MimeMessageItemWriter mimeMessageItemWriter = new MimeMessageItemWriter();
        mimeMessageItemWriter.setJavaMailSender(javaMailSender);
        return mimeMessageItemWriter;
    }
}
