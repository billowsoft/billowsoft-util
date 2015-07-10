package com.billowsoft.util.common;

import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtil {

	private static Properties emailProperties = new Properties();
	// keep one session for all requests
	private static Session session;
	static {
		emailProperties.setProperty("mail.smtp.host",
				System.getProperty("mail.smtp.host"));
		session = Session.getDefaultInstance(emailProperties);
	}

	/**
	 * Send email with specified from and to address. <b>Note</b>: the system
	 * property named 'mail.smtp.host' must be set before send email 
	 * 
	 * @param fromAddress
	 * @param toAddress
	 * @param subject
	 * @param emailBody
	 */
	public static void sendEmail(String fromAddress, List<String> toAddresses,
			String subject, String emailBody) {
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromAddress));
			for (String toAddress : toAddresses) {
				message.addRecipient(Message.RecipientType.TO,
						new InternetAddress(toAddress));
			}
			message.setSubject(subject);
			message.setText(emailBody);
			Transport.send(message, message.getAllRecipients());
		} catch (MessagingException e) {
			throw new RuntimeException("Send mail failed, " + e.getMessage());
		}
	}
}
