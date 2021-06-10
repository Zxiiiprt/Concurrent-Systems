/**
 * 
 */
package ProducerConsumer;
/**
 * @author zxiiiprt
 *
 */
public class ProducerConsumer {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Thread Agent, Chef1, Chef2, Chef3;
		Table table;
		
		table = new Table();
		
		Agent = new Thread(new Agent(table), "Agent");
		Chef1 = new Thread(new Chef(table, 0), "Chef");
		Chef2 = new Thread(new Chef(table, 1), "Chef");
		Chef3 = new Thread(new Chef(table, 2), "Chef");
		Agent.start();
		Chef1.start();//PB
		Chef2.start();//Jam
		Chef3.start();//Bread
	}
	
	static class Agent implements Runnable
	{
		private Table table;
		
		public Agent(Table buf)
		{
			table = buf;
		}
		
		@Override
		public void run()
		{
			for(int i = 1; i <= table.MAX; i++)
			{
				int item = (int) Math.floor(Math.random()*3);
				System.out.println(Thread.currentThread().getName() + " produced " + i + " Sandwich, Item Type: " + table.agentStr[item] + "\n");
				table.addEnd(item);
				//System.out.println("COUNT after Add: " + table.getCount() + "\n");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					System.err.println(e);
				}
			}
		}
	}
	
	static class Chef implements Runnable
	{
		private Table table;
		private int ingredient;
		private String[] ingredientStr = {"Mr.PeanutButter", "Jams", "Bread"};
		
		public Chef (Table buf, int gredient)
		{
			table = buf;
			ingredient = gredient;
		}
		
		@Override
		public void run()
		{
			for(int i = 1; i <= table.MAX; i++)
			{
				Object first = table.getFirst();
				//System.out.println(Thread.currentThread().getName() + " Reads First: " + first);
				if((int)first == ingredient) {
					Object item = table.removeFirst();
					System.out.println(Thread.currentThread().getName() + " " + ingredientStr[this.getIngredient()] + ", consumed Sandwich#: " + table.getSCount() + ", Item Type: " + table.agentStr[(int)item] + "\n");
					//System.out.println("COUNT after Remove: " + table.getCount() + "\n");
					if (table.getSCount() == table.MAX) System.exit(0);
				}
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					System.err.println(e);
				}
			}
		}
		
		public int getIngredient()
		{
			return ingredient;
		}
	}
}
