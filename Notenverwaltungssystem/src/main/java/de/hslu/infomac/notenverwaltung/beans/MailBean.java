package de.hslu.infomac.notenverwaltung.beans;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang.StringUtils;

import de.hslu.infomac.notenverwaltung.enumerations.Anrede;
import de.hslu.infomac.notenverwaltung.helper.JsfUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @author Igor Eslengert
 */
@ManagedBean(name = "mailBean")
@SessionScoped
public class MailBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/////////////////////////////////////
	////////// Bean Attribute //////////
	///////////////////////////////////

	private String betreff;
	private String inhalt;
	private String senderEmail;
	private String senderName;

	//////////////////////////////////////
	//// Getter/Setter der Attribute ////
	////////////////////////////////////

	public String getBetreff() {
		return betreff;
	}

	public void setBetreff(String betreff) {
		this.betreff = betreff;
	}

	public String getInhalt() {
		return inhalt;
	}

	public void setInhalt(String inhalt) {
		this.inhalt = inhalt;
	}

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	///////////////////////////////////
	//////// Weitere Methoden ////////
	/////////////////////////////////

	public void sendKontaktMail() {
		List<String> empfaenger = new ArrayList<String>();
		empfaenger.add("notenverwaltungssystem@gmail.com");
		sendMail(betreff, inhalt, empfaenger, null, null, "Notenverwaltungssystem", senderEmail, senderName);
		inhalt = null;
		senderEmail = null;
		senderName = null;
		betreff = null;
		JsfUtil.addSuccessMessage("kontakt:send", "Ihre Nachricht wurde erfolgreich gesendet.");
	}

	public void sendMail(String betreff, String inhalt, List<String> empfaengerEmails, List<String> ccEmpfaengerEmails,
			Anrede anrede, String empfaengerName, String senderEmail, String senderName) {

		if (empfaengerEmails != null && !empfaengerEmails.isEmpty()) {

			final String username = "notenverwaltungssystem@gmail.com";
			final String password = "BtAm]3K5}.mVDw";

			Properties props = new Properties();
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");

			Session mailsession = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});

			MimeMessage message = new MimeMessage(mailsession);
			try {

				message.setSubject(betreff, "UTF-8");

				BodyPart body = new MimeBodyPart();
				Configuration cfg = new Configuration();
				Template template;

				String gruss = "Sehr geehrte Damen und Herren";
				if (anrede != null && anrede.equals(Anrede.Herr)) {
					gruss = "Sehr geehrter Herr ";
				} else if (anrede != null && anrede.equals(Anrede.Frau)) {
					gruss = "Sehr geehrte Frau ";
				}
				try {
					cfg.setClassForTemplateLoading(this.getClass(), "/");
					template = cfg.getTemplate("/mail/html-mail-template.ftl");
					Map<String, String> rootMap = new HashMap<String, String>();
					rootMap.put("betreff", betreff);
					rootMap.put("gruss", gruss);
					if (empfaengerName != null && !empfaengerName.isEmpty()) {
						rootMap.put("empfaengerName", empfaengerName);
					} else {
						rootMap.put("empfaengerName", "");
					}
					rootMap.put("inhalt", inhalt);
					if (senderEmail != null && !senderEmail.isEmpty()) {
						rootMap.put("sender", senderName + " (" + senderEmail + ")");
					} else {
						rootMap.put("sender", senderName);
					}

					Writer out = new StringWriter();

					try {
						template.process(rootMap, out);
						body.setContent(out.toString(), "text/html");

						Multipart multipart = new MimeMultipart();
						multipart.addBodyPart(body);

						message.setContent(multipart);
						for (String empfaengerEmail : empfaengerEmails) {
							message.addRecipient(Message.RecipientType.TO, new InternetAddress(empfaengerEmail));
						}
						if (ccEmpfaengerEmails != null) {
							for (String ccEmpfaengerEmail : ccEmpfaengerEmails) {
								message.addRecipient(Message.RecipientType.CC, new InternetAddress(ccEmpfaengerEmail));
							}
						}
						Transport.send(message, message.getAllRecipients());

					} catch (TemplateException e) {
						Logger.getLogger("Template Exception.");
					}

				} catch (IOException e) {
					Logger.getLogger("IO Exception.");
				}

			} catch (AddressException e) {
				Logger.getLogger("Address Exception.");
			} catch (MessagingException e) {
				Logger.getLogger("Messaging Exception.");
			}
		}
	}

	public String empaengerFormatieren(final String titel, final String vorname, final String nachname) {
		String empfaenger = "";
		if (!StringUtils.isEmpty(titel)) {
			empfaenger += titel;
		}
		if (!StringUtils.isEmpty(vorname)) {
			empfaenger += " " + vorname;
		}
		if (!StringUtils.isEmpty(nachname)) {
			empfaenger += " " + nachname;
		}
		return empfaenger.trim();
	}
}
