package com.proyectoIntegrador.MultiCom.logic;

import java.math.*;
import java.security.*; 
import java.text.*; 
import java.time.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*; 

public class myFuntions {
	public static String viewTime(Date date) {
		int hour = date.getHours(), minutes = date.getMinutes();
		String hourVar = hour+"", minutesVar = minutes+""; 

		if(hour >= 0 && hour <=9) hourVar = "0"+hour; 
		if(minutes >= 0 && minutes <=9) minutesVar = "0"+minutes;
		
		return hourVar+":"+minutesVar;
	}
		
	public static void expirationReservation(String fecha, String hora) { 
		Timer timer = new Timer();
		TimerTask tt = new TimerTask() {
			@Override  
		    public void run() {  
		        System.out.println("Task is on");  
		    };  
		};
		timer.schedule(tt, new Date());   
	}
	
	public static boolean verifyCross(String fecha, String horaInicio, String horaFin, String horaInicioA, String horaFinB ) { 
		try {
			SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd"); 
			Date baseDate = formato.parse(fecha);
			String[] vector = myFuntions.extracHourMinutes(horaInicio);
			
			baseDate.setHours( Integer.parseInt(vector[0]) );
			baseDate.setMinutes( Integer.parseInt(vector[1]) );
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(baseDate);  
			
			String xv = myFuntions.viewTime(baseDate);
			do {
				if (xv.equals(horaInicioA) || xv.equals(horaFinB)) return true;
				
				calendar.add(Calendar.MINUTE, 1);
				Date fechaSalida = calendar.getTime();
				
				xv = myFuntions.viewTime(fechaSalida); 
				
			} while ( !xv.equals(horaFin) ); 

			return false;
		} catch (Exception e) {
			
			return true;
		}
	}
	
	public static String[] extracHourMinutes(String hour) { 
		return hour.split(":");  
	}
	
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
