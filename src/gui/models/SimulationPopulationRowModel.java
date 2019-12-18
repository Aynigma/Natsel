package gui.models;

import sim.Pop;

/**
 * This model Class is meant to represent population data during their creation.
 * @author Aynigma
 */
public class SimulationPopulationRowModel {
	
	private int quantity;
	private String name;
	private String description;
	
	private Pop population;
	
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
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
	
	public SimulationPopulationRowModel(int quantity, Pop population) {
		super();
		this.quantity = quantity;
		this.name = population.getName();
		this.description = population.getDescription();
		this.setPopulation(population);
	}
	
	public String toString() {
		return ""+quantity+" : "+name+" :\n "+description+".";
	}
	public Pop getPopulation() {
		return population;
	}
	public void setPopulation(Pop population) {
		this.population = population;
		this.name = population.getName();
		this.description = population.getDescription();
	}
	

}
