/**
 * 
 */
package CanalLock;
/**
 * @author zxiiiprt
 *
 */
public class ControllerThread {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Main and Boat Threads
		
		//ControllerThread invokes canal.lower() and canal.raise() as necessary

		CanalLock canalLock = new CanalLock();
		
		Thread boat1, boat2, boat3, boat4, boat5, boat6, boat7, boat8, boat9, boat10;
		
		boat1 = new Thread(new Boat(canalLock), "Boat1");
		boat2 = new Thread(new Boat(canalLock), "Boat2");
		boat3 = new Thread(new Boat(canalLock), "Boat3");
		boat4 = new Thread(new Boat(canalLock), "Boat4");
		boat5 = new Thread(new Boat(canalLock), "Boat5");
		boat6 = new Thread(new Boat(canalLock), "Boat6");
		boat7 = new Thread(new Boat(canalLock), "Boat7");
		boat8 = new Thread(new Boat(canalLock), "Boat8");
		boat9 = new Thread(new Boat(canalLock), "Boat9");
		boat10 = new Thread(new Boat(canalLock), "Boat10");
		
		boat1.start();
		boat2.start();
		boat3.start();
		boat4.start();
		boat5.start();
		boat6.start();
		boat7.start();
		boat8.start();
		boat9.start();
		boat10.start();
	}

}
