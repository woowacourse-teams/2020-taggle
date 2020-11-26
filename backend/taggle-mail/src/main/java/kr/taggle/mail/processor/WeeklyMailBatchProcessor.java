package kr.taggle.mail.processor;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import kr.taggle.mail.service.MailService;
import kr.taggle.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Component
public class WeeklyMailBatchProcessor implements ItemProcessor<User, MimeMessage> {
    private final MailService mailService;

    @Override
    public MimeMessage process(final User user) throws MessagingException {
        log.info("{}의 이메일 메시지 생성", user.getNotificationEmail());
        return mailService.createMail(user);
    }
}
