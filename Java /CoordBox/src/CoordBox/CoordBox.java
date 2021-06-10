/**
 * 
 */
package CoordBox;

/**
 * @author zxiiiprt
 *
 */
public class CoordBox {

	private Object contents = null;
	private boolean empty = true;//, wait = false;
	private int pcount = 0, gcount = 0;
	/**
	 * @param args
	 */
	
	public boolean put(Object obj) {
		synchronized(this) {
			while(!empty) {
				try{
					wait();
				} catch (InterruptedException e) {
					return false;
				}
			}
			pcount++;
			System.out.println("Put Count: " + pcount + ", From Thread: " + Thread.currentThread().getName());
			if(pcount > 1) {
				System.out.println("Put Count EXCEEDED: " + pcount + ", From Thread: " + Thread.currentThread().getName());
				pcount--;
				System.out.println("Put Count After Decrement: " + pcount + ", From Thread: " + Thread.currentThread().getName());
				return false;
			}
			while(pcount != gcount) {
				try {
					wait();
				} catch (InterruptedException e) {
					return false;
				}
			}
			contents = obj;
			System.out.println("Contents after PUT: " + contents + ", From Thread: " + Thread.currentThread().getName());
			empty = false;
			notifyAll();
			return true;
		}
	}
	
	public Object get() {
		synchronized(this) {
			gcount++;
			System.out.println("Get Count: " + gcount + ", From Thread: " + Thread.currentThread().getName());
			if(gcount > 1) {
				System.out.println("Get Count EXCEEDED: " + gcount + ", From Thread: " + Thread.currentThread().getName());
				gcount--;
				System.out.println("Get Count After Decrement: " + gcount + ", From Thread: " + Thread.currentThread().getName());
				return null;
			}
			while(empty) {
				try {
					wait();
				} catch (InterruptedException e) {
					return null;
				}
			}
			Object item = contents;
			contents = null;
			System.out.println("Contents after GET: " + contents + ", From Thread: " + Thread.currentThread().getName());
			empty = true;
			notifyAll();
			return item;	
		}
	}
}
