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
package de.remk0.azei.azcommand;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.gudy.azureus2.plugins.PluginInterface;
import org.gudy.azureus2.plugins.download.Download;
import org.gudy.azureus2.plugins.download.DownloadManager;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import de.remk0.azei.azcommand.AzDownloadByState.STATES;

/**
 * @author Remko Plantenga
 * 
 */
public class AzStopTest {

    private Mockery context = new Mockery();
    private AzStop azcommand;
    private PluginInterface mockedPluginInterface;
    private DownloadManager mockedDownloadManager;

    @Before
    public void prepare() {
        azcommand = new AzStop(STATES.SEED, 2);
        mockedPluginInterface = this.context.mock(PluginInterface.class);
        mockedDownloadManager = this.context.mock(DownloadManager.class);

        this.context.checking(new Expectations() {
            {
                allowing(mockedPluginInterface).getDownloadManager();
                will(returnValue(mockedDownloadManager));
            }
        });
    }

    /**
     * Test method for
     * {@link de.remk0.azei.azcommand.AzDownloadByStateAndIndex#execute(org.gudy.azureus2.plugins.PluginInterface)}
     * .
     */
    @Test
    public void testExecuteInvalidIndex() {
        final Download[] downloads = new Download[] { createDownload(1, false) };
        this.context.checking(new Expectations() {
            {
                allowing(mockedDownloadManager).getDownloads();
                will(returnValue(downloads));
            }
        });
        boolean success = azcommand.execute(mockedPluginInterface);
        assertFalse(success);
    }

    private Download createDownload(final int index, final boolean isCompleted) {
        final Download d1 = this.context.mock(Download.class, "d" + isCompleted
                + index);
        this.context.checking(new Expectations() {
            {
                allowing(d1).getPosition();
                will(returnValue(index));
                allowing(d1).isComplete();
                will(returnValue(isCompleted));
            }
        });
        return d1;
    }

    @Test
    public void testExecute() throws Exception {
        final Download[] downloads = new Download[] { createDownload(1, false),
                createDownload(1, true), createDownload(2, true) };
        this.context.checking(new Expectations() {
            {
                allowing(mockedDownloadManager).getDownloads();
                will(returnValue(downloads));
                oneOf(downloads[2]).stop();
            }
        });
        boolean success = azcommand.execute(mockedPluginInterface);
        assertTrue(success);
    }

}
