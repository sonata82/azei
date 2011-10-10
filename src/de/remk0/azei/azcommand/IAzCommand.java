package de.remk0.azei.azcommand;

import org.gudy.azureus2.plugins.PluginInterface;

/**
 * Interface for executing Azureus commands
 * 
 * @author Remko Plantenga
 *
 */
public interface IAzCommand {
	/**
	 * This method is called to execute any commands on the 
	 * PluginInterface from Azureus.
	 * 
	 * @param pluginInterface
	 * @return
	 */
	public boolean execute(PluginInterface pluginInterface);
	/**
	 * This method is called to parse results from the executed 
	 * commands to a string.
	 * 
	 * @param successfull
	 * @param pluginInterface
	 * @return
	 */
	public String parseResult(boolean successfull, PluginInterface pluginInterface);
}
