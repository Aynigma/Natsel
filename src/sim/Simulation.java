package sim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.AbstractMap.SimpleImmutableEntry;

import gui.Window;
import gui.controllers.SimulationController;
import gui.models.SimulationPopulationRowModel;
import gui.models.TurnDataRowModel;

import java.util.Collections;

/**
 * Object representing a given environment to simulate. Contains collections of Pops and calls their interactions through "iterations".
 * @author Thomas_Florent
 * @see {@link #iterate(boolean)}
 * @see {@link sim.Pop}
 */
public class Simulation
{
	private String name;		
		public String getName() {return name;}
		public void setName(String name) {this.name = name;}
	private String description;
		public String getDescription() {return description;}
		public void setDescription(String description) {this.description = description;}
	private SimulationController controller;
		public void setController(SimulationController controller) {this.controller = controller;}
	
	public Simulation(String name, String description)
	{
		this.name = name;
		this.description = description;
		this.iteration = 0;
	}
	
	/**
	 * Pops that were spawned during the iteration. 
	 * They are considered "children", and thus separated the environment until the next iteration.
	 */
	private ArrayList<Pop> childPops = new ArrayList<Pop>();
		public void addChild(Pop newChild) {this.childPops.add(newChild);}
		
	/**
	 * ArrayList containing the Pops currently present in the simulation.
	 * @see {@link sim.Pop}
	 */
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
		/**
		 * @param foodType The {@link FoodType} to look for.
		 * @return The amount of Pops with the given FoodType present in the environment.
		 * Used to prevent infinite loops where a Pop would endlessly look for food that isn't there.
		 * @see {@link sim.FoodType}
	+	 * @see {@link rules.Eat}
		 */
		public float getFoodAmount(FoodType foodType)
		{
			float amount = 0;
			for(Pop pop : environment)
			{
				if(pop.isAlive() && pop.getFoodType() == foodType) amount += pop.getFoodValue();
			}
			return amount;
		}
		
		/**
		 * Overloads {@link #getFoodAmount(FoodType)}, but with an ArrayList of FoodTypes instead.
		 * @param foodTypes ArrayList containing the {@link FoodType}s to look for. 
		 * @see {@link #getFoodAmount(FoodType)}
		 * @see {@link FoodType}
		 * @see {@link rules.Eat}
		 */
		public float getFoodAmount(ArrayList<FoodType> foodTypes)
		{
			float amount = 0;
			for(FoodType foodType : foodTypes) amount += getFoodAmount(foodType);
			return amount;
		}
		
		/**
		 * Iterate through the environment and count the number of active and alive Pops.
		 * @return The number of active and alive Pops in the environment.
		 * @see {@link Pop#isAlive()}
		 * @see {@link Pop#isDone()}
		 */
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
		
		/**
		 * Iterate through the environment and print the number of instances of a given Pop.
		 * @param popName Name of the Pop that we want to count the instances of.
		 * @see {@link #printAllPops()}
		 */

		public void printPop(String popName)
		{
			int amount = 0;
			for (Pop pop : environment)
			{
				if(pop.getName() == popName) amount += 1;
			}
			System.out.println(popName + " : " + amount);
		}
		
		/**
		 * Print the number of instances of all the Pops present in the environment.
		 * @see {@link #printPop(String)}
		 */
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
		/**
		 * Iterate the simulation a single time. 
		 * The environment will be shuffled, then the algorithm will go through each Pop, 
		 * check if they are alive and active, then call a {@link Pop#iterateStep()}.
		 * It will continue until all pops in the environment are either inactive (done == true) or dead.
		 * @param print Whether or not to print the results.
		 * @see {@link Pop#iterateStep()}
		 */
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
		
		/**
		 * Iterate the simulation multiple times.
		 * @param amount How many times to iterate.
		 * @param print Whether or not to print the results.
		 * @see {@link #iterate(boolean)}
		 */
		public void iterate(int amount, boolean print)
		{
			sql.TurnHandler turnHandler = new sql.TurnHandler();
			if(iteration == 0) 
			{
				for (Pop pop : getEnvironment()) 
				{
					turnHandler.uploadPopulationTurn(iteration, pop.getName(), countPop(pop.getName()), this.getName());
				}
				if(print)
				{
					System.out.println("Iteration " + iteration + "\n");
					printAllPops();
					System.out.println();
				}
			}
			
			
			
			for(int i = 0; i < amount; i++) 
			{
				iterate(print);
				for (Pop pop : getEnvironment()) 
				{
					turnHandler.uploadPopulationTurn(iteration, pop.getName(), countPop(pop.getName()), this.getName());
				}
				
			}
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

