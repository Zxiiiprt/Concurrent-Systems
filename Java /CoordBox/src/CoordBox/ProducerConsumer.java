/**
 * 
 */
package CoordBox;

/**
 * @author zxiiiprt
 *
 */
public class ProducerConsumer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Thread producer1, producer2, producer3, consumer1, consumer2, consumer3;
		//CoordBox box = new CoordBox();
		CoordBox2 box2 = new CoordBox2();
		
		producer1 = new Thread(new Producer(box2), "Producer1");
		producer2 = new Thread(new Producer(box2), "Producer2");
		producer3 = new Thread(new Producer(box2), "Producer3");
		consumer1 = new Thread(new Consumer(box2), "Consumer1");
		consumer2 = new Thread(new Consumer(box2), "Consumer2");
		consumer3 = new Thread(new Consumer(box2), "Consumer3");
		producer1.start();
		consumer1.start();
		producer2.start();
		producer3.start();
		consumer3.start();
		consumer2.start();
		
	}
	
	static class Producer implements Runnable {
		
		private CoordBox2 box;
		
		public Producer(CoordBox2 b) {
			this.box = b;
		}
		
		@Override
		public void run() {
			int i = (int)Math.floor(Math.random()*10);
			System.out.println(Thread.currentThread().getName() + " is trying to PUT: " + i + " in the Box");
			box.put(i);	
		}
	}
	
	static class Consumer implements Runnable {
		
		private CoordBox2 box;
		
		public Consumer(CoordBox2 b) {
			this.box = b;
		}
		
		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName() + " is trying to Get from the Box");
			box.get();
		}
	}
}
