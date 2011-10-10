/**
 * 
 */
package de.remk0.azei.azcommand;

import org.gudy.azureus2.plugins.download.Download;
import org.gudy.azureus2.plugins.download.DownloadManager;

/**
 * Sets variables with options to a value
 * 
 * @author Remko Plantenga
 *
 */
public class AzSet extends AzDownloadByStateAndIndex {
	public enum VARIABLE { LIMIT };
	public enum OPTION { DOWNLOAD, UPLOAD };
	
	private VARIABLE variable;
	private OPTION option;
	private int value;
	
	public AzSet(STATES state, int index, VARIABLE var, OPTION op, int value) {
		super(state, index);
		this.variable = var;
		this.option = op;
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see de.remk0.azei.azcommand.AzDownloadByState#executeOnDownload(org.gudy.azureus2.plugins.download.Download)
	 */
	@Override
	protected boolean executeOnDownload(Download d) {
		switch(this.variable) {
		case LIMIT:
			switch(this.option) {
			case UPLOAD:
				d.setUploadRateLimitBytesPerSecond(value);
				return true;
			case DOWNLOAD:
				d.setDownloadRateLimitBytesPerSecond(value);
				return true;
			default:
			}
			break;
		default:
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see de.remk0.azei.azcommand.AzDownloadByState#executeOnDownloads(org.gudy.azureus2.plugins.download.DownloadManager)
	 */
	@Override
	protected void executeOnDownloads(DownloadManager d) {
		
	}

}
