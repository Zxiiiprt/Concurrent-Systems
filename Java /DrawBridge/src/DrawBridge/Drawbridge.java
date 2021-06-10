/**
 * 
 */
package DrawBridge;
/**
 * @author zxiiiprt
 *
 */
public class Drawbridge {
	
	enum State {
		OPEN, OPENING, CLOSE, CLOSING
	}

	public final static int CLEARANCE = 10;
	State state = State.CLOSE;
	public boolean traffic = false; //False = Traffic must Stop on bridge, True = Traffic on bridge
	
	public void open() {
		synchronized(this) {
			while(state != State.OPEN) { //While we're in any other state but OPEN
				if(state == State.OPENING || state == State.CLOSING) { //If the bridge is Opening/Closing
					try {
						traffic = false; //Implied but being Defensive
						System.out.println("Thread: " + Thread.currentThread().getName() + " - Bridge State before Wait: " + state);
						System.out.println("Wait for Bridge to finish Opening/Closing");
						wait(); //Wait for the state to change from Opening/Closing
						System.out.println("Thread: " + Thread.currentThread().getName() + " - Bridge State after Wait: " + state); 
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				else if(state == State.CLOSE) { //If the Bridge is Closed
					try {
						traffic = false; //Stop the Traffic
						state = State.OPENING; //Change State from Close to Opening
						System.out.println("Thread: " + Thread.currentThread().getName() + ", invoked - Bridge State: " + state);
						Thread.sleep(5000); //Wait for 1minute to pass
						state = State.OPEN; //Change State from Opening to Open
						//System.out.println("Thread: " + Thread.currentThread().getName() + " - Bridge State is now: " + state);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}	
			}
			System.out.println("Thread: " + Thread.currentThread().getName() + ", Requested - Bridge State is now : " + state);
			notifyAll();
		}
		
	}
	
	public void close() {
		synchronized(this) {
			while(state != State.CLOSE) { //While we're in any other state but Closed
				if(state == State.OPENING || state == State.CLOSING) { //If the bridge is Opening/Closing
					try {
						traffic = false; //Implied but being Defensive
						System.out.println("Thread: " + Thread.currentThread().getName() + " - Bridge State before Wait: " + state);
						System.out.println("Wait for Bridge to finish Opening/Closing");
						wait(); //Wait for the state to change from Opening/Closing
						System.out.println("Thread: " + Thread.currentThread().getName() + " - Bridge State after Wait: " + state);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				else if(state == State.OPEN) { //If the Bridge is Open
					try {
						traffic = false; //Being Defensive
						state = State.CLOSING; //Change State from Open to Closing
						System.out.println("Thread: " + Thread.currentThread().getName() + ", invoked - Bridge State: " + state);
						Thread.sleep(5000); //Wait for 1minute to pass
						state = State.CLOSE; //Change State from Closing to Close
						traffic = true; //Cars can now pass through
						//System.out.println("Thread: " + Thread.currentThread().getName() + " - Bridge State is now: " + state);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}	
			}
			System.out.println("Thread: " + Thread.currentThread().getName() + ", Requested - Bridge State is now : " + state);
			notifyAll(); 
		}
	}
	
	public void goWest() {
		synchronized(this) {
			while(state != State.CLOSE) {
				try {
					System.out.println("Thread: " + Thread.currentThread().getName() + " is waiting for Bridge to Close, Traffic: " + traffic);
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			try {
				traffic = true; //Traffics about to pass
				System.out.println("Thread: " + Thread.currentThread().getName() + " is passing over the Bridge..., Traffic: " + traffic);
				Thread.sleep(1000); //Wait for 10 seconds
				//traffic = false; //Traffic has passed
				System.out.println("Thread: " + Thread.currentThread().getName() + " has successfully passed over the Bridge!, Traffic: " + traffic);
				notifyAll();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void goEast() {
		
	}
	
	public void goSouth(int height) {
		
		if(height < CLEARANCE) {
			try {
				System.out.println("Thread: " + Thread.currentThread().getName() + " (type small) is passing under the Bridge...");
				Thread.sleep(3000); //Wait for 30 seconds
				System.out.println("Thread: " + Thread.currentThread().getName() + " (type small) successfully passed under the Bridge!");
				return;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		synchronized(this) {
			if(height >= CLEARANCE) {
				while(traffic || state != State.OPEN) {
					try {
						System.out.println("Thread: " + Thread.currentThread().getName() + " is over the Clearance size and is WAITING...");
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}	
				}
				//open();
				try {
					System.out.println("Thread: " + Thread.currentThread().getName() + " (above Clearance) is passing through the Bridge...");
					Thread.sleep(3000); //Wait for 30 seconds
					System.out.println("Thread: " + Thread.currentThread().getName() + " (above Clearance) has successfully passed through the Bridge!");
					notifyAll();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void goNorth(int height) {
		
	}
}
