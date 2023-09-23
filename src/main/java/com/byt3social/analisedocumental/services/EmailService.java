package com.byt3social.analisedocumental.services;

import com.byt3social.analisedocumental.exceptions.EmailFailedException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Value("classpath:templates/email/documentacao.html")
    private Resource resource;

    public void notificaEmpresa(String nomeEmpresa, String link) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            message.setFrom(new InternetAddress("byt3social@gmail.com"));
            message.setRecipients(MimeMessage.RecipientType.TO, "byt3social@gmail.com");
            message.setSubject("B3 Social | Envie sua documentação");

            InputStream inputStream = resource.getInputStream();
            String htmlTemplate = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            htmlTemplate = htmlTemplate.replace("${nome_empresa}", nomeEmpresa);
            htmlTemplate = htmlTemplate.replace("${empresa_url}", link);

            message.setContent(htmlTemplate, "text/html; charset=utf-8");
        } catch (Exception e) {
            throw new EmailFailedException();
        }

        mailSender.send(message);
    }
}
