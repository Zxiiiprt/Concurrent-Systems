/**
 * 
 */
package StateMachine;
/**
 * @author Zxiiiprt
 *
 */
public class Check {
	
	/**
	 * The Check class tests the "States" of a given String, and is an implementation of a simple State Machine
	 * We essentially have 3 States in this State Machine - 'a', 'b', and 'c'
	 * State 'a' can only go to State 'b'
	 * State 'b' can transition to any of the 3 States - 'a', 'b', and 'c'
	 * If State 'c' is the last State in the String (and has been followed by a 'b'), then we have detected the Sequence
	 * In one manner, this is a type of Sequence Detector looking for the sequence "abc"
	 * We can have any number of 'b' after a 'b', but only one 'a' after a 'b'
	 * As stated earlier, if the sequence does not consist of a 'b' after an 'a' then we quit (return false)
	 */
	public static void main(String[] args) {
		
		String test = "aababababababababbbbbbbbbbbbbc";
		Check c = new Check();
		System.out.println(c.check(test));
	}
	
	/**
	 * Check for strings consisting of the letter `a' followed by one or more `b's followed by either an `a' or a `c'
	 * If the letter `a' is detected, the next letter that follows must be a `b', then any number of `b's, an `a' or a `c'
	 * Any time a `c' is found immediately after a `b', the method check() will return true
	 * Any letter other than an `a', `b' or `c'; or any acceptable letter that appears out of order, will cause the check() method to return false
	 * @param str : This is the String that is the input to detect/Check if our Sequence was valid or not
	 * @return : If Sequence is Detected, return true; else return false
	 */
	public boolean check(String str) {
		//Obtain the String and split it into an Array of Characters - store it in char[] state
		char[] state = str.toCharArray();
		//If the first Character of the String is an 'a', then we can proceed safely with our Sequence
		if(state[0] == 'a') {
			//Once we have entered this point we are in our first State 'a' => to continue the next element must be a 'b'
			//Loop through the rest of the Character Array from the second element of the Array until the length
			for(int i = 1; i < state.length; i++) {
				System.out.println("Char# " + (i+1) + " is: " + state[i]);
				//If the next element was Detected to be a 'b' THEN
				if(state[i] == 'b') {
					//At this point our Sequence has Detected "ab" so far
					//If the element Detected after the 'b' is an 'a' or a 'b' again, provided we are not going out of bounds THEN
					if((state[i+1] == 'a' || state[i+1] == 'b') && (i+1 < state.length)) {
						//Our Sequence has Detected "aba" or "abb" so far
						//Go back to the top of the loop and continue
						continue;
					}
					//If the next element after the 'b' was Detected to be a 'c' THEN
					else if(state[i+1] == 'c' && (i+2 == state.length)) {
						//At this point our Sequence has Detected "abc" so far
						//As long as the 'c' was followed by a 'b', provided that the 'c' was the final Character of our Input String
						//Return true: We have successfully Detected our Sequence
						return true;
					}
					else {
						//Our Sequence Detected an invalid Character or an invalid State Transition that defies our State Machine
						//Return false: This means that our Sequence was disrupted by a transition to an invalid state
						return false;
					}
				}
			}
		}
		//The input String DID NOT start with an 'a' or was introduced by an INVALID Character/State Transition
		//Return false: Our Sequence was disrupted by a transition to an invalid state
		return false;
	}
}
