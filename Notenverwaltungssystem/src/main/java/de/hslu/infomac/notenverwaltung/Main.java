package de.hslu.infomac.notenverwaltung;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import de.hslu.infomac.notenverwaltung.beans.MailBean;
import de.hslu.infomac.notenverwaltung.enumerations.Anrede;
import de.hslu.infomac.notenverwaltung.exceptions.CouldNotHashPasswordException;

public class Main {

	public static void main(String[] args) throws ParseException, CouldNotHashPasswordException {
		final String username = "mic311.notenverwaltungssystem@gmail.com";
		final String password = "BtAm]3K5}.mVDw";

		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("nv@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse("igoreslengert@web.de"));
			message.setSubject("test");
			message.setText("test" );

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

}
