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

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.gudy.azureus2.plugins.Plugin;
import org.gudy.azureus2.plugins.PluginConfigListener;
import org.gudy.azureus2.plugins.PluginException;
import org.gudy.azureus2.plugins.PluginInterface;
import org.gudy.azureus2.plugins.PluginListener;

import com.sun.mail.util.logging.MailHandler;

import de.remk0.azei.config.Config;
import de.remk0.azei.config.ConfigUI;
import de.remk0.azei.logging.AzureusHandler;

/**
 * Handles interaction between Azureus and the plugin
 * 
 * @author Remko Plantenga
 *
 */
public class AzeiPlugin implements Plugin, PluginListener, PluginConfigListener {
	public static final String PRODUCTNAME = "azei";
	public static final String VERSION = "1.0";
	private QueueInterface emailInterface = null;
	private Thread emailInterfaceThread = null;
	private Thread queueProcessorThread = null;
	
	private Config configuration = null;
	private ConfigUI configurationUI = null;
	
	public static Logger logger = Logger.getLogger("de.remk0.azei");
	private static FileHandler fh = null;
	private static AzureusHandler ah = null;
	private static ConsoleHandler ch = new ConsoleHandler();
	private static MailHandler mh = new MailHandler();
	
	/* (non-Javadoc)
	 * @see org.gudy.azureus2.plugins.Plugin#initialize(org.gudy.azureus2.plugins.PluginInterface)
	 */
	@Override
	public void initialize(PluginInterface pluginInterface) throws PluginException {
		
		//
		// load configuration
		//
		configuration = new Config(pluginInterface);
		configurationUI = new ConfigUI(pluginInterface, this);		
		
		//
		// logging
		//
		if (Config.getBooleanParameter(Config.LOG_FILE)) {
			try {
				fh = new FileHandler("azei.log", true);
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			fh.setLevel(Level.ALL);
			logger.addHandler(fh);
		}
		if (Config.getBooleanParameter(Config.LOG_AZUREUS)) {
			ah = new AzureusHandler(pluginInterface, "azei");
			ah.setLevel(Level.ALL);
			logger.addHandler(ah);
		}
		//ch.setLevel(Level.ALL);
		//mh.setLevel(Level.SEVERE);
		//logger.addHandler(ch);
		//logger.addHandler(mh);
		logger.setLevel(Level.ALL);
		logger.setUseParentHandlers(false);
		
		logger.entering("AzeiPlugin", "initialize");
		
		//
		// hook up to global events
		//
		pluginInterface.addListener(this);
		
		//
		// create controller
		//
		createEmailInterfaceAndThread();
		
		//
		// create thread for QueueProcessor
		//
		createQueueProcessorThread(pluginInterface);

		//
		// start threads on @see initializationComplete		
		//
	}

	@Override
	public void closedownComplete() {
		logger.entering("AzeiPlugin", "closedownComplete");
	}

	@Override
	public void closedownInitiated() {
		logger.entering("AzeiPlugin", "closedownInitiated");
		shutdown();
	}

	@Override
	public void initializationComplete() {
		logger.entering("AzeiPlugin", "initializationComplete");
		if (Config.getBooleanParameter(Config.ENABLED)) {
			start();
		}
	}

	@Override
	public void configSaved() {
		logger.entering("AzeiPlugin", "configSaved");
		/*
		if (emailInterfaceThread != null && emailInterface != null) {
			emailInterface.setContinueLoop(false);
			emailInterfaceThread.interrupt();
			try {
				emailInterfaceThread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			emailInterface = null;
			emailInterfaceThread = null;
			
			createEmailInterfaceAndThread();
			if (Config.getBooleanParameter(Config.ENABLED)) {
				emailInterfaceThread.start();
			}
		}*/
	}
	
	private void createEmailInterfaceAndThread() {
		AzeiPlugin.logger.entering("AzeiPlugin", "createEmailInterfaceAndThread");
		
		emailInterface = QueueInterfaceFactory.getInstance().build();
		
		// create thread for EmailInterface
		emailInterfaceThread = new Thread(emailInterface);
		emailInterfaceThread.setName("azei:emailInterface");		
	}

	private void createQueueProcessorThread(PluginInterface pluginInterface) {
		AzeiPlugin.logger.entering("AzeiPlugin", "createQueueProcessorThread");
		
		queueProcessorThread = new Thread(new QueueProcessor(pluginInterface));
		queueProcessorThread.setName("azei:queueProcessor");
	}
	
	private void start()
	{
		AzeiPlugin.logger.entering("AzeiPlugin", "start");
		emailInterfaceThread.start();
		queueProcessorThread.start();		
	}
	
	private void shutdown()
	{
		AzeiPlugin.logger.entering("AzeiPlugin", "shutdown");
		if (emailInterface != null) {
			emailInterface.setContinueLoop(false);
		}
		if (emailInterfaceThread != null) {
			emailInterfaceThread.interrupt();
		}
		if (queueProcessorThread != null) {
			queueProcessorThread.interrupt();
		}		
	}
}
