/**
 * 
 */
package BoundedBuffer;
/**
 * @author zxiiiprt
 *
 */
public class BoundedBuffer {
	
	public static final int SIZE = 5;
	private Object[] buffer = new Object[SIZE];
	private int inIndex = 0, outIndex = 0, count = 0;
	
	private boolean writeable = true; //If true, there is room for at least one object in buffer
	private boolean readable = false; //If true, there is at least one object stored in buffer
	
	public synchronized void addEnd(Object item)
	{
		while(!writeable)
		{
			try {
				wait();
			} catch (InterruptedException e) {
				System.err.println(e);
			}
		}
		buffer[inIndex] = item;
		//System.out.println("Item added: " + item + ", Buffer inIndex: " + inIndex);
		readable = true;
		
		inIndex = (inIndex + 1) % SIZE;
		//System.out.println("inIndex after the MOD: " + inIndex);
		count++;
		
		if (count == SIZE) writeable = false;
		
		notifyAll();
	}
	
	public synchronized Object removeFirst()
	{
		Object item;
		
		while(!readable)
		{
			try {
				wait();
			} catch (InterruptedException e) {
				System.err.println(e);
			}
		}
		item = buffer[outIndex];
		//System.out.println("Item removed: " + item + ", Buffer outIndex: " + outIndex);
		writeable = true;
		
		outIndex = (outIndex + 1) % SIZE;
		//System.out.println("outIndex after the MOD: " + outIndex);
		count--;
		
		if (count == 0) readable = false;
		
		notifyAll();
		
		return item;
	}
	
	public int getCount()
	{
		return count;
	}
}
