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
