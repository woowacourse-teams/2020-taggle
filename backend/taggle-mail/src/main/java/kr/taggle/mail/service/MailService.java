package kr.taggle.mail.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import kr.taggle.bookmark.domain.Bookmark;
import kr.taggle.bookmark.domain.BookmarkRepository;
import kr.taggle.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MailService {
    private static final String WEEKLY_MAIL_TEMPLATE = "weekly_mail";

    private final BookmarkRepository bookmarkRepository;
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine springTemplateEngine;

    public MimeMessage createMail(final User user) throws MessagingException {
        final Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "id"));
        final Page<Bookmark> bookmarks = bookmarkRepository.findAllByUserId(user.getId(), pageable);

        return createMimeMessage(user, bookmarks);
    }

    private MimeMessage createMimeMessage(final User user, final Page<Bookmark> bookmarks) throws MessagingException {
        final MimeMessage message = mailSender.createMimeMessage();
        final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message);

        mimeMessageHelper.setTo(user.getNotificationEmail());
        mimeMessageHelper.setSubject("주간 사용 알림");

        final Context context = new Context();
        context.setVariable("bookmarks", bookmarks);
        mimeMessageHelper.setText(getTemplate(context), true);

        return message;
    }

    private String getTemplate(final Context context) {
        return springTemplateEngine.process(WEEKLY_MAIL_TEMPLATE, context);
    }
}
