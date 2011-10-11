/*
 * azei
 * Azureus Email Interface Plugin
 * 
 * Copyright (C) 2010 Remko Plantenga
 * 
 * This file is part of azei.
 * 
 * azei is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * azei is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with azei. If not, see <http://www.gnu.org/licenses/>.
 */
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