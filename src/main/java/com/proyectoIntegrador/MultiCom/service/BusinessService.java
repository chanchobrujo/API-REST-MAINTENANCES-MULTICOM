package com.proyectoIntegrador.MultiCom.service;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*; 

public class BusinessService {
	
	public static String sendMail(String email, String asunto, String mensaje) {
        try {
        	Properties propiedad = new Properties();
            propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
            propiedad.setProperty("mail.smtp.starttls.enable", "true");
            propiedad.setProperty("mail.smtp.port", "587");
            propiedad.setProperty("mail.smtp.auth", "true");
            Session session = Session.getDefaultInstance(propiedad);
            String emailSender = "cuentaempresarial526@gmail.com";
            String password = "drcsfkzfhgjxwtjm";
            MimeMessage mail = new MimeMessage(session);
            mail.setFrom(new InternetAddress (emailSender));
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            mail.setSubject(asunto);
            mail.setText(mensaje);
            MimeMultipart mp = new MimeMultipart();
            BodyPart html = new MimeBodyPart();
            html.setContent(""
                    + "<br><h1>MULTICOM</h1><br>"
                    + "<h2>"+mensaje+"</h2><br>" 
                    , "text/html");
            mp.addBodyPart(html);
            mail.setContent(mp);
            Transport transporte = session.getTransport("smtp");
            transporte.connect(emailSender, password);
            transporte.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));
            transporte.close();
            return "Correo enviado";
		} catch (Exception e) {
            return e.getMessage();
		}
    }
}
