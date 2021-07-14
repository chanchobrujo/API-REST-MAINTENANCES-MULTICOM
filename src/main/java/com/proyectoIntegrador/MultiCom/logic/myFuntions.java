package com.proyectoIntegrador.MultiCom.logic;

import java.math.*;
import java.security.*; 
import java.text.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*; 

public class myFuntions {
	public static String sendMail(String correro,String Asunto,String mensajee) throws Exception{
        try {
        	Properties propiedad = new Properties();
            propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
            propiedad.setProperty("mail.smtp.starttls.enable", "true");
            propiedad.setProperty("mail.smtp.port", "587");
            propiedad.setProperty("mail.smtp.auth", "true");
            Session sesion = Session.getDefaultInstance(propiedad);
            String correoEnvia = "cuentaempresarial526@gmail.com";
            String contrasena = "drcsfkzfhgjxwtjm";
            String destinatario = correro;
            String asunto = Asunto;
            String mensaje = mensajee;
            MimeMessage mail = new MimeMessage(sesion);
            mail.setFrom(new InternetAddress (correoEnvia));
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
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
            Transport transporte = sesion.getTransport("smtp");
            transporte.connect(correoEnvia,contrasena);
            transporte.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));
            transporte.close();
            return "Correo enviado";
		} catch (Exception e) {
            return e.getMessage();
		}
    }

    public static int compareTime(String hora1, String hora2) {
        try {
            DateFormat dateFormat = new SimpleDateFormat ("hh:mm");

            Date comparar1 = dateFormat.parse( hora1 );
            Date comparar2 = dateFormat.parse( hora2 );
            
            return comparar1.compareTo(comparar2);
        } catch (Exception e) {
            return 0;
        }
    }

    public static String generatedID() { 
        return (String) UUID.randomUUID().toString().toUpperCase().subSequence(0,8);
    }

    public static String encryptInSHA1(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
