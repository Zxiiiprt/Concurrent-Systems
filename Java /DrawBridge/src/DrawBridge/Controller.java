/**
 * 
 */
package DrawBridge;
/**
 * @author zxiiiprt
 *
 */
 
public class Controller implements Runnable {

	private Drawbridge bridge;
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Thread controller1, controller2, controller3; 
		Thread car1, car2, car3, car4, car5, car6, car7, car8, car9, car10; 
		Thread boat1, boat2, boat3, boat4, boat5, boat6, boat7, boat8, boat9, boat10;
		Drawbridge bridge = new Drawbridge();
		
		controller1 = new Thread(new Controller(bridge), "Controller1");
		//controller2 = new Thread(new Controller(bridge), "Controller2");
		//controller3 = new Thread(new Controller(bridge), "Controller3");
		
		car1 = new Thread(new Car(bridge), "Car1");
		car2 = new Thread(new Car(bridge), "Car2");
		car3 = new Thread(new Car(bridge), "Car3");
		car4 = new Thread(new Car(bridge), "Car4");
		car5 = new Thread(new Car(bridge), "Car5");
		car6 = new Thread(new Car(bridge), "Car6");
		car7 = new Thread(new Car(bridge), "Car7");
		car8 = new Thread(new Car(bridge), "Car8");
		car9 = new Thread(new Car(bridge), "Car9");
		car10 = new Thread(new Car(bridge), "Car10");
		
		boat1 = new Thread(new Boat(bridge, 5), "Boat1");
		boat2 = new Thread(new Boat(bridge, 14), "Boat2");
		boat3 = new Thread(new Boat(bridge, 50), "Boat3");
		boat4 = new Thread(new Boat(bridge, 221), "Boat4");
		boat5 = new Thread(new Boat(bridge, 5), "Boat5");
		boat6 = new Thread(new Boat(bridge, 23), "Boat6");
		boat7 = new Thread(new Boat(bridge, 14), "Boat7");
		boat8 = new Thread(new Boat(bridge, 41), "Boat8");
		boat9 = new Thread(new Boat(bridge, 50), "Boat9");
		boat10 = new Thread(new Boat(bridge, 5), "Boat10");
		
		controller1.start();
		//controller2.start();
		//controller3.start();
		
		car1.start();
		car2.start();
		car3.start();
		car4.start();
		car5.start();
		car6.start();
		car7.start();
		car8.start();
		car9.start();
		car10.start();
		
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
	
	public Controller(Drawbridge b) {
		this.bridge = b;
	}
	
	@Override
	public void run() {
		//bridge.open();
		try {
			Thread.sleep(1000);
			bridge.open();
			Thread.sleep(100);
			bridge.close();
			Thread.sleep(500);
			bridge.open();
			Thread.sleep(100);
			bridge.close();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	static class Car implements Runnable {
		
		private Drawbridge bridge;
		
		public Car(Drawbridge b) {
			this.bridge = b;
		}
		
		@Override
		public void run() {
			//bridge.goEast();
			bridge.goWest();
		}
	}
	
	static class Boat implements Runnable {
		
		private Drawbridge bridge;
		public int height;
		
		public Boat(Drawbridge b, int h) {
			this.bridge = b;
			this.height = h;
		}
		@Override
		public void run() {
			//bridge.goNorth(height);
			try {
				Thread.sleep(2000);
				bridge.goSouth(height);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		public int getHeight() {
			return this.height;
		}
	}
}
