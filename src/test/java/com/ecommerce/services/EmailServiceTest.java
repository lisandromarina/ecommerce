package com.ecommerce.services;

import com.ecommerce.exception.ApiRequestException;
import com.ecommerce.service.impl.EmailServiceImpl;
import com.ecommerce.utils.email.EmailMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {

    @Mock
    JavaMailSender mailSender;
    @InjectMocks
    private EmailServiceImpl emailService;

    @Test
    void testSendEmail_Successful() throws MessagingException, UnsupportedEncodingException {
        // Define test data
        EmailMessage emailMessage = new EmailMessage(
                "oneToAddress",
                "oneSubject",
                "oneContent"
        );
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        // Mock the mailSender.send() method to do nothing (successful case)
        doNothing().when(mailSender).send(mimeMessage);

        // Call the sendEmail method
        emailService.sendEmail(emailMessage);

        // Verify that JavaMailSender.createMimeMessage() was called
        verify(mailSender, times(1)).createMimeMessage();

        // Verify that JavaMailSender.send() was called with the MimeMessage
        verify(mailSender, times(1)).send(mimeMessage);
    }

    @Test
    void testSendEmail_MailException() throws MessagingException {
        // Define test data
        EmailMessage emailMessage = new EmailMessage(
                "oneToAddress",
                "oneSubject",
                "oneContent"
        );
        // Set necessary fields in the emailMessage, replace with appropriate data for testing

        // Mock the mailSender.createMimeMessage() method to return a MimeMessage
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        // Mock the mailSender.send() method to throw a MailSendException
        doThrow(new MailSendException("Failed to send email")).when(mailSender).send(mimeMessage);

        // Call the sendEmail method and expect an ApiRequestException to be thrown
        assertThrows(ApiRequestException.class, () -> emailService.sendEmail(emailMessage));

        // Verify that JavaMailSender.createMimeMessage() was called
        verify(mailSender, times(1)).createMimeMessage();
    }

    @Test
    void testSendEmail_MessagingException() throws MessagingException {
        // Define test data
        EmailMessage emailMessage = new EmailMessage(
                "oneToAddress",
                "oneSubject",
                "oneContent"
        );

        // Mock the mailSender.createMimeMessage() method to throw a MessagingException
        doAnswer(invocation -> {
            throw new MessagingException("Messaging error");
        }).when(mailSender).createMimeMessage();

        // Call the sendEmail method and expect an ApiRequestException to be thrown
        assertThrows(ApiRequestException.class, () -> emailService.sendEmail(emailMessage));

        // Verify that JavaMailSender.createMimeMessage() was called
        verify(mailSender, times(1)).createMimeMessage();
    }

}
