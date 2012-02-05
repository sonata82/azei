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

import static org.junit.Assert.assertTrue;

import java.util.Vector;

import org.junit.Test;

import de.remk0.azei.azcommand.AzList;
import de.remk0.azei.azcommand.AzStart;
import de.remk0.azei.azcommand.AzStop;
import de.remk0.azei.azcommand.IAzCommand;

public class SimpleAzureusScriptParserTest {

    @Test
    public void testParseList() {
        Vector<IAzCommand> commands = SimpleAzureusScriptParser.getInstance()
                .parse("LIST");
        assertTrue(commands.size() == 1);
        assertTrue(commands.get(0) instanceof AzList);
    }

    @Test
    public void testParseStart() {
        Vector<IAzCommand> commands = SimpleAzureusScriptParser.getInstance()
                .parse("START");
        assertTrue(commands.size() == 1);
        assertTrue(commands.get(0) instanceof AzStart);
    }

    @Test
    public void testParseStartAndList() {
        Vector<IAzCommand> commands = SimpleAzureusScriptParser.getInstance()
                .parse("START \nLIST");
        assertTrue(commands.size() == 2);
        assertTrue(commands.get(0) instanceof AzStart);
        assertTrue(commands.get(1) instanceof AzList);
    }

    @Test
    public void testParseStop() {
        Vector<IAzCommand> commands = SimpleAzureusScriptParser.getInstance()
                .parse("STOP");
        assertTrue(commands.size() == 1);
        assertTrue(commands.get(0) instanceof AzStop);

        commands = SimpleAzureusScriptParser.getInstance().parse("STOP SEED 2");
        assertTrue(commands.size() == 1);
        assertTrue(commands.get(0) instanceof AzStop);
    }

}
