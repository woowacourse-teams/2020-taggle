package kr.taggle.mail.scheduler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import kr.taggle.mail.job.WeeklyMailJobConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeeklyMailScheduler {

    private final JobLauncher jobLauncher;
    private final WeeklyMailJobConfig weeklyMailJobConfig;

    @Scheduled(cron = "${batch.cron-period}")
    public void runJob() {
        final Map<String, JobParameter> parameters = new HashMap<>();
        parameters.put("requestDate", new JobParameter(System.currentTimeMillis()));
        final JobParameters jobParameters = new JobParameters(parameters);
        try {
            jobLauncher.run(weeklyMailJobConfig.weeklyMailSendJob(), jobParameters);
        } catch (final JobInstanceAlreadyCompleteException | JobRestartException | JobParametersInvalidException | JobExecutionAlreadyRunningException e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }
    }
}
