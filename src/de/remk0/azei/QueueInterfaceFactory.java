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
package de.remk0.azei;

import de.remk0.azei.config.Config;

/**
 * Factory to create QueueInterfaces
 * 
 * @author Remko Plantenga
 *
 */
public class QueueInterfaceFactory {
	private static QueueInterfaceFactory instance = new QueueInterfaceFactory();
	
	private QueueInterfaceFactory() 
	{
	}
	
	public static QueueInterfaceFactory getInstance() {
		return instance;
	}
	
	public QueueInterface build() {
		QueueInterface myInterface = new POP3MailQueueInterface(
				Config.getStringParameter(Config.EMAIL_HOSTNAME), 
				Config.getStringParameter(Config.EMAIL_PORT), 
				Config.getStringParameter(Config.EMAIL_USERNAME), 
				Config.getStringParameter(Config.EMAIL_PASSWORD),
				Config.getStringParameter(Config.EMAIL_FOLDERNAME)
			);
		
		return myInterface;
	}
}
