package de.remk0.azei.sas;

import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.Test;

import de.remk0.azei.azcommand.AzDownloadByState.STATES;

/**
 * @author Remko Plantenga
 *
 */
public class ParseCommandTest {

	/**
	 * Test method for {@link de.remk0.azei.sas.ParseCommand#parseState(java.util.Scanner)}.
	 */
	@Test
	public final void testParseState() {
		String content = "SEED";
		Scanner myScanner = new Scanner(content).useDelimiter("\\W");
		assertEquals(STATES.SEED, ParseCommand.parseState(myScanner));
		content = "DOWNLOAD";
		myScanner = new Scanner(content).useDelimiter("\\W");
		assertEquals(STATES.DOWNLOAD, ParseCommand.parseState(myScanner));
		content = "DOWNLOAD2";
		myScanner = new Scanner(content).useDelimiter("\\W");
		assertEquals(STATES.ALL, ParseCommand.parseState(myScanner));
		content = "DOWNLAOD";
		myScanner = new Scanner(content).useDelimiter("\\W");
		assertEquals(STATES.ALL, ParseCommand.parseState(myScanner));
		content = "UPLOAD";
		myScanner = new Scanner(content).useDelimiter("\\W");
		assertEquals(STATES.ALL, ParseCommand.parseState(myScanner));
	}

	/**
	 * Test method for {@link de.remk0.azei.sas.ParseCommand#parseIndex(java.util.Scanner)}.
	 */
	@Test
	public final void testParseIndex() {
		String content = "10000";
		Scanner myScanner = new Scanner(content).useDelimiter("\\W");
		assertEquals(10000, ParseCommand.parseIndex(myScanner));
		content = "-10000";
		myScanner = new Scanner(content).useDelimiter("\\W");
		assertEquals(-1, ParseCommand.parseIndex(myScanner));
		content = "0";
		myScanner = new Scanner(content).useDelimiter("\\W");
		assertEquals(-1, ParseCommand.parseIndex(myScanner));
	}

}
