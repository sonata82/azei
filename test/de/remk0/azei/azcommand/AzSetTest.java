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

import org.gudy.azureus2.plugins.PluginInterface;
import org.gudy.azureus2.plugins.download.Download;
import org.gudy.azureus2.plugins.download.DownloadManager;
import org.jmock.Expectations;
import org.jmock.Mockery;
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
public class AzSetTest {

    private Mockery context = new Mockery();
    private PluginInterface mockedPluginInterface;
    private DownloadManager mockedDownloadManager;
    private IAzCommand azcommand;

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
    public void testExecute() {
        azcommand = new AzSet(STATES.DOWNLOAD, 2, VARIABLE.LIMIT,
                OPTION.DOWNLOAD, 20);
        mockedPluginInterface = this.context.mock(PluginInterface.class);
        mockedDownloadManager = this.context.mock(DownloadManager.class);
        final Download dl = createDownload(2, false);
        this.context.checking(new Expectations() {
            {
                allowing(dl).setDownloadRateLimitBytesPerSecond(20);
            }
        });
        final Download[] downloads = new Download[] { createDownload(1, false),
                dl, createDownload(3, false) };
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
        azcommand.execute(mockedPluginInterface);
    }

}
