package com.example.advanced_webapp.Email;

import com.sendgrid.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class EmailService {
    @Value("${spring.sendgrid.api.key}")
    private String SENDGRID_API_KEY;
    public void send(String email, String link) throws IOException {
        Email from = new Email("niu.jins@northeastern.edu");
        String subject = "Email to verify your account";
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
