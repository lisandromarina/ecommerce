package com.ecommerce.utils.email;

import org.springframework.stereotype.Component;

public class VerificationAccountEmailMessage extends EmailMessage{
    private static final String DEFAULT_SUBJECT = "Por favor verifique su cuenta";
    private static final String BASE_CONTENT = "Querido [[name]],<br>"
            + "Por favor haga click en el enlace de abajo para verificar tu cuenta:<br>"
            + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFICAR</a></h3>"
            + "Muchas gracias!,<br>"
            + "Mercado Licha.";

    public VerificationAccountEmailMessage(String toAddress, String name, String verificationCode, String siteURl) {
        super(toAddress, DEFAULT_SUBJECT, null);
        String content = BASE_CONTENT.replace("[[name]]", name);
        String verifyURL = siteURl + "/user/verify?code=" + verificationCode;
        content = content.replace("[[URL]]", verifyURL);
        setContent(content);
    }
}
