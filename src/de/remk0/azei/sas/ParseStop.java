/**
 * 
 */
package de.remk0.azei.sas;

import java.util.Scanner;

import de.remk0.azei.azcommand.AzStop;
import de.remk0.azei.azcommand.IAzCommand;
import de.remk0.azei.azcommand.AzDownloadByState.STATES;

/**
 * Parses a string to an Azureus stop command
 * 
 * @usage
 * stop [<seed|download> [index]] 
 * 
 * @author Remko Plantenga
 *
 */
public class ParseStop extends
		ParseCommand {
	public static IAzCommand parse(Scanner myScanner) {
		STATES state = parseState(myScanner);
		int torrentIndex = parseIndex(myScanner);
		
		return new AzStop(state, torrentIndex);		
	}
}
