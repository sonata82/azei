package de.remk0.azei.config;

import org.gudy.azureus2.plugins.PluginConfigListener;
import org.gudy.azureus2.plugins.PluginInterface;
import org.gudy.azureus2.plugins.ui.config.Parameter;
import org.gudy.azureus2.plugins.ui.model.BasicPluginConfigModel;

/**
 * UI Interface for the configuration
 * 
 * @author Remko Plantenga
 *
 */
public class ConfigUI {
	private static BasicPluginConfigModel pluginConfigModel;
	
	public ConfigUI(PluginInterface pluginInterface, PluginConfigListener listener) {
		pluginConfigModel = pluginInterface.getUIManager().createBasicPluginConfigModel("azei");
		
		pluginConfigModel.addLabelParameter2("azei.config.name");
		pluginConfigModel.addLabelParameter2("azei.config.description");
		pluginConfigModel.addHyperlinkParameter2("azei.config.web", "http://www.remk0.de/");
		
		pluginConfigModel.addBooleanParameter2(Config.ENABLED, "azei.config.enabled", Config.getDefaultBooleanValue(Config.ENABLED));
		pluginConfigModel.addBooleanParameter2(Config.LOG_AZUREUS, "azei.config.logAzureus", Config.getDefaultBooleanValue(Config.LOG_AZUREUS));
		pluginConfigModel.addStringParameter2(Config.EMAIL_HOSTNAME, "azei.config.emailHostname", Config.getDefaultStringValue(Config.EMAIL_HOSTNAME));
		pluginConfigModel.addStringParameter2(Config.EMAIL_PORT, "azei.config.emailPort", Config.getDefaultStringValue(Config.EMAIL_PORT));
			
		pluginConfigModel.addStringParameter2(Config.EMAIL_USERNAME, "azei.config.emailUsername", Config.getDefaultStringValue(Config.EMAIL_USERNAME));
		pluginConfigModel.addPasswordParameter2(Config.EMAIL_PASSWORD, "azei.config.emailPassword", 1, new byte[] {} );
		pluginConfigModel.addBooleanParameter2(Config.EMAIL_SMTP_AUTH, "azei.config.emailSMTPAuth", Config.getDefaultBooleanValue(Config.EMAIL_SMTP_AUTH));
		pluginConfigModel.addStringParameter2(Config.EMAIL_SUBJECT, "azei.config.emailSubject", Config.getDefaultStringValue(Config.EMAIL_SUBJECT));
		pluginConfigModel.addStringParameter2(Config.EMAIL_REPLY_SUBJECT, "azei.config.emailReplySubject", Config.getDefaultStringValue(Config.EMAIL_REPLY_SUBJECT));
		pluginConfigModel.addStringParameter2(Config.EMAIL_FROM, "azei.config.emailFrom", Config.getDefaultStringValue(Config.EMAIL_FROM));
		pluginConfigModel.addStringParameter2(Config.EMAIL_FOLDERNAME, "azei.config.emailFolderName", Config.getDefaultStringValue(Config.EMAIL_FOLDERNAME));		
		pluginConfigModel.addBooleanParameter2(Config.EMAIL_DELETE, "azei.config.emailDelete", Config.getDefaultBooleanValue(Config.EMAIL_DELETE));
		
		Parameter[] advancedGroup = new Parameter[6];		
		advancedGroup[0] = pluginConfigModel.addBooleanParameter2(Config.EMAIL_DEBUG_MODE, "azei.config.emailDebugMode", Config.getDefaultBooleanValue(Config.EMAIL_DEBUG_MODE));
		advancedGroup[1] = pluginConfigModel.addBooleanParameter2(Config.LOG_FILE, "azei.config.logFile", Config.getDefaultBooleanValue(Config.LOG_FILE));
		advancedGroup[2] = pluginConfigModel.addIntParameter2(Config.EMAIL_INITIAL_WAIT, "azei.config.emailInitialWait", Config.getDefaultIntValue(Config.EMAIL_INITIAL_WAIT), 0, 32767);
		advancedGroup[3] = pluginConfigModel.addIntParameter2(Config.EMAIL_PROCESS_WAIT, "azei.config.emailProcessWait", Config.getDefaultIntValue(Config.EMAIL_PROCESS_WAIT), 0, 32767);
		advancedGroup[4] = pluginConfigModel.addIntParameter2(Config.EMAIL_LOOP_WAIT, "azei.config.emailLoopWait", Config.getDefaultIntValue(Config.EMAIL_LOOP_WAIT), 1, 32767);
		advancedGroup[5] = pluginConfigModel.addIntParameter2(Config.QUEUE_POLL_WAIT, "azei.config.queuePollWait", Config.getDefaultIntValue(Config.QUEUE_POLL_WAIT), 1, 32767);
		for (Parameter p : advancedGroup) {
			p.setMinimumRequiredUserMode(Parameter.MODE_ADVANCED);
		}
		pluginConfigModel.createGroup("azei.config.advanced", advancedGroup);
		
		pluginInterface.getPluginconfig().addListener(listener);
	}
}
