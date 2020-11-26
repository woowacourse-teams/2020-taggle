package kr.taggle.mail.job;

import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.item.mail.javamail.MimeMessageItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import kr.taggle.mail.config.BatchConfig;
import kr.taggle.mail.processor.WeeklyMailBatchProcessor;
import kr.taggle.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class WeeklyMailJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final WeeklyMailBatchProcessor weeklyMailBatchProcessor;
    private final MimeMessageItemWriter mimeMessageItemWriter;
    private final BatchConfig batchConfig;

    @Bean
    public Job weeklyMailSendJob() {
        return jobBuilderFactory.get("weeklyMailSendJob")
                .start(weeklyMailSendStep())
                .build();
    }

    @Bean
    public Step weeklyMailSendStep() {
        return stepBuilderFactory.get("weeklyMailSendStep")
                .<User, MimeMessage>chunk(batchConfig.getChunk())
                .reader(userReader())
                .processor(weeklyMailBatchProcessor)
                .writer(mimeMessageItemWriter)
                .build();
    }

    @Bean
    public JpaPagingItemReader<User> userReader() {
        return new JpaPagingItemReaderBuilder<User>()
                .entityManagerFactory(entityManagerFactory)
                .pageSize(batchConfig.getChunk())
                .saveState(false)
                .queryString("SELECT u FROM User u WHERE u.notificationEnabled = true ORDER BY u.id")
                .build();
    }
}
