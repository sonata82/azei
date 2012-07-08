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
import org.junit.Before;

import de.remk0.azei.azcommand.AzDownloadByState.STATES;

/**
 * @author Remko Plantenga
 * 
 */
public class AzBaseTest {

    protected Mockery context = new Mockery();
    protected IAzCommand azcommand;
    protected PluginInterface mockedPluginInterface;
    protected DownloadManager mockedDownloadManager;

    public AzBaseTest() {
        super();
    }

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

    protected Download createDownloadMock(final int index, final boolean isCompleted) {
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

}