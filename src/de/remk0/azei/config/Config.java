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
package de.remk0.azei.config;

import java.util.HashMap;

import org.gudy.azureus2.plugins.PluginConfig;
import org.gudy.azureus2.plugins.PluginInterface;

/**
 * Holds all settings using the Azureus Plugin API
 * 
 * @author Remko Plantenga
 *
 */
public class Config {
	public static final String EMAIL_SUBJECT = "emailSubject";
	public static final String EMAIL_HOSTNAME = "emailHostname";
	public static final String EMAIL_PORT = "emailPort";
	public static final String EMAIL_PROTOCOL = "emailProtocol";
	public static final String EMAIL_USERNAME = "emailUsername";
	public static final String EMAIL_PASSWORD = "emailPassword";
	public static final String EMAIL_SMTP_AUTH = "emailSMTPAuth";
	public static final String EMAIL_DELETE = "emailDelete";
	public static final String EMAIL_REPLY_SUBJECT = "emailReplySubject";
	public static final String EMAIL_FROM = "emailFrom";
	public static final String EMAIL_DEBUG_MODE = "emailDebugMode";
	public static final String EMAIL_FOLDERNAME = "emailFolderName";
	public static final String EMAIL_INITIAL_WAIT = "emailInitialWait";
	public static final String EMAIL_PROCESS_WAIT = "emailProcessWait";
	public static final String EMAIL_LOOP_WAIT = "emailLoopWait";
	public static final String QUEUE_POLL_WAIT = "queuePollWait";
	public static final String ENABLED = "enabled";
	public static final String LOG_AZUREUS = "logAzureus";
	public static final String LOG_FILE = "logFile";
	private static HashMap<String, String> defaultStringValues = new HashMap<String, String>();	
	private static HashMap<String, Boolean> defaultBooleanValues = new HashMap<String, Boolean>();
	private static HashMap<String, Integer> defaultIntValues = new HashMap<String, Integer>();
	
	private static PluginConfig pluginConfig;
	
	public Config(PluginInterface pluginInterface) {
		pluginConfig = pluginInterface.getPluginconfig();
		
		defaultBooleanValues.put(ENABLED, false);
		defaultStringValues.put(EMAIL_HOSTNAME, "127.0.0.1");
		defaultStringValues.put(EMAIL_PORT, "110");
		defaultStringValues.put(EMAIL_PROTOCOL, "pop3");
		defaultStringValues.put(EMAIL_SUBJECT, "[azei]");
		defaultBooleanValues.put(EMAIL_DELETE, false);
		defaultStringValues.put(EMAIL_REPLY_SUBJECT, "RE: [azei] Result of your request");
		defaultBooleanValues.put(EMAIL_DEBUG_MODE, false);
		defaultStringValues.put(EMAIL_FOLDERNAME, "INBOX");
		defaultIntValues.put(EMAIL_INITIAL_WAIT, 3);
		defaultIntValues.put(EMAIL_PROCESS_WAIT, 5);
		defaultIntValues.put(EMAIL_LOOP_WAIT, 30);
		defaultIntValues.put(QUEUE_POLL_WAIT, 2);
		defaultBooleanValues.put(LOG_AZUREUS, true);
		defaultBooleanValues.put(LOG_FILE, false);
		defaultBooleanValues.put(EMAIL_SMTP_AUTH, false);
	}
	
	public static void setParameter(String name, String value) {
		pluginConfig.setPluginParameter(name, value);
	}
	
	public static String getStringParameter(String name) {
		String defValue = getDefaultStringValue(name);
		return pluginConfig.getPluginStringParameter(name, defValue);
	}
	
	public static String getDefaultStringValue(String name) {
		String defValue = defaultStringValues.get(name);
		if (defValue == null) return ""; else return defValue; 
	}
	
	public static boolean getBooleanParameter(String name) {
		boolean defValue = getDefaultBooleanValue(name);
		return pluginConfig.getPluginBooleanParameter(name, defValue);
	}
	
	public static boolean getDefaultBooleanValue(String name) {
		boolean defValue = defaultBooleanValues.get(name);
		return defValue;
	}
	
	public static int getIntParameter(String name) {
		int defValue = getDefaultIntValue(name);
		return pluginConfig.getPluginIntParameter(name, defValue);
	}
	
	public static int getDefaultIntValue(String name) {
		int defValue = defaultIntValues.get(name);
		return defValue;
	}
}
