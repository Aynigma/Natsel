package tests;

import sim.*;
import rules.*;

public class TestSimulation 
{
	public static void main(String[] args) 
	{
		Simulation simulation = new Simulation(" "," ");
		
		for (int i = 0; i < 50; i++) 
		{
			Pop bati = new Pop("bati", "", simulation, FoodType.animal, 1);
			bati.addRule(new Eat(FoodType.vegetable, 1, 2));
			bati.addRule(new NeedFood(1, 50));
			bati.addRule(new Reproduce(2));
			simulation.addPop(bati);
		}
		
		for(int i = 0; i < 2; i++)
		{
			Pop malolo = new Pop("malolo", "", simulation, FoodType.animal, 1);
			malolo.addRule(new Eat(FoodType.animal, 1, 2));
			malolo.addRule(new NeedFood(1, 50));
			malolo.addRule(new Reproduce(2));
			simulation.addPop(malolo);
		}
			
		for (int i = 0; i < 100; i++) 
		{
			Pop sandwich = new Pop("sandwich", "", simulation, FoodType.vegetable, 1);
			sandwich.addRule(new Reproduce(0));
			simulation.addPop(sandwich);
		}
		
		simulation.iterate(10, true);
	}
}
