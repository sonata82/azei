/**
 * 
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
