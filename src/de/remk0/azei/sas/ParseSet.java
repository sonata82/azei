/**
 * 
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
