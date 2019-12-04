package rules;

import sim.*;
import java.util.ArrayList;

public class Eat extends Rule
{
	public Eat(FoodType newEdible, float newMaxEatenOnce, float newMaxEaten)
	{
		this.edibles.add(newEdible);
		this.maxEatenOnce = newMaxEatenOnce;
		this.maxEaten = newMaxEaten;
	}
	public Eat(ArrayList<FoodType> newEdibles, float newMaxEatenOnce, float newMaxEaten)
	{
		this.edibles = newEdibles;
		this.maxEatenOnce = newMaxEatenOnce;
		this.maxEaten = newMaxEaten;
	}
	
	
	public Eat(Eat eat){
		this.edibles = eat.edibles;
		this.maxEatenOnce = eat.maxEatenOnce;
		this.maxEaten = eat.maxEaten;
	}
	
	public ArrayList<FoodType> edibles = new ArrayList<FoodType>();
		public float getEdibleAmount()
		{
			int amount = 0;
			for(Pop other : this.getSimulation().getEnvironment())
			{
				if(edibles.contains(other.getFoodType()) && other.getName() != this.getPop().getName() && other.isAlive())
				{
					amount++;
				}
			}
			return amount;
		}
		
	public float maxEatenOnce, maxEaten;
	
	@Override
	public boolean behave() 
	{
		float eatenOnce = 0;
//		System.out.println("eatenOnce : " + eatenOnce + " | maxEatenOnce : " + this.maxEatenOnce);
//		System.out.println(getEdibleAmount());
		while(eatenOnce < this.maxEatenOnce && getEdibleAmount() > 0)
		{
			for(Pop other : this.getSimulation().getEnvironment())
			{
				if(edibles.contains(other.getFoodType()) && other.getName() != this.getPop().getName() && other.isAlive()) 
				{
					//System.out.println(this.getPop().getEaten());
					//System.out.println("miam");
					this.getPop().eat(other.getFoodValue());
					eatenOnce += other.getFoodValue();
					//System.out.println(this.getPop().getEaten());
					other.kill();
				}
				if(eatenOnce >= this.maxEatenOnce) break;
			}
		}
		//System.out.println("eaten : " + this.getPop().getEaten() + " | maxEaten : " + this.maxEaten);
		if(this.getPop().getEaten() >= this.maxEaten || getEdibleAmount() == 0.0) return true;
		else return false;
	}
}
