/**
 * 
 */
package CanalLock;
/**
 * @author zxiiiprt
 *
 */
public class CanalLock {

	public int waterLevel, gateUp, gateDown, numBoats;
	public boolean inTheLock = false; //Checking if all 4 boats are in the Lock or not
	
	public CanalLock() {
		this.waterLevel = 1; //1 = High water level, 0 = Low water level
		this.gateUp = 1; //1 = Upstream Gate is Open, 0 = Upstream gate is Closed
		this.numBoats = 0; //0 numBoats initially 
		this.gateDown = 0; //1 = Downstream Gate is Open, 0 = Downstream gate is Closed
	}
	
	public synchronized void lowerWater() {
		while(gateUp != 0 && !inTheLock) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		gateDown = 0; //Close Downstream Gate
		System.out.println("Gates are closed water level is being lowered");
		waterLevel = 1; //Lower the water level
		notifyAll();
	}
	
	public synchronized void raiseWater() {
		while(gateUp != 0 && !inTheLock) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		gateUp = 0; //Close the Upstream Gate
		System.out.println("Gates are closed water level is being raised");
		waterLevel = 1; //Raise water level
		notifyAll();
	}
	
	public synchronized void lower() {
		while(gateUp == 0 && numBoats != 4 && !inTheLock) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			System.out.println("Lowering the Canal Lock..");
			this.gateUp = 0; //Close the Upstream Gate
			lowerWater(); //Lower the water level
			this.gateDown = 1; //Open the Downstream Gate
			Thread.sleep(5000); //Lowering gate process take 5 minutes; here 5seconds is assumed to be equal to 5minutes
			System.out.println("Lock has been Lowered!");
			notifyAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public synchronized void raise() {
		while(!inTheLock && gateDown == 0 && numBoats != 4) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			System.out.println("Raising the Canal Lock...");
			this.gateDown = 0; //Close the Downstream Gate
			raiseWater(); //Raise water level
			this.gateUp = 1; //Open the Upstream Gate
			Thread.sleep(5000); //Raising gate process take 5 minutes; here 5seconds is assumed to be equal to 5minutes
			System.out.println("Lock has been Raised!");
			notifyAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void pass() {
		while(gateUp == 0 && numBoats >= 4 || (gateDown == 1)) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			numBoats++;
			System.out.println("Boat#: " + this.numBoats + " from Thread: " + Thread.currentThread().getName() + " is Entering the Lock");
			Thread.sleep(300);
			System.out.println("Boat#: " + this.numBoats + " from Thread: " + Thread.currentThread().getName() + " has Entered the Lock");
			if(this.numBoats == 4) {
				inTheLock = true; //4 Boats have entered the Lock
				System.out.print("4 Boats have entered the Lock, about to Lower the Gates...");
				lower();
				System.out.print("This is to check we safely returned from Lower()\n");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		while(!inTheLock) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if(numBoats > 0 && inTheLock) {
			try {
				System.out.println("Boat#: " + this.numBoats + " from Thread: " + Thread.currentThread().getName() + " is Leaving the Lock");
				Thread.sleep(300);
				numBoats--;
				System.out.println("Boat#: " + (this.numBoats+1) + " from Thread: " + Thread.currentThread().getName() + " has Left the Lock");
				if(this.numBoats == 0) { //Yeah idk, just wrecking my brain by this point
					inTheLock = false; //Boat has left the lock
					System.out.println("All 4 Boats have left the Lock");
					raise();
					System.out.print("This is to check we safely returned from Raise()\n");
					notifyAll();
					System.out.println("Everyone else was notified that Lock was Raised and all Boats have passed: Safe to enter again");
				}
			} catch (InterruptedException e) {
					e.printStackTrace();
			}
		 }
		//This is the part that I'm stuck on... Need to call rais() and decrement each boat one by one
		/***
		 * Something like this: but not really like this (wrote like a semi-logic but this is definitely not the "Right" way to do it) -
		 * while(inTheLock) {
			try {
				System.out.println("Boat#: " + this.numBoats + " from Thread: " + Thread.currentThread().getName() + " is Leaving the Lock");
				Thread.sleep(300);
				numBoats--;
				System.out.println("Boat#: " + (this.numBoats+1) + " from Thread: " + Thread.currentThread().getName() + " has Left the Lock");
				if(this.numBoats == 0) { //Yeah idk, just wrecking my brain by this point
					inTheLock = false; //Boat has left the lock
					System.out.println("All 4 Boats have left the Lock");
					raise();
					System.out.print("This is to check we safely returned from Raise()\n");
					notifyAll();
					System.out.println("Everyone else was notified that Lock was Raised and all Boats have passed: Safe to enter again");
				}
			} catch (InterruptedException e) {
					e.printStackTrace();
			}
		   }
		 */
		//return true;
	}
}

/**
public class CanalLock {

	public int waterLevel, gateUp, gateDown, numBoats;
	public boolean inTheLock = false; //Checking if all 4 boats are in the Lock or not
	
	
	public CanalLock() {
		this.waterLevel = 1; //1 = High water level, 0 = Low water level
		this.gateUp = 1; //1 = Upstream Gate is Open, 0 = Upstream gate is Closed
		this.numBoats = 0; //0 numBoats initially 
		this.gateDown = 0; //1 = Downstream Gate is Open, 0 = Downstream gate is Closed
	}
	
	public synchronized void lower() {
		if(this.numBoats <= 4 && inTheLock) {
			try {
				System.out.println("Lowering the Canal Lock..");
				this.gateUp = 0; //Close the Upstream Gate
				this.waterLevel = 0; //Lower the water level
				this.gateDown = 1; //Open the Downstream Gate
				Thread.sleep(5000); //Lowering gate process take 5 minutes; here 5seconds is assumed to be equal to 5minutes
				System.out.println("Lock has been Lowered!");
				return;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void raise() {
		if(!inTheLock && this.numBoats == 0) {
			try {
				System.out.println("Raising the Canal Lock...");
				this.gateDown = 0; //Close the Downstream Gate
				this.waterLevel = 1; //Raise water level
				this.gateUp = 1; //Open the Upstream Gate
				Thread.sleep(5000); //Raising gate process take 5 minutes; here 5seconds is assumed to be equal to 5minutes
				System.out.println("Lock has been Raised!");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public synchronized boolean pass() {
		numBoats++;
		while(this.gateUp == 0 || numBoats > 4) {
			try {
				System.out.println("Waiting for Upstream Gate to be Opened...");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if(this.gateUp == 1 && numBoats <= 4) {
			try {
				System.out.println("Boat#: " + this.numBoats + " from Thread: " + Thread.currentThread().getName() + " is Entering the Lock");
				Thread.sleep(300);
				System.out.println("Boat#: " + this.numBoats + " from Thread: " + Thread.currentThread().getName() + " has Entered the Lock");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if(this.numBoats == 4) {
			inTheLock = true; //4 or less Boats have entered the Lock
			System.out.print("4 Boats have entered the Lock, about to Lower the Gates...");
			lower();
			System.out.print("This is to check we safely returned from Lower()\n");
		}
		//if(numBoats == 4 && this.gateDown == 1 && inTheLock) { //Implies that gateUp = 0
		if(inTheLock) {
			try {
				System.out.println("Boat#: " + this.numBoats + " from Thread: " + Thread.currentThread().getName() + " is Leaving the Lock");
				Thread.sleep(300);
				numBoats--;
				System.out.println("Boat#: " + (this.numBoats+1) + " from Thread: " + Thread.currentThread().getName() + " has Left the Lock");
				if(this.numBoats == 0 && inTheLock) {
					inTheLock = false; //Boat has left the lock
					System.out.println("All 4 Boats have left the Lock");
					raise();
					System.out.print("This is to check we safely returned from Raise()\n");
					notifyAll();
					System.out.println("Everyone else was notified that Lock was Raised and all Boats have passed: Safe to enter again");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//}
		}
		return true;
	}
}
**/


/**
public synchronized boolean pass() {
numBoats++;
while(this.gateUp == 0 || numBoats > 4) {
	try {
		System.out.println("Waiting for Upstream Gate to be Opened...");
		wait();
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
}
if(this.gateUp == 1) {
	while(numBoats > 4) {
		try {
			System.out.println("Upstream Gate is Open but too many boats...");
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	//if(numBoats < 4) {
	try {
		//numBoats++;
		System.out.println("Boat#: " + this.numBoats + " from Thread: " + Thread.currentThread().getName() + " is Entering the Lock");
		Thread.sleep(300);
		inTheLock = true; //Boats have entered the Lock
		System.out.println("Boat#: " + this.numBoats + " from Thread: " + Thread.currentThread().getName() + " has Entered the Lock");
		lower();
		//System.out.print("This is to check where I'm landing");
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	//}
}
if(numBoats == 4 && this.gateUp ==1) {
	lower();
}
if(numBoats <= 4 && this.gateDown == 1) { //Implies that gateUp = 0
	//numBoats--;
	while(numBoats >= 0) {
		try {
			numBoats--;
			System.out.println("Boat#: " + (this.numBoats+1) + " from Thread: " + Thread.currentThread().getName() + " is Leaving the Lock");
			Thread.sleep(300);
			inTheLock = false; //Boat has left the lock
			System.out.println("Boat#: " + (this.numBoats+1) + " from Thread: " + Thread.currentThread().getName() + " has Left the Lock");
			raise();
			System.out.print("This is to check where I'm landing ");
			notifyAll();
			System.out.println("Everyone else was notified");
		} catch (InterruptedException e) {
		e.printStackTrace();
		}
	}
}
return true;
}
**/