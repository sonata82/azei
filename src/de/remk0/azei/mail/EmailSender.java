package de.remk0.azei.mail;

import java.util.Vector;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;

import de.remk0.azei.AzeiPlugin;

/**
 * Sends emails
 * 
 * @author Remko Plantenga
 *
 */
public class EmailSender {
	
	public static boolean send(Transport transport, Vector<Message> messages) {
		for (Message m: messages) {
			try {
				transport.sendMessage(m, m.getAllRecipients());
			} catch (MessagingException e) {
				try {
					AzeiPlugin.logger.severe("Error while trying to send mail to " 
							+ m.getRecipients(RecipientType.TO)[0].toString() + 
							", error: " + e.getMessage());
				} catch (MessagingException e1) {
					AzeiPlugin.logger.severe("Error while trying to send mail. " 
							+ "An additional error was thrown while trying to retrieve the recipients" + 
							", error: " + e.getMessage());
				}
			}
		}
		return true;
	}
}
