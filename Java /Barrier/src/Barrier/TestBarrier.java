/**
 * 
 */
package Barrier;
/**
 * @author Aaditya Sharma
 *
 */
public class TestBarrier {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Thread t1, t2, t3, t4, t5, t6;
		Barrier b = new Barrier(5);
		
		t1 = new Thread(new nThread(b, 0), "Thread1");
		t2 = new Thread(new nThread(b, 200), "Thread2");
		t3 = new Thread(new nThread(b, 300), "Thread3");
		t4 = new Thread(new nThread(b, 400), "Thread4");
		t5 = new Thread(new nThread(b, 600), "Thread5");
		t6 = new Thread(new nThread(b, 900), "Thread6");
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		t6.start();
	}
	
	static class nThread implements Runnable {
		
		private Barrier b;
		private int time;
		
		public nThread(Barrier b, int delay) {
			this.b = b;
			this.time = delay;
		}
		
		@Override
		public void run() {
			boolean result = false;
			
			try {
				Thread.sleep(time);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			
			System.out.println(Thread.currentThread().getName() + " invoked Checkpoint");
			result = b.checkpoint();
			System.out.println(Thread.currentThread().getName() + " - invokes Checkpoint  and returns: " + result);
		}
	}
}
