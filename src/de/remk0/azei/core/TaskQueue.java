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
package de.remk0.azei.core;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Queue of tasks in a static wrapper
 * 
 * getInstancePending() retrieves the queue for pending tasks
 * getInstanceDone() retrieves the queue for finished tasks
 * 
 * @author Remko Plantenga
 *
 */
public class TaskQueue extends LinkedBlockingQueue<Task> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static TaskQueue instancePending = new TaskQueue(10);
	private static TaskQueue instanceDone = new TaskQueue(10);
	
	private TaskQueue(int capacity) {
		//
		super(capacity);
	}
	
	/**
	 * Returns the queue of tasks pending
	 * 
	 * @return
	 */
	public static TaskQueue getInstancePending() {
		return instancePending;
	}
	
	/**
	 * Returns the queue of tasks processed
	 * 
	 * @return
	 */
	public static TaskQueue getInstanceDone() {
		return instanceDone;
	}
	
}
