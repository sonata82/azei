/*
 * azei
 * Azureus Email Interface Plugin
 * 
 * Copyright (C) 2012 Remko Plantenga
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
package de.remk0.azei.azcommand;

import static org.junit.Assert.assertTrue;

import org.gudy.azureus2.plugins.download.Download;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.remk0.azei.azcommand.AzDownloadByState.STATES;
import de.remk0.azei.azcommand.AzSet.OPTION;
import de.remk0.azei.azcommand.AzSet.VARIABLE;

/**
 * @author Remko Plantenga
 * 
 */
@RunWith(JMock.class)
public class AzSetTest extends AzBaseTest {

    @Test
    public void testExecute() {

        azcommand = new AzSet(STATES.DOWNLOAD, 2, VARIABLE.LIMIT,
                OPTION.DOWNLOAD, 20);

        final Download dl = createDownloadMock(2, false);
        this.context.checking(new Expectations() {
            {
                allowing(dl).setDownloadRateLimitBytesPerSecond(20);
            }
        });

        final Download[] downloads = new Download[] { createDownloadMock(1, false),
                dl, createDownloadMock(3, false) };

        this.context.checking(new Expectations() {
            {
                allowing(mockedDownloadManager).getDownloads();
                will(returnValue(downloads));
            }
        });
        this.context.checking(new Expectations() {
            {
                allowing(mockedPluginInterface).getDownloadManager();
                will(returnValue(mockedDownloadManager));
            }
        });

        boolean success = azcommand.execute(mockedPluginInterface);
        assertTrue(success);
    }

}
