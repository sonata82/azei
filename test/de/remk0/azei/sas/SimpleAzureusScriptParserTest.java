package de.remk0.azei.sas;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import de.remk0.azei.azcommand.*;

public class SimpleAzureusScriptParserTest {

	@Test
	public void testParseList() {
		Vector<IAzCommand> commands = SimpleAzureusScriptParser.getInstance().parse("LIST");
		assertTrue(commands.size() == 1);
		assertTrue(commands.get(0) instanceof AzList);
	}
	
	@Test
	public void testParseStart() {
		Vector<IAzCommand> commands = SimpleAzureusScriptParser.getInstance().parse("START");
		assertTrue(commands.size() == 1);
		assertTrue(commands.get(0) instanceof AzStart);
	}
	
	@Test
	public void testParseStartAndList() {
		Vector<IAzCommand> commands = SimpleAzureusScriptParser.getInstance().parse("START \nLIST");
		assertTrue(commands.size() == 2);
		assertTrue(commands.get(0) instanceof AzStart);
		assertTrue(commands.get(1) instanceof AzList);		
	}

}
