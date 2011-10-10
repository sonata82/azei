package de.remk0.azei;

import java.util.Collection;

import de.remk0.azei.core.Task;
import de.remk0.azei.core.TaskQueue;

/**
 * This is the base class for all interface to our queue of tasks.
 * 
 * The run method is executed on startup and continues to loop until 
 * continueLoop is false or the thread is interrupted.
 * 
 * @author Remko Plantenga
 *
 */
public abstract class QueueInterface implements Runnable {
	private boolean continueLoop = true;

	@Override
	public final void run() {
		continueLoop = initialize();
		
		while (continueLoop) {
			try {

				// queuereader.read();
				// queuereader.newtasks();
				// queue.add
				// 
				// queue.poll
				// queuewriter.write();
				// 
		
				if (beforeReading()) {
					Collection<? extends Task> t = read();
					if (t != null && t.size() > 0) {
						TaskQueue.getInstancePending().addAll(t);
					}
				}
				
				between();
				
				if (beforeWriting()) {
					write(TaskQueue.getInstanceDone().poll());
				}
				
				interval();
			} catch (InterruptedException e) {
				
			}
		}
		
		cleanup();
	}

	/**
	 * This method writes a messages using the data of the finished task. 
	 * It is called after reading new tasks and the between()-method
	 * is done.
	 * 
	 * @param poll a finished task polled from the queue
	 */
	protected abstract void write(Task poll);
	
	/**
	 * This method reads a collection of new messages from input. 
	 * 
	 * @return should return the collection of new tasks or null on no input
	 */
	protected abstract Collection<? extends Task> read();

	/**
	 * Is called at the startup before any processing has been done. Allows a 
	 * descending class to do any initialization on startup.
	 * 
	 * @return true if successfully initialized and ready, return false to never 
	 * start processing
	 */
	protected boolean initialize() {
		return true;
	}
	
	/**
	 * Is called before calling the read()-method. Allows a descending class to
	 * do any initialization before reading. 
	 * 
	 * @return true if successfull and ready, return false to skip reading this 
	 * round.
	 */
	protected boolean beforeReading() {
		return true;
	}

	/**
	 * Is called between reading and writing.
	 * 
	 * @throws InterruptedException
	 */
	protected void between() throws InterruptedException {
		return;
	}

	/**
	 * Is called before calling the write()-method. Allows a descending class to 
	 * do any initialization before writing.
	 */
	protected boolean beforeWriting() {
		return true;
	}

	/**
	 * Is called every loop after calling the write()-method and before 
	 * initiating the next calling of the read()-method.
	 * 
	 * @throws InterruptedException
	 */
	protected void interval() throws InterruptedException {
		return;
	}
	
	/**
	 * Is called after the loop is discontinued to allow a descending class to 
	 * do any cleanup before being destroyed.
	 */
	protected void cleanup() {
		return;
	}

	/**
	 * Determines if the loop is continued
	 * 
	 * @param continueLoop set to true to continue looping, set to false to end 
	 * the looping
	 */
	public void setContinueLoop(boolean continueLoop) {
		this.continueLoop = continueLoop;
	}
}
