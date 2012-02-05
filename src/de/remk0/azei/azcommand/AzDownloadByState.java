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

/**
 * Represents a download identified by its state.
 * 
 * @author Remko Plantenga
 * 
 */
public abstract class AzDownloadByState implements IAzCommand {
    public static enum STATES {
        ALL, DOWNLOAD, SEED
    };

    private STATES state;

    public AzDownloadByState(STATES state) {
        this.state = state;
    }

    protected STATES getState() {
        return this.state;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.remk0.azei.azcommand.IAzCommand#execute(org.gudy.azureus2.plugins.
     * PluginInterface)
     */
    @Override
    public boolean execute(PluginInterface pluginInterface) {
        boolean ok = true;

        switch (this.state) {
        case DOWNLOAD:
            for (Download d : pluginInterface.getDownloadManager()
                    .getDownloads()) {
                if (!d.isComplete()) {
                    if (!executeOnDownload(d)) {
                        ok = false;
                    }
                }
            }
            break;
        case SEED:
            for (Download d : pluginInterface.getDownloadManager()
                    .getDownloads()) {
                if (d.isComplete()) {
                    if (!executeOnDownload(d)) {
                        ok = false;
                    }
                }
            }
            break;
        case ALL:
            // change all
            executeOnDownloads(pluginInterface.getDownloadManager());
            break;
        }
        return ok;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.remk0.azei.azcommand.IAzCommand#parseResult(boolean,
     * org.gudy.azureus2.plugins.PluginInterface)
     */
    @Override
    public String parseResult(boolean successfull,
            PluginInterface pluginInterface) {
        if (successfull) {
            return "ok";
        } else {
            return "error";
        }
    }

    /**
     * This function is called for every download.
     * 
     * It should return if the status could be changed, otherwise it should
     * return false.
     * 
     * @param d
     * @return
     */
    protected abstract boolean executeOnDownload(Download d);

    /**
     * This function is called once for all downloads.
     * 
     * @param d
     */
    protected abstract void executeOnDownloads(DownloadManager d);

}
