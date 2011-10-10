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
