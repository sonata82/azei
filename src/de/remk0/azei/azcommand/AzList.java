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

import org.gudy.azureus2.plugins.PluginInterface;
import org.gudy.azureus2.plugins.download.Download;
import org.gudy.azureus2.plugins.download.DownloadManager;
import org.gudy.azureus2.plugins.download.DownloadStats;

/**
 * Retrieves a list of downloads, uploads or both
 * 
 * @author Remko Plantenga
 *
 */
public class AzList extends AzDownloadByState {
	private Download[] result;

	public AzList(STATES state) {
		super(state);
	}

	/* (non-Javadoc)
	 * @see de.remk0.azei.azcommand.IAzCommand#parseResult(org.gudy.azureus2.plugins.PluginInterface)
	 * 
	 * health position name state percentComplete sources(connected) dl-speed ETA
	 */
	@Override
	public String parseResult(boolean successfull, PluginInterface pluginInterface) {
		if (successfull) {
			if (this.result != null && this.result.length > 0) {
				String r = "";
				for (Download d : this.result) {
					if (d.isComplete()) {
						// seeding
						r += healthToString(d.getStats().getHealth()) + " " + 
							 d.getPosition() + " " + 
							 d.getName() + " " + 
							 d.getStats().getStatus() + " " +
							 d.getStats().getDownloadCompleted(false) + "% ";
					
						if (d.getPeerManager() != null) {
							 r += Integer.toString(d.getPeerManager().getPeers().length) + " ";
						}
						
						r += Long.toString(d.getStats().getUploadAverage()) + "bytes/s ";
					} else {
						// downloading
						r += healthToString(d.getStats().getHealth()) + " " + 
							 d.getPosition() + " " + 
							 d.getName() + " " + 
							 d.getStats().getStatus() + " " +
							 d.getStats().getDownloadCompleted(false) + "% ";
						
						if (d.getPeerManager() != null) {
							 r += Integer.toString(d.getPeerManager().getPeers().length) + " ";
						}
						
						r += Long.toString(d.getStats().getDownloadAverage()) + "bytes/s ETA " + 
							 d.getStats().getETA();
					}
				}
				return r + "\n";
			}
			return "List empty";
		} else {
			return "Error on retrieving list";
		}
	}
	
	private String healthToString(int health) {
		String strHealth = "";
		switch(health) {
		case DownloadStats.HEALTH_ERROR:
			strHealth = ":-(";
			break;
		case DownloadStats.HEALTH_KO:
			strHealth = ":-(";
			break;
		case DownloadStats.HEALTH_NO_REMOTE:
			strHealth = ":-(";
			break;
		case DownloadStats.HEALTH_NO_TRACKER:
			strHealth = ":-(";
			break;
		case DownloadStats.HEALTH_OK:
			strHealth = ":-)";
			break;
		case DownloadStats.HEALTH_STOPPED:
			strHealth = ":-|";
			break;
		default:
			strHealth = "X-(";
			break;
		}
		return strHealth;
	}

	private String stateToString(int state) {
		String strState = "";
		switch(state) {
		case Download.ST_DOWNLOADING:
			strState = "downloading";
			break;
		case Download.ST_ERROR:
			strState = "error";
			break;
		case Download.ST_PREPARING:
			strState = "preparing";
			break;
		case Download.ST_QUEUED:
			strState = "queued";
			break;
		case Download.ST_READY:
			strState = "ready";
			break;
		case Download.ST_SEEDING:
			strState = "seeding";
			break;
		case Download.ST_STOPPED:
			strState = "stopped";
			break;
		case Download.ST_STOPPING:
			strState = "stopping";
			break;
		case Download.ST_WAITING:
			strState = "waiting";
			break;
		default:
			strState = "unknown";
			break;
		
		}
		return strState;
	}


	@Override
	protected boolean executeOnDownload(Download d) {
		Download[] copy;
		if (this.result == null) {
			copy = new Download[1];
		} else {
			copy = new Download[this.result.length+1];
			int i = 0;
			for (Download download : this.result) {
				copy[i] = download;
				i++;
			}
		}
		copy[copy.length-1] = d;
		this.result = copy;
		return true;
	}


	@Override
	protected void executeOnDownloads(DownloadManager d) {
		this.result = d.getDownloads();		
	}

}
