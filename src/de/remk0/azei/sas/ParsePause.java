/**
 * 
 */
package de.remk0.azei.sas;

import java.util.Scanner;

import de.remk0.azei.azcommand.AzPause;
import de.remk0.azei.azcommand.IAzCommand;
import de.remk0.azei.azcommand.AzDownloadByState.STATES;

/**
 * Parses a string to an Azureus pause command
 * 
 * @usage
 * pause [<seed|download> [index]]
 * 
 * @author Remko Plantenga
 *
 */
public class ParsePause extends
		ParseCommand {
	public static IAzCommand parse(Scanner myScanner) {
		STATES state = parseState(myScanner);
		int torrentIndex = parseIndex(myScanner);
		
		return new AzPause(state, torrentIndex);
	}
}
