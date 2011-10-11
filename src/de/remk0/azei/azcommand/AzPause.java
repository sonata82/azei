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

import org.gudy.azureus2.plugins.download.Download;
import org.gudy.azureus2.plugins.download.DownloadManager;

/**
 * Pauses a download
 * 
 * @author Remko Plantenga
 *
 */
public class AzPause extends AzDownloadByStateAndIndex {

	public AzPause(STATES state, int index) {
		super(state, index);
	}

	/* (non-Javadoc)
	 * @see de.remk0.azei.azcommand.AzChangeState#changeStatus(org.gudy.azureus2.plugins.download.Download)
	 */
	@Override
	protected boolean executeOnDownload(Download d) {
		d.pause();
		return true;
	}

	/* (non-Javadoc)
	 * @see de.remk0.azei.azcommand.AzChangeState#changeStatus(org.gudy.azureus2.plugins.download.DownloadManager)
	 */
	@Override
	protected void executeOnDownloads(DownloadManager d) {
		for (Download download : d.getDownloads()) {
			executeOnDownload(download);
		}
	}

}
