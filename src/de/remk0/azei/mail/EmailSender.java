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
