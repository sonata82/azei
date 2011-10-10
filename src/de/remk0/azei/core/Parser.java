/**
 * 
 */
package de.remk0.azei.core;

import java.util.Vector;

import javax.mail.Message;

/**
 * This is the base class for all parser.
 * @not used yet
 * 
 * @author Remko Plantenga
 *
 */
public abstract class Parser {
	private Vector<Message> messages = null;
	private Vector<Task> tasks = null;
	
	public Vector<Message> getMessages() {
		return messages;
	}

	public Vector<Task> getTasks() {
		return tasks;
	}
	
	public abstract void parseTo();
	
	public abstract void parseFrom(Vector<Message> retrievedMessages);
}
