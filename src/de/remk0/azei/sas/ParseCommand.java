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

import de.remk0.azei.azcommand.AzDownloadByStateAndIndex;
import de.remk0.azei.azcommand.AzDownloadByState.STATES;

/**
 * Generic helper class for all commands to extend.
 * 
 * @author Remko Plantenga
 *
 */
public abstract class ParseCommand {
	
	/**
	 * Next string denotes download, upload or both
	 * 
	 * @param myScanner
	 * @return
	 */
	public static STATES parseState(Scanner myScanner) {
		STATES state = STATES.ALL;
		if (myScanner.hasNext()) {
			String stateAsString = myScanner.next();
			if (stateAsString.equals(AzDownloadByStateAndIndex.STATES.DOWNLOAD.toString())) {
				state = AzDownloadByStateAndIndex.STATES.DOWNLOAD;
			} else if (stateAsString.equals(AzDownloadByStateAndIndex.STATES.SEED.toString())) {
				state = AzDownloadByStateAndIndex.STATES.SEED;
			}
		} else {
			// leave all state 
		}
		return state;
	}
	
	/**
	 * Next string denotes Index
	 * 
	 * @param myScanner
	 * @return
	 */
	public static int parseIndex(Scanner myScanner) {
		int torrentIndex = -1;
		if (myScanner.hasNextInt()) {
			torrentIndex = myScanner.nextInt(10);
		} else {
			// leave -1 index
		}
		return torrentIndex;
	}
}
