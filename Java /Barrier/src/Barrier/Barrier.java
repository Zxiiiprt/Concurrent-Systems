/**
 * 
 */
package Barrier;
/**
 * @author Aaditya Sharma
 *
 */
public class Barrier {

	/**
	 * The Checkpoint method will block all threads until reached the maximum number of Threads (private int maxThreads)
	 * To keep track of the number of Threads that have entered the method/been blocked by checkpoint, we hold the value in a counter (private int count)
	 */
	private int count, maxThreads;
	
	/**
	 * Constructor for the Barrier class
	 * Creates a new Barrier object with maxThreads number of Threads to block upon and count initialized to 0
	 * 
	 * @param n : Barrier object will wait until n threads have invoked checkpoint()
	 */
	public Barrier(int n) {
		this.maxThreads = n;
		count = 0;
	}
	
	/**
	 * When Checkpoint() is invoked by a Thread, it is blocked until the Thread was the (n+1)th Thread
	 * n is our maxThreads to be blocked
	 * If we reach the (n+1)th Thread, then all Threads that have been previously blocked will be unblocked now
	 * 
	 * @return : boolean blocked, "blocked" will return if the Thread was blocked or not
	 */
	public boolean checkpoint() {
		
		//Synchronize the barrier object Thread invoking checkpoint()
		synchronized(this) {
			//When a Thread enters this method, the boolean value is initialized to false
			boolean blocked = false;
			//Increment the counter every time a Thread enters this method
			//This keeps track of the Total Number of Threads that have invoked checkpoint()
			count++;
			//While the number of Threads is less than the maximum Threads to be blocked AND the Thread is unblocked
			while(count < maxThreads && !blocked) {
				try {
					//Block the Thread
					blocked = true;
					//Thread waits until count reaches (n+1)
					wait();
				} catch (InterruptedException e) {
					System.err.println(e);
				}
			}
			/**
			 * Reaching this point means that the Threads have exit the waiting set and all n Threads have been successfully blocked 
			 * Furthermore, the (n+1)th Thread has invoked checkpoint() allowing the Threads that have been blocked to be released
			 * 
			 * Initialize count back to 0
			 */
			count = 0;
			//Notify all the Threads
			notifyAll();
			//Return the boolean value of blocked
			return blocked;
		}
	}
}

