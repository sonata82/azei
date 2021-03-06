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
package de.remk0.azei;

import java.util.Date;

import org.gudy.azureus2.plugins.PluginInterface;

import de.remk0.azei.config.Config;
import de.remk0.azei.core.Task;
import de.remk0.azei.core.TaskQueue;

/**
 * Processes the queue of pending tasks and updates them on
 * success or failure. The updated tasks are then added to the queue for 
 * finished tasks.
 * 
 * @author Remko Plantenga
 *
 */
public class QueueProcessor implements Runnable {
	private PluginInterface pluginInterface;
	
	public QueueProcessor(PluginInterface aPluginInterface) {
		AzeiPlugin.logger.entering("QueueProcessor", "constructor");
		pluginInterface = aPluginInterface;
	}
	
	@Override
	public void run() {
		AzeiPlugin.logger.entering("QueueProcessor", "run");
		
		boolean loop = true;
		
		while (loop) {
			try {
				// get first task in queue
				Task toDo = TaskQueue.getInstancePending().poll();
				// process task
				if (toDo != null) {
					AzeiPlugin.logger.info("Executing task...");
					Date start = new Date();
					
					boolean successfull = toDo.getAzCommand().execute(pluginInterface);
					
					Date end = new Date();
					String result = null;
					
					if (!successfull) {
						AzeiPlugin.logger.severe("Error on executing task");
					}
					AzeiPlugin.logger.finer("Parsing result...");
					result = toDo.getAzCommand().parseResult(successfull, pluginInterface);
					
					AzeiPlugin.logger.fine("Adding result to task");
					// update task
					toDo.setResult(successfull, start, end, result);
					
					AzeiPlugin.logger.fine("Adding task to done queue");
					// add the task to the finished queue
					TaskQueue.getInstanceDone().add(toDo);
				}
				
				//AzeiPlugin.logger.finer("Sleeping 2s");
				// wait
				Thread.sleep(Config.getIntParameter(Config.QUEUE_POLL_WAIT) * 1000);
			} catch (InterruptedException e) {
				AzeiPlugin.logger.fine("We were interrupted");
				loop = false;
			}
		}
	}

}
