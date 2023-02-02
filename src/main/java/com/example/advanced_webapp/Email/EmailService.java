package com.example.advanced_webapp.Email;

import com.sendgrid.*;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class EmailService {
    private String SENDGRID_API_KEY="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";

    public void send(String email, String link) throws IOException {
        Email from = new Email("niu.jins@northeastern.edu");
        String subject = "Sending with SendGrid is Fun";
        Email to = new Email(email);
        Content content = new Content("text/plain", "Please verify your account "+link);
        Mail mail = new Mail(from, subject, to, content);
        SendGrid sg = new SendGrid(SENDGRID_API_KEY);
        System.out.println(SENDGRID_API_KEY);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (
                IOException ex) {
            throw ex;
        }
    }
}
