package de.remk0.azei.logging;

import java.util.logging.*;

import org.gudy.azureus2.plugins.PluginInterface;
import org.gudy.azureus2.plugins.logging.LoggerChannel;

/**
 * Handler for logging to the Azureus log channel
 * 
 * @author Remko Plantenga
 *
 */
public class AzureusHandler extends Handler {
	private LoggerChannel log;
	
	public AzureusHandler(PluginInterface pluginInterface, String name) {
		log = pluginInterface.getLogger().getChannel(name);
	}
	
	@Override
	public void close() throws SecurityException {
		log = null;
	}

	@Override
	public void flush() {
	}

	@Override
	public void publish(LogRecord record) {
		int log_type = -1;
		switch(record.getLevel().intValue()){
			case Integer.MIN_VALUE:		//Level.ALL
			case 300:					//Level.FINEST
			case 400:					//Level.FINER
				break;
			case 500:					//Level.FINE
			case 700:					//Level.CONFIG
			case 800:					//Level.INFO
				log_type = LoggerChannel.LT_INFORMATION;
				break;
			case 900:					//Level.WARNING
				log_type = LoggerChannel.LT_WARNING;
				break;
			case 1000:					//Level.SEVERE
				log_type = LoggerChannel.LT_ERROR;
				break;
			case Integer.MAX_VALUE:		//Level.OFF
				// don't know what to do yet..
				break;
			default:
				// don't know what to do yet..
				break;
		}
		if (log_type != -1)
			log.log(log_type, record.getMessage());
	}


}
