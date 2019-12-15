package tests;

import sql.Population;
import sql.PopulationRules;
import sql.Rules;
import sql.Simulation;
import sql.Turns;

public class View {

	public View() {
		viewPopulation(1);
		viewRule(1);
		viewPopulationRules("tata");
		viewSimulation(1);
		viewSimulationId("simu1");
		viewPopulationSimulation("simu1");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new View();
	}

	public void viewPopulation(int i) {
		Population p = new Population();
		p.viewPopulation(i);
	}

	public void viewRule(int i) {
		Rules r = new Rules();
		r.viewRules(i);

	}

	public void viewPopulationRules(String i) {
		PopulationRules r = new PopulationRules();
		r.viewPopulationRules(i);

	}

	public void viewSimulation(int i) {
		Simulation s = new Simulation();
		s.viewSimulation(i);
	}

	public void viewSimulationId(String i) {
		Simulation s = new Simulation();
		System.out.println(s.viewSimulationId(i));
	}
	
	public void viewPopulationSimulation(String i) {
		Turns t = new Turns();
		t.viewPopulationSimulation(i);
	}
}
