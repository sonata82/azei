package de.remk0.azei;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import de.remk0.azei.config.Config;

/**
 * This is a base class for all interfaces that use mail as input.
 * 
 * @author Remko Plantenga
 *
 */
public abstract class MailQueueInterface extends QueueInterface {
	private boolean newSettings = true;
	private boolean prepared = false;
	private String host;
	private String port;
	private String username;
	private Session session = null;
	private String protocol;
	private String password;
	private String folderName;
	private Store store = null;
	private Folder folder = null;
	
	public MailQueueInterface(String aHost, String aPort, String aProtocol, String aUsername, String aPassword, String aFolderName) {
		AzeiPlugin.logger.entering("MailQueueInterface", "constructor");
		
		host = aHost;
		port = aPort;
		protocol = aProtocol;
		username = aUsername;
		password = aPassword;
		folderName = aFolderName;
	}
	
	@Override
	protected boolean initialize() {
		super.initialize();

		// wait before connecting
		AzeiPlugin.logger.info("Wait " + Config.getIntParameter(Config.EMAIL_INITIAL_WAIT) + "s before starting email processing");
		try {
			Thread.sleep(Config.getIntParameter(Config.EMAIL_INITIAL_WAIT) * 1000);
		} catch (InterruptedException e) {
			AzeiPlugin.logger.severe("Interrupted during initial wait, wtf?");
			return false;
		}
		return true;
	}
	
	@Override
	protected boolean beforeReading() {
		super.beforeReading();
		
		// new settings available?
		if (newSettings) {
			// prepare connection
			if (prepareConnection()) {
				newSettings = false;
			} else {
				AzeiPlugin.logger.severe("Unable to prepare connection");
			}
		}
		if (isPrepared()) {
			return connect();
		}
		return false;
	}
	
	@Override
	protected void interval() throws InterruptedException {
		disconnect();
		Thread.sleep(Config.getIntParameter(Config.EMAIL_LOOP_WAIT) * 1000);		
		super.interval();		
	}
	
	@Override
	protected void cleanup() {
		super.cleanup();
		disconnect();
	}

	/**
	 * Connects to a server using the current connection parameters
	 * 
	 * @return true if successfull, otherwise false
	 */
	private boolean connect() {
		AzeiPlugin.logger.entering("MailQueueInterface", "connect");

		try {
			store = getSession().getStore(getProtocol());
		} catch (NoSuchProviderException e) {
			AzeiPlugin.logger.severe("Unknown protocol '" + getProtocol() + "'");
			return false;
		}
		
		try {
			store.connect(getHost(), getUsername(), getPassword());
		} catch (MessagingException e) {
			String message;
			if (e.getNextException() != null) {
				message = e.getNextException().getMessage();
			} else {
				message = e.getMessage();
			}
			
			AzeiPlugin.logger.severe("Unknown host (" + getHost() + 
					":" + getPort() + ") or wrong credentials: " + 
					message);
			return false;
		}

		try {
			folder = store.getFolder(getFolderName());
		} catch (MessagingException e) {
			AzeiPlugin.logger.severe("No folder labeled " + getFolderName());
			return false;
		}
		
		return true;		
	}
	
	/**
	 * Disconnects from the current server
	 */
	private void disconnect() {
		AzeiPlugin.logger.entering("MailQueueInterface", "disconnect");

		try {
			if (getFolder() != null)
				if (getFolder().isOpen())
					getFolder().close(true);
		} catch (MessagingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			if (getStore() != null)
				getStore().close();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @return the prepared
	 */
	private boolean isPrepared() {
		return prepared;
	}

	/**
	 * @param prepared the prepared to set
	 */
	protected void setPrepared(boolean prepared) {
		this.prepared = prepared;
	}
	
	@Override
	protected void between() throws InterruptedException {
		super.between();
		// wait x seconds
		Thread.sleep(Config.getIntParameter(Config.EMAIL_PROCESS_WAIT) * 1000);
	}
	
	/*
	public void applySettings(String aHost, String aPort, String aProtocol, String aUsername, String aPassword, String aFolderName) {
		AzeiPlugin.logger.entering("MailQueueInterface", "applySettings");
		if (!aHost.equals(host)) {
			host = aHost;
			newSettings = true;
		}
		if (!aPort.equals(port)) {
			port = aPort;
			newSettings = true;
		}
		if (!aProtocol.equals(protocol)) {
			protocol = aProtocol;
			newSettings = true;
		}
		if (!aUsername.equals(username)) {
			username = aUsername;
			newSettings = true;
		}
		if (!aPassword.equals(password)) {
			password = aPassword;
			newSettings = true;		
		}
		if (!aFolderName.equals(folderName)) {
			folderName = aFolderName;
			newSettings = true;
		}
		if (newSettings) { AzeiPlugin.logger.finest("Settings were applied"); }
	}
	*/

	/**
	 * @return the host
	 */
	protected String getHost() {
		return host;
	}

	/**
	 * @return the username
	 */
	protected String getUsername() {
		return username;
	}

	/**
	 * @return the session
	 */
	protected Session getSession() {
		return session;
	}

	/**
	 * @return the password
	 */
	protected String getPassword() {
		return password;
	}

	/**
	 * @return the protocol
	 */
	protected String getProtocol() {
		return protocol;
	}

	/**
	 * @return the folderName
	 */
	protected String getFolderName() {
		return folderName;
	}

	/**
	 * @return the folder
	 */
	protected Folder getFolder() {
		return folder;
	}

	/**
	 * @return the store
	 */
	protected Store getStore() {
		return store;
	}
	
	//protected abstract Collection<? extends Task> read();
	protected abstract boolean prepareConnection();

	/**
	 * @return the port
	 */
	protected String getPort() {
		return port;
	}

	/**
	 * @param session the session to set
	 */
	protected void setSession(Session session) {
		this.session = session;
	}
}
