/**
 * 
 */
package BoundedBuffer;

/**
 * @author zxiiiprt
 *
 */
public class ProducerConsumer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thread producer, consumer;
		BoundedBuffer buffer;
		
		buffer = new BoundedBuffer();
		
		producer = new Thread(new Producer(buffer), "Producer");
		consumer = new Thread(new Consumer(buffer), "Consumer");
		producer.start();
		consumer.start();
	}
	
	static class Producer implements Runnable
	{
		private BoundedBuffer buffer;
		
		public Producer(BoundedBuffer buf)
		{
			buffer = buf;
		}
		
		//@SuppressWarnings("deprecation")
		@Override
		public void run()
		{
			for(int i = 0; i < 10; i++)
			{
				int item = i;
				System.out.println(Thread.currentThread().getName() + " produced " + (item));
				buffer.addEnd(item);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					System.err.println(e);
				}
			}
		}
	}
	
	static class Consumer implements Runnable
	{
		private BoundedBuffer buffer;
		
		public Consumer (BoundedBuffer buf)
		{
			buffer = buf;
		}
		
		@Override
		public void run()
		{
			for(int i = 10; i > 0; i--)
			{
				Object item = buffer.removeFirst();
				System.out.println(Thread.currentThread().getName() + " consumed " + ((int)item));
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					System.err.println(e);
				}
			}
		}
	}
}
