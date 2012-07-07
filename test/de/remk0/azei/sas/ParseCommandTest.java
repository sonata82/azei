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

import static org.junit.Assert.assertEquals;

import java.util.Scanner;

import org.junit.Ignore;
import org.junit.Test;

import de.remk0.azei.azcommand.AzDownloadByState.STATES;

/**
 * @author Remko Plantenga
 * 
 */
public class ParseCommandTest {

    /**
     * Test method for
     * {@link de.remk0.azei.sas.ParseCommand#parseState(java.util.Scanner)}.
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
     * Test method for
     * {@link de.remk0.azei.sas.ParseCommand#parseIndex(java.util.Scanner)}.
     */
    @Ignore("as long as <1000 is not recognized")
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
