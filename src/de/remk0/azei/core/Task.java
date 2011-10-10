package de.remk0.azei.core;

import java.util.Date;

import javax.mail.internet.InternetAddress;

import de.remk0.azei.azcommand.IAzCommand;

/**
 * Represents a task to be processed. A task consist of a command to Azureus 
 * an orderer, a recipient and - once processed by Azureus - a result.
 * 
 * @author Remko Plantenga
 *
 */
public class Task {
	private IAzCommand azCommand;
	private boolean successfull;
	private String result;
	private InternetAddress recipient;
	private InternetAddress orderer;
	private Date start;
	private Date end;
	private String subject;
	
	/**
	 * Creates a new instance of Task using the given parameters
	 * 
	 * @param anOrderer Orderer of the task
	 * @param aCommand Command to process
	 * @param aRecipient Recipient of the result
	 * @param aSubject Subject to use
	 */
	public Task(InternetAddress anOrderer, IAzCommand aCommand, InternetAddress aRecipient, String aSubject) {
		orderer = anOrderer;
		azCommand = aCommand;
		recipient = aRecipient;
		subject = aSubject;
	}

	/**
	 * @return the result
	 */
	public boolean getSuccessfull() {
		return successfull;
	}

	/**
	 * Sets the result of this task
	 * 
	 * @param successfull completed successfull
	 * @param start timestamp the task started
	 * @param end timestamp the task ended
	 * @param result result of the task as a string
	 */
	public void setResult(boolean successfull, Date start, Date end, String result) {
		this.successfull = successfull;
		this.start = start;
		this.end = end;
		this.result = result;
	}

	/**
	 * @return the azCommand
	 */
	public IAzCommand getAzCommand() {
		return azCommand;
	}

	/**
	 * @return the duration
	 */
	public long getDuration() {
		return end.getTime()-start.getTime();
	}

	/**
	 * @return the recipient
	 */
	public InternetAddress getRecipient() {
		return recipient;
	}

	/**
	 * @return the orderer
	 */
	public InternetAddress getOrderer() {
		return orderer;
	}

	/**
	 * @return the start
	 */
	public Date getStart() {
		return start;
	}

	/**
	 * @return the end
	 */
	public Date getEnd() {
		return end;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}
	
}