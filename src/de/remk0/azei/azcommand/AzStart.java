/**
 * 
 */
package de.remk0.azei.azcommand;

import org.gudy.azureus2.plugins.download.Download;
import org.gudy.azureus2.plugins.download.DownloadException;
import org.gudy.azureus2.plugins.download.DownloadManager;

/**
 * Starts a download
 * 
 * @author Remko Plantenga
 *
 */
public class AzStart extends AzDownloadByStateAndIndex {
	
	public AzStart(STATES state, int index) {
		super(state, index);
	}

	@Override
	protected boolean executeOnDownload(Download d) {
		try {
			d.restart();
		} catch (DownloadException e) {
			setErrorMessage(e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	protected void executeOnDownloads(DownloadManager d) {
		d.startAllDownloads();
	}

}
