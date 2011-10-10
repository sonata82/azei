/**
 * 
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
