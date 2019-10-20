package rules;

import sim.*;
import java.util.ArrayList;

public class Eat extends Rule
{
	public Eat(FoodType newEdible, float newMaxEaten)
	{
		this.edibles.add(newEdible);
		this.maxEaten = newMaxEaten;
	}
	public Eat(ArrayList<FoodType> newEdibles, float newMaxEaten)
	{
		this.edibles = newEdibles;
		this.maxEaten = newMaxEaten;
	}
	
	public ArrayList<FoodType> edibles = new ArrayList<FoodType>();
		public float getEdibleAmount()
		{
			int amount = 0;
			for(Pop other : this.getSimulation().getEnvironment())
			{
				if(edibles.contains(other.getFoodType()) & other.getName() != this.getPop().getName() & other.isAlive())
				{
					amount++;
				}
			}
			return amount;
		}
		
	public float maxEaten;
	
	@Override
	public void behave() 
	{
		while(this.getPop().getEaten() < this.maxEaten & getEdibleAmount() > 0)
		{
			for(Pop other : this.getSimulation().getEnvironment())
			{
				if(edibles.contains(other.getFoodType()) & other.getName() != this.getPop().getName() & other.isAlive()) 
				{
					this.getPop().eat(other.getFoodValue());
					other.kill();
				}
				if(this.getPop().getEaten() >= this.maxEaten) break;
			}
		}
	}
}
