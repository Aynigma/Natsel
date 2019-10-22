package sim;
import java.util.ArrayList;
import rules.*;

public class Pop 
{
	public Pop(String newName, Simulation newSimulation, FoodType newFoodType, int newFoodValue)
	{
		this.name = newName;
		this.simulation = newSimulation;
		this.foodType = newFoodType;
		this.foodValue = newFoodValue;
		this.eaten = 0;
		this.alive = true;
		this.done = false;
		this.step = 0;
	}
	
	
	
	private String name;
		public String getName() {return this.name;}
		
	private Simulation simulation;
		public Simulation getSimulation() {return this.simulation;}
		//public void setSimulation(Simulation parentSimulation) {this.simulation = parentSimulation;}
	
	private FoodType foodType;
		public FoodType getFoodType() {return this.foodType;}
		
	private int foodValue;
		public int getFoodValue() {return this.foodValue;}
		
	private boolean alive;
		public boolean isAlive() {return this.alive;}
		public void kill() {if(this.alive) this.alive = false;}
	
	private float eaten;
		public float getEaten() {return this.eaten;}
		public void eat(float amount) {this.eaten += amount;}
	
	private int step;
		public int getStep() {return this.step;}
		
	private boolean done;
		public boolean isDone() {return this.done;}
		private void Done() {this.done = true;}
		public void Ready()
		{
			this.eaten = 0;
			this.step = 0;
			this.done = false;
		}
		
	private ArrayList<Rule> rules = new ArrayList<Rule>();
		public ArrayList<Rule> getRules() {return rules;}
		public void addRule(Rule rule) 
		{
			rule.setPop(this); 
			rule.setSimulation(simulation);
			rules.add(rule);
		}
		public void addRules(ArrayList<Rule> newRules) 
		{
			for (Rule rule : newRules) 
			{
				rule.setPop(this);
				rule.setSimulation(simulation);
			}
			rules.addAll(newRules);
		}
	
	public void iterateStep()
	{
		//System.out.println(name + " " + this + " " + isAlive() + " " + step);
		if(rules.get(step).behave()) step++;
		if(rules.size() <= step) Done();
	}
}