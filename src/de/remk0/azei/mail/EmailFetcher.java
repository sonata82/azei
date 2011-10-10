package de.remk0.azei.mail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.event.ConnectionEvent;
import javax.mail.event.ConnectionListener;
import javax.mail.search.SubjectTerm;

import de.remk0.azei.AzeiPlugin;
import de.remk0.azei.config.Config;

/**
 * Reads a mailbox and fetches emails according to 
 * predefined requirements
 * 
 * @author Remko Plantenga
 *
 */
public class EmailFetcher implements ConnectionListener {
	public static final int PENDING = 0;
	public static final int FETCHED = 2;
	
	private Folder folder = null;	
	private Vector<Message> fetched = new Vector<Message>();
	private int state = PENDING;
	
	private Runnable threadWaiting = null;
	
	public EmailFetcher(Runnable aThreadWaiting, Folder aFolder) {
		AzeiPlugin.logger.entering("EmailFetcher", "constructor");
		
		threadWaiting = aThreadWaiting;
		folder = aFolder;
	}
	
	public void receive(Message message) {
		AzeiPlugin.logger.entering("EmailFetcher", "receive");
		
		String messageId;
		try {
			messageId = "'" + message.getSubject() + "'";
		} catch (MessagingException e1) {
			messageId = "";
		}
		
		try {
			if (!message.getContentType().startsWith("text/plain") &&
				!message.getContentType().startsWith("multipart")) {
				AzeiPlugin.logger.severe("Sorry, email " + messageId + " not in plain text format");
				return;
			}
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		Object o = null;
		
		try {
			o = message.getContent();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if ((o instanceof String) || (o instanceof Multipart)) {
			// get email
			fetched.add(message);
			// remove email from inbox
			if (Config.getBooleanParameter(Config.EMAIL_DELETE)) {
				try {
					message.setFlag(Flags.Flag.DELETED, true);
				} catch (MessagingException e) {
					AzeiPlugin.logger.severe("Email " + messageId + " could not be flagged for deletion");
				}
			}
		} else {
			if (o instanceof InputStream) {
				//TODO:ByteArrayInputStream bytes = (ByteArrayInputStream)o;
				AzeiPlugin.logger.severe("Email "+ messageId +" is declared as plain/text, but content is neither readable as string nor as multipart mime");				
			} else {
				AzeiPlugin.logger.severe("Email content could not be read");
			}
		}
	}

	@Override
	public void closed(ConnectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disconnected(ConnectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void opened(ConnectionEvent arg0) {
		AzeiPlugin.logger.entering("EmailFetcher", "openend");
		
		String subject = Config.getStringParameter(Config.EMAIL_SUBJECT);
		Message[] forMe = null;
		
		try {
			AzeiPlugin.logger.fine("Searching for emails with subject starting " + subject );
			forMe = folder.search(new SubjectTerm(subject));
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (forMe.length > 0) {
			FetchProfile fp = new FetchProfile();
			fp.add(FetchProfile.Item.ENVELOPE);
			
			try {
				folder.fetch(forMe, fp);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for (Message m : forMe) {
				receive(m);
			}
		} else {
			AzeiPlugin.logger.fine("No new messages with subject " + subject +" for me");
		}
		
		AzeiPlugin.logger.fine("Done fetching messages");
		this.setState(FETCHED);
		
		AzeiPlugin.logger.finer("Notifying all threads");
		synchronized(threadWaiting) {
			threadWaiting.notifyAll();
		}
	}
	
	public Vector<Message> getFetched() {
		return fetched;
	}

	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	private void setState(int state) {
		this.state = state;
	}
	
}
