package com.ecommerce.service.impl;

import com.ecommerce.exception.ApiRequestException;
import com.ecommerce.service.EmailService;
import com.ecommerce.utils.email.EmailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;

@Service
@Transactional
public class EmailServiceImpl implements EmailService {
    private static final String FROM_ADDRES = "lisandromarina1@gmail.com";
    private static final String SENDER_NAME = "Mercado Licha";
    @Autowired
    private JavaMailSender mailSender;
    @Override
    public void sendEmail(EmailMessage emailMessage) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom(FROM_ADDRES, SENDER_NAME);
            helper.setTo(emailMessage.getToAddress());
            helper.setSubject(emailMessage.getSubject());
            helper.setText(emailMessage.getContent(), true);

            mailSender.send(message);
        } catch (MailException e) {
            throw new ApiRequestException("Failed to send email: " + e.getMessage(), e);
        } catch (MessagingException e) {
            throw new ApiRequestException(e.getMessage(), e);
        } catch (UnsupportedEncodingException e) {
            throw new ApiRequestException(e.getMessage(), e);
        }
    }
}
