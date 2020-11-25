package kr.taggle.mail.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import kr.taggle.mail.dto.MailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MailService {
    private static final String MAIL_TEMPLATE = "mail-template";

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine springTemplateEngine;

    public void mailSend(final MailDto mailDto) {
        final MimeMessage message = mailSender.createMimeMessage();
        final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message);

        try {
            mimeMessageHelper.setTo(mailDto.getAddress());
            mimeMessageHelper.setSubject(mailDto.getTitle());
            mimeMessageHelper.setText(getTemplate(mailDto.getContext()), true);

            mailSender.send(message);
        } catch (final MessagingException e) {
            log.error("", e);
        }
    }

    private String getTemplate(final Context context) {
        return springTemplateEngine.process(MAIL_TEMPLATE, context);
    }
}
