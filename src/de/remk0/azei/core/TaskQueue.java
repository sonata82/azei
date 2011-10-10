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
