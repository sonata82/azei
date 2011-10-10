package de.remk0.azei.core;

import java.io.IOException;
import java.io.InputStream;

import java.util.Date;
import java.util.Vector;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Message.RecipientType;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import de.remk0.azei.AzeiPlugin;
import de.remk0.azei.azcommand.IAzCommand;
import de.remk0.azei.config.Config;
import de.remk0.azei.sas.SimpleAzureusScriptParser;

/**
 * Parses emails into tasks and vice versa.
 * 
 * @author Remko Plantenga
 */
public class EmailParser {
	private static Vector<Message> messages = null;
	private static Vector<Task> tasks = null;
	
	public static Vector<Message> getMessages() {
		return messages;
	}

	public static Vector<Task> getTasks() {
		return tasks;
	}
	
	/**
	 * Parses emails to tasks
	 * 
	 * @param messages the messages to parse
	 */
	public static void parseFrom(Vector<Message> messages) {
		AzeiPlugin.logger.entering("EmailParser", "parseFrom");
		
		tasks = new Vector<Task>();
		
		for (Message m : messages) {
			InternetAddress client;
			
			try {
				// first try reply to address
				client = (InternetAddress)m.getReplyTo()[0];
			} catch (MessagingException e) {
				// fail, lets try first from
				try {
					client = (InternetAddress) m.getFrom()[0];
				} catch (MessagingException e1) {
					// complete fail, skip this mail
					AzeiPlugin.logger.severe("Could not get recipient, skipping mail");
					continue;
				}				
			}
			
			String content = "";
			try {
				Object o = m.getContent();
				if (o instanceof String) {
					content = o.toString();
				} else if (o instanceof Multipart) {
					MimeMultipart mmp = (MimeMultipart)o;
					for (int i=0, l=mmp.getCount(); i<l; i++) {
						Object oMmp = mmp.getBodyPart(i).getContent();
						if (oMmp instanceof String) {
							content = oMmp.toString();
							break;	//TODO:we should look for more possibilities
						}
					}
				} else if (o instanceof InputStream) {
					AzeiPlugin.logger.severe("InputStream not supported yet");
				}
			} catch (IOException e) {
				AzeiPlugin.logger.severe("IOException on retrieving content of email");
				content = "";
			} catch (MessagingException e) {
				AzeiPlugin.logger.finest("MessageException on retrieving content of email");
				content = "";
			}
			
			String subject = Config.getStringParameter(Config.EMAIL_REPLY_SUBJECT);
			try {
				subject = "RE: " + m.getSubject();
			} catch (MessagingException e) {
				AzeiPlugin.logger.warning("Could not get subject, using default");
			}
			
			
			if (content == "") {
				AzeiPlugin.logger.severe("Email is empty or could not be read, skipping email");
				continue;
			}
			
			content = content.toUpperCase();
			Vector<IAzCommand> commands = SimpleAzureusScriptParser.getInstance().parse(content);
			for (IAzCommand c : commands) {
				Task t = new Task(client, c, client, subject);
				
				tasks.add(t);
			}
		}
	}
	
	/**
	 * Parse tasks to emails
	 * 
	 * @param session an open session to an email server
	 * @param tasks the tasks to parse
	 * @return
	 */
	public static boolean parseTo(Session session, Task[] tasks) {
		AzeiPlugin.logger.entering("EmailParser", "parseTo");
		
		messages = new Vector<Message>();
		
		String from = Config.getStringParameter(Config.EMAIL_FROM);
		
		for (Task t: tasks) {
			Message m = new MimeMessage(session);
			if (t.getResult() != null) {
				try {
					m.setFrom(new InternetAddress(from));
					m.setRecipient(RecipientType.TO, t.getRecipient());
					m.setSubject(t.getSubject());
					m.setText(t.getResult());
					m.setHeader("X-Mailer", AzeiPlugin.PRODUCTNAME + "/" + AzeiPlugin.VERSION);
					m.setSentDate(new Date());
				} catch (AddressException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				messages.add(m);
			} else {
				AzeiPlugin.logger.warning("Ignoring task without result");
			}
		}
		
		return true;
	}

 }
