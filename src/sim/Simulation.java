package sim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.AbstractMap.SimpleImmutableEntry;

import gui.Window;
import gui.controllers.SimulationController;
import gui.models.SimulationPopulationRowModel;
import gui.models.TurnDataRowModel;

import java.util.Collections;

public class Simulation
{
	private String name;
	private String description;
	private SimulationController controller;
	
	public Simulation(String name, String description)
	{
		this.name = name;
		this.description = description;
		this.iteration = 0;
	}
	
	private ArrayList<Pop> childPops = new ArrayList<Pop>();
		public void addChild(Pop newChild) {this.childPops.add(newChild);}
		
	private ArrayList<Pop> environment = new ArrayList<Pop>();
		public ArrayList<Pop> getEnvironment() {return this.environment;}
		public void addPop(Pop pop) {this.environment.add(pop);}
//		public void addPop(Pop pop, int instances)
//		{
//			for (int i = 0; i < instances; i++) 
//			{
//				this.environment.add(pop);
//			}
//		}
//		
		public float getFoodAmount(FoodType foodType)
		{
			float amount = 0;
			for(Pop pop : environment)
			{
				if(pop.isAlive() && pop.getFoodType() == foodType) amount += pop.getFoodValue();
			}
			return amount;
		}
		public float getFoodAmount(ArrayList<FoodType> foodTypes)
		{
			float amount = 0;
			for(FoodType foodType : foodTypes) amount += getFoodAmount(foodType);
			return amount;
		}
		
		public int popToDo()
		{
			int todo = 0;
			for(Pop pop : environment)
			{
				if(!pop.isDone() && pop.isAlive()) todo++;
			}
			return todo;
		}
		
		public int countPop(String popName)
		{
			int amount = 0;
			for (Pop pop : environment)
			{
				if(pop.getName() == popName) amount += 1;
			}
			return amount;
		}
		
		public void printPop(String popName)
		{
			int amount = 0;
			for (Pop pop : environment)
			{
				if(pop.getName() == popName) amount += 1;
			}
			System.out.println(popName + " : " + amount);
		}
		
		public void printAllPops()
		{
			HashMap<String, Integer> population = new HashMap<String, Integer>();
			for (Pop pop : environment)
			{
				if(pop.isAlive())
				{
					int newAmount = population.containsKey(pop.getName()) ? population.get(pop.getName()) + 1 : 1;
					population.put(pop.getName(), newAmount);
				}
			}
			
			for (HashMap.Entry<String, Integer> pair : population.entrySet()) System.out.println(pair.getKey() + " : " + pair.getValue());
			
			UpdateControllerTable();
		}
		
	public int iteration;
		public void iterate(boolean print)
		{
			iteration++;
			ArrayList<Pop> deadPops = new ArrayList<Pop>();
			Collections.shuffle(environment);
			while (popToDo() > 0)
			{	
				//System.out.println(popToDo());
				for(Pop pop : environment)
				{
					//System.out.println(pop + " " + pop.getEaten() + " " + pop.isAlive() + " " + pop.getName());
					if(pop.isAlive() && !pop.isDone()) pop.iterateStep();
					//Second check in case pop died after iterate()
					if(!pop.isAlive()) deadPops.add(pop);
				}
			}
			for(Pop deadPop : deadPops) environment.remove(deadPop);
			deadPops = new ArrayList<Pop>();
			for(Pop child : childPops) environment.add(child);
			childPops = new ArrayList<Pop>();
			for(Pop pop : environment) pop.Ready();
			if(print) 
			{
				System.out.println("Iteration " + iteration + "\n");
				printAllPops();
				System.out.println();
			}

		}
		public void iterate(int amount, boolean print)
		{
			if(print) 
			{
				System.out.println("Iteration " + iteration + "\n");
				printAllPops();
				System.out.println();
			}
			for(int i = 0; i < amount; i++) iterate(print);
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public void setController(SimulationController controller) {
			this.controller = controller;
		}
		
		public void UpdateControllerTable() {

			if(controller != null){
				ArrayList<SimulationPopulationRowModel> populations = Window.getInstance().getPopulations();
				int[] populationsCounts = new int[populations.size()];
				for (int i = 0; i < populationsCounts.length; i++) {
					populationsCounts[i] = countPop(populations.get(i).getName());
				}
				
				controller.addRow(new TurnDataRowModel(iteration, populationsCounts));
			}
		}
		
}
