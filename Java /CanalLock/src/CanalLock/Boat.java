/**
 * 
 */
package CanalLock;

/**
 * @author zxiiiprt
 *
 */
public class Boat implements Runnable {

	private CanalLock canalLock;
	
	public Boat (CanalLock canalLock) {
		this.canalLock = canalLock;
	}
	
	public void run() {
		canalLock.pass(); //Boat Thread invokes the pass() method to pass through the Lock
	}
}
