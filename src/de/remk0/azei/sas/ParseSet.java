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

import de.remk0.azei.azcommand.AzSet;
import de.remk0.azei.azcommand.IAzCommand;
import de.remk0.azei.azcommand.AzDownloadByState.STATES;
import de.remk0.azei.azcommand.AzSet.OPTION;
import de.remk0.azei.azcommand.AzSet.VARIABLE;

/**
 * Parses a string to an Azureus set command.
 * 
 * @usage
 * set <seed|download> [index] <limit> <seed|download> <value>
 * 
 * @author Remko Plantenga
 *
 */
public class ParseSet extends ParseCommand {
	public static IAzCommand parse(Scanner myScanner) {
		STATES state = parseState(myScanner);
		int torrentIndex = parseIndex(myScanner);
		VARIABLE var = null;
		if (myScanner.hasNext()) {
			String varAsString = myScanner.next();
			if (varAsString.equals(VARIABLE.LIMIT.toString())) {
				var = VARIABLE.LIMIT;
			}
		}
		if (var == null) {
			//unknown variable given 
		}
		OPTION op = null;
		if (myScanner.hasNext()) {
			String optionAsString = myScanner.next();
			if (optionAsString.equals(OPTION.DOWNLOAD.toString())) {
				op = OPTION.DOWNLOAD;
			} else if (optionAsString.equals(OPTION.UPLOAD.toString())) {
				op = OPTION.UPLOAD;
			}
		}
		if (op == null) {
			// unknown option given
		}
		int val = Integer.MIN_VALUE;
		if (myScanner.hasNextInt()) {
			val = myScanner.nextInt();
		}
		if (val == Integer.MIN_VALUE) {
			// unknown value given
		}
		return new AzSet(state, torrentIndex, var, op, val);
	}
}
