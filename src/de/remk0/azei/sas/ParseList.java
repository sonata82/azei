/**
 * 
 */
package de.remk0.azei.sas;

import java.util.Scanner;

import de.remk0.azei.azcommand.AzList;
import de.remk0.azei.azcommand.IAzCommand;
import de.remk0.azei.azcommand.AzDownloadByState.STATES;

/**
 * Parses a string to an Azureus list command
 * 
 * @usage
 * list [<seed|download>]
 * 
 * @author Remko Plantenga
 *
 */
public class ParseList extends ParseCommand {
	public static IAzCommand parse(Scanner myScanner) {
		STATES state = parseState(myScanner);
		
		return new AzList(state);
	}
}
