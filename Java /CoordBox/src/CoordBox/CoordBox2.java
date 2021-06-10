/**
 * 
 */
package CoordBox;
/**
 * @author zxiiiprt
 *
 */
public class CoordBox2 {
	private Object contents = null; //initialize contents to be null
    private boolean empty = true; //initialize box to be empty
    private boolean waiting = false; //count number of consumers waiting

    /**
     * Puts an object into the box. Does not return until a consumer
     * arrives to consume (get()).
     *
     * @param item The Object to  be put in the box
     * @return true after an object is put in the box
     */
    public synchronized Object put(Object item){
        while(!empty){ //if box is not empty enter busy waiting loop
            try{
                wait(); //wait until box is empty
            } catch (InterruptedException e) {
                return false;
            }
        }
/**
        //if reached this point then the box is empty
        if(waiting){ //if a consumer is waiting
            contents = item; //put item in box
            System.out.println("Contents after PUT: " + contents + ", From Thread: " + Thread.currentThread().getName());
            empty = false; //set box boolean to false
            notifyAll();
            return true;
        }else{ //no consumer is waiting
            while(!waiting){ //busy waiting loop
                try{
                    wait(); //wait until a consumer is waiting
                } catch (InterruptedException e) {
                    return false;
                }
            }
        }
**/		
        while(!waiting) {
        	try {
        		wait();
        	} catch (InterruptedException e) {
        		return false;
        	}
        }
        contents = item;
        System.out.println("Contents after PUT: " + contents + ", From Thread: " + Thread.currentThread().getName());
        empty = false; //set box boolean to false
        waiting = false;
        notifyAll();
        return true;
    }

    /**
     * Takes an Object from the Box. Returns the item taken form the box.
     * Returns null if other consumers are waiting.
     *
     * @return the item that taken out of the box, or null if other consumers are waiting
     */
    public synchronized Object get(){
        if(waiting){ //other consumers are already waiting
        	System.out.println("Waiting Requests: " + waiting + ", From Thread: " + Thread.currentThread().getName());
            return null;
        }else { //we are the first consumer waiting, therefore we can continue to wait
            waiting = true;
        }

        while(empty){ //if the box is empty enter busy waiting loop
            try{
                wait(); //wait until loop condition is not true
            } catch (InterruptedException e) {
                return null; //catch exception that could be thrown by wait()
            }
        }

        //if reached this point, the box is not empty
        Object item = contents; //copy box contents to item
        contents = null;  //empty box's contents
        System.out.println("Contents after GET: " + contents + ", From Thread: " + Thread.currentThread().getName());
        empty = true; //set box boolean to empty
        waiting = false; //decrement the number of consumers waiting
        notifyAll();
        return item;
    }
}
