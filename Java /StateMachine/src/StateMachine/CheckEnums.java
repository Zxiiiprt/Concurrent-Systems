package StateMachine;

public class CheckEnums {

	public static void main(String[] args) {
		
		String test = "ababac";
		CheckEnums c = new CheckEnums();
		System.out.println(c.checkEnum(test));
	}
	
	enum State {
		STATE1, STATE2, STATE3, INVALID
	}
	
	public boolean checkEnum(String str) {
		
		char[] array = str.toCharArray();
		State state = null;
		System.out.println("Char# 1 is: " + array[0]);
		
		if(array[0] == 'a') {
			state = State.STATE1;
			System.out.println("Current State: " + state);
			System.out.println("Char# 2 is: " + array[1]);
			
			if(array[1] == 'b') {
				state = State.STATE2;
				System.out.println("Current State: " + state);
				
				for(int i = 2; i < array.length; i++) {
					
					System.out.println("Char# " + (i+1) + " is: " + array[i]);
					
					if(array[i] == 'c') {
						state = State.STATE3;
						System.out.println("Current State: " + state);
						continue;
					}
					else if(array[i] == 'b') {
						state = State.STATE2;
						System.out.println("Current State: " + state);
						continue;
					}
					else if(array[i] == 'a') {
						state = State.STATE1;
						System.out.println("Current State: " + state);
						if(array[i+1] != 'b' && (i+1 <= array.length)) {
							System.out.println("Char# " + (i+2) + " is INVALID in our Sequence: " + array[i+1]);
							state = State.INVALID;
							System.out.println("Current State: " + state);
							break;
						}
						continue;
					}
					else {
						System.out.println("Char# " + (i+1) + " is INVALID in our Sequence: " + array[i]);
						state = State.INVALID;
						System.out.println("Current State: " + state);
						break;
					}
				}
			}
		}
		
		if(state == State.STATE3) {
			System.out.println("Final State: " + state);
			return true;
		}
		else {
			System.out.println("Final State: " + state);
			return false;
		}
	}
}