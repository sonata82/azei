/**
 * 
 */
package de.remk0.azei;

import java.util.Collection;
import java.util.Properties;
import java.util.Vector;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;

import de.remk0.azei.config.Config;
import de.remk0.azei.core.EmailParser;
import de.remk0.azei.core.Task;
import de.remk0.azei.mail.EmailFetcher;
import de.remk0.azei.mail.EmailSender;

/**
 * This is a POP3 mail interface
 * 
 * @author Remko Plantenga
 *
 */
public class POP3MailQueueInterface extends MailQueueInterface {
	private EmailFetcher ef = null;	

	public POP3MailQueueInterface(String aHost, String aPort, 
			String aUsername, String aPassword, String aFolderName) {
		super(aHost, aPort, "pop3", aUsername, aPassword, aFolderName);
	}

	/**
	 * Prepares the connection to a server with the current connection parameters
	 * 
	 * @return true if successfully prepared, otherwise false
	 */
	@Override
	protected boolean prepareConnection() {
		AzeiPlugin.logger.entering("POP3MailQueueInterface", "prepareConnection");

		setPrepared(false);
		
		Properties props = new Properties();
		try {
			props.put("mail.pop3.host", getHost());
			props.put("mail.pop3.user", getUsername());
			props.put("mail.pop3.port", getPort());
			if (Config.getBooleanParameter(Config.EMAIL_SMTP_AUTH)) {
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.user", getUsername());
			}
		} catch (NullPointerException e) {
			AzeiPlugin.logger.severe("No hostname or username given");
			return false;
		}
		
		setSession(Session.getInstance(props));
		getSession().setDebug(Config.getBooleanParameter(Config.EMAIL_DEBUG_MODE));
		
		setPrepared(true);
		
		return true;
	}

	/* (non-Javadoc)
	 * @see de.remk0.azei.IQueueReader#read()
	 */
	@Override
	public Collection<? extends Task> read() {
		Collection<Task> readTasks = null;
		
		// open mbox
		if (fetch()) {
			// asynchron!
			synchronized (this) {
				boolean interrupted = false;
				while(ef.getState() == EmailFetcher.PENDING && !interrupted) {
					AzeiPlugin.logger.finest("Waiting for EmailFetcher...");
					try {
						// wait for EmailFetcher to notify us
						wait();
					} catch (InterruptedException e) {
						AzeiPlugin.logger.finest("Interrupted on waiting for EmailFetcher");
						interrupted = true;
					}
					AzeiPlugin.logger.finest("Finished waiting for EmailFetcher");
				}
			}
			AzeiPlugin.logger.fine("Looking for fetched emails");
			// fetch emails
			Vector<Message> m = ef.getFetched();
			if (m.size() > 0) {
				AzeiPlugin.logger.fine("Sending fetched emails to parser");
				// parse emails to tasks
				EmailParser.parseFrom(m);
				
				if (EmailParser.getTasks().size() > 0) {
					readTasks = EmailParser.getTasks();
				}
			} else {
				AzeiPlugin.logger.fine("No emails to parse");
			}
		}			
	return readTasks;
	}
	
	/**
	 * Initializes an instance of EmailFetcher and hooks it up to the folder 
	 * as a ConnectionListener. 
	 * 
	 * @return true on succes, otherwise false
	 */
	private boolean fetch() {
		AzeiPlugin.logger.entering("POP3MailQueueInterface", "fetch");
		
		try {
			if (((getFolder().getType() & Folder.HOLDS_MESSAGES) != 0)) {
				// sadly, we have to open the folder to know if it is
				// actually holding any messages
				AzeiPlugin.logger.fine("Folder " + getFolderName() + " holds messages");
				
				ef = new EmailFetcher(this, getFolder());
				getFolder().addConnectionListener(ef);
				getFolder().open(Folder.READ_WRITE);
				return true;
			}

			AzeiPlugin.logger.fine("Folder " + getFolderName() + "does not hold messages");
		} catch (MessagingException e1) {
			AzeiPlugin.logger.severe("Error reading folder " + getFolderName());
		}
		return false;
	}

	@Override
	protected void interval() throws InterruptedException {
		getStore().removeConnectionListener(ef);
		ef = null;
		
		super.interval();		
	}

	@Override
	public void write(Task task) {
		if (task != null) {
			AzeiPlugin.logger.fine("Parsing task to mail");
			
			// parse tasks to mails
			EmailParser.parseTo(getSession(), new Task[]{ task });
			
			if (EmailParser.getMessages().size() > 0) {
				Transport tr = null;
				try {
					tr = getSession().getTransport("smtp");
				} catch (NoSuchProviderException e) {
					AzeiPlugin.logger.severe("No SMTP available, can't send mail");
				}
				
				if (tr != null) {
					try {
						tr.connect(getHost(), getUsername(), getPassword());
					} catch (MessagingException e) {
						AzeiPlugin.logger.severe("Connect failed: " + e.getMessage());
					}
					
					AzeiPlugin.logger.fine("Sending mail");
					// send mails
					EmailSender.send(tr, EmailParser.getMessages());
					
					// close connection
					try {
						tr.close();
					} catch (MessagingException e) {
						AzeiPlugin.logger.severe("Failed to close connection to SMTP server");
					}
					
					AzeiPlugin.logger.fine("Done");
				}
			} else {
				AzeiPlugin.logger.finer("No email to send");
			}
		} else {
			AzeiPlugin.logger.finest("No task to parse");
		}
	}
}
