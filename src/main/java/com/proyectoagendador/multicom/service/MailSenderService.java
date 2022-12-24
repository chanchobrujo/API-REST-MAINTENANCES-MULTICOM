package com.proyectoagendador.multicom.service;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import com.proyectoagendador.multicom.constants.properties.SenderMailProperties;

import static javax.mail.Session.getDefaultInstance;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailSenderService {

    private final SenderMailProperties properties;

    private Properties setProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", this.properties.getHost());
        properties.setProperty("mail.smtp.starttls.enable", this.properties.getStarttls());
        properties.setProperty("mail.smtp.port", this.properties.getPort());
        properties.setProperty("mail.smtp.auth", this.properties.getAuth());
        return properties;
    }

    private Multipart setContent (String message) throws MessagingException {
        BodyPart html = new MimeBodyPart();
        MimeMultipart mp = new MimeMultipart();
        Object content = "<br><h1>MULTICOM</h1><br>" + "<h2>"+message+"</h2><br>";
        html.setContent(content, "text/html");
        mp.addBodyPart(html);
        return mp;
    }

    private void transport(Session session, MimeMessage mail) throws MessagingException {
        Transport transport = session.getTransport("smtp");
        transport.connect(this.properties.getEmail(), properties.getPassword());
        transport.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));
        transport.close();
    }
	
	public void sendMail(String email, String asunto, String mensaje) {
        try {
            Session session = getDefaultInstance(setProperties());
            MimeMessage mail = new MimeMessage(session);
            mail.setFrom(new InternetAddress(this.properties.getEmail()));
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            mail.setSubject(asunto);
            mail.setText(mensaje);
            mail.setContent(setContent(mensaje));
            this.transport(session, mail);
        } catch (MessagingException exception) {
            log.error(exception.getMessage());
        }
    }
}
