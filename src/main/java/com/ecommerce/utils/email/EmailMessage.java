package com.ecommerce.utils.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

public class EmailMessage {
    private String toAddress;
    private String subject;
    private String content;

    public EmailMessage(String toAddress, String subject, String content) {
        this.toAddress = toAddress;
        this.subject = subject;
        this.content = content;
    }
    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
