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
