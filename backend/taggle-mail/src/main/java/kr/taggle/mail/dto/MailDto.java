package kr.taggle.mail.dto;

import org.thymeleaf.context.Context;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MailDto {
    private String address;
    private String title;
    private Context context;
}
