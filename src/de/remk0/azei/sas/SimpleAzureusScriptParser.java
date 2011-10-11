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
package de.remk0.azei.sas;

import java.util.Scanner;
import java.util.Vector;

import de.remk0.azei.azcommand.IAzCommand;

/**
 * Simple Azureus Script Parser
 * 
 * Factory to build IAzCommands from strings. Parses strings to their 
 * corresponding IAzCommand and returns them as a vector.
 * 
 * @author Remko Plantenga
 *
 */
public class SimpleAzureusScriptParser {
	public enum SAS_COMMAND { LIST, START, STOP, PAUSE, SET };
	
	private static SimpleAzureusScriptParser instance = new SimpleAzureusScriptParser();
	
	private SimpleAzureusScriptParser()
	{	
	}
	
	public static SimpleAzureusScriptParser getInstance() {
		return instance;
	}
	
	public Vector<IAzCommand> parse(String content) {
		Vector<IAzCommand> commands = new Vector<IAzCommand>();
		
		Scanner myScanner = new Scanner(content).useDelimiter("\\W");
		
		while (myScanner.hasNext()) {
			String command = myScanner.next();
			IAzCommand c = null;
			
			if (command.equals(SAS_COMMAND.LIST.toString())) {
				c = ParseList.parse(myScanner);
			} else if (command.equals(SAS_COMMAND.START.toString())) {
				c = ParseStart.parse(myScanner); 
			} else if (command.equals(SAS_COMMAND.STOP.toString())) {
				c = ParseStop.parse(myScanner);
			} else if (command.equals(SAS_COMMAND.PAUSE.toString())) {
				c = ParsePause.parse(myScanner);
			} else if (command.equals(SAS_COMMAND.SET.toString())) {
				c = ParseSet.parse(myScanner);
			}
			
			if (c != null) {
				commands.add(c);
			}
		}
		
		return commands;
	}
}
