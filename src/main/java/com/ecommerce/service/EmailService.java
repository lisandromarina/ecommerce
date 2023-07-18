package com.ecommerce.service;

import com.ecommerce.utils.email.EmailMessage;

public interface EmailService {

    void sendEmail(EmailMessage emailMessage);
}
