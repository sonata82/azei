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
