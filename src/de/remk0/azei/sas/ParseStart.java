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

import de.remk0.azei.azcommand.AzStart;
import de.remk0.azei.azcommand.IAzCommand;
import de.remk0.azei.azcommand.AzDownloadByState.STATES;

/**
 * Parses a string an Azureus start command
 * 
 * @usage
 * start [<seed|download> [index]]
 * 
 * @author Remko Plantenga
 *
 */
public class ParseStart extends ParseCommand {

	public static IAzCommand parse(Scanner myScanner) {
		STATES state = parseState(myScanner);
		int torrentIndex = parseIndex(myScanner);
		
		return new AzStart(state, torrentIndex);
	}
	
}
