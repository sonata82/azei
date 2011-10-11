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
