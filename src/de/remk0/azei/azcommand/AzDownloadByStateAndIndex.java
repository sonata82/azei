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

/**
 * Represents a download identified by its state and/or index.
 * 
 * @author Remko Plantenga
 *
 */
public abstract class AzDownloadByStateAndIndex extends AzDownloadByState {
	private int index;
	private String errorMessage;
	
	/**
	 * Creates a new instance of AzChangeState with the given parameters
	 * 
	 * @param state
	 * @param index
	 */
	public AzDownloadByStateAndIndex(STATES state, int index) {
		super(state);
		this.index = index;
	}
	
	/* (non-Javadoc)
	 * @see de.remk0.azei.azcommand.IAzCommand#execute(org.gudy.azureus2.plugins.PluginInterface)
	 */
	@Override
	public boolean execute(PluginInterface pluginInterface) {
		boolean ok = true;
		
		if (this.index == -1) {
			// change the status of download defined by state only
			ok = super.execute(pluginInterface);
		} else {
			// change the status of download defined by index
			for (Download d : pluginInterface.getDownloadManager().getDownloads()) {
				if (d.getPosition() == this.index) {
					if (!executeOnDownload(d)) {
						ok = false;
					} else {
						ok = true;
					}
					break;
				}
			}
		}
		return ok;
	}

	/* (non-Javadoc)
	 * @see de.remk0.azei.azcommand.IAzCommand#parseResult(org.gudy.azureus2.plugins.PluginInterface)
	 */
	@Override
	public String parseResult(boolean successfull, PluginInterface pluginInterface) {
		if (successfull) { 
			return "ok"; 
		} else { 
			if (this.index != -1) {
				return "command failed. " + Integer.toString(this.index) + " = illegal index?\n" +
						"complete error message follows:\n" + getErrorMessage();
			} else {
				return super.parseResult(successfull, pluginInterface);
			}
		}
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	protected void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the errorMessage
	 */
	protected String getErrorMessage() {
		return errorMessage;
	}
}
