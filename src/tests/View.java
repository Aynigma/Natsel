package tests;

import sql.PopHandler;
import sql.PopulationRules;
import sql.RulesHandler;
import sql.SimulationHandler;
import sql.TurnsHandler;

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
		PopHandler p = new PopHandler();
		p.viewPopulation(i);
	}

	public void viewRule(int i) {
		RulesHandler r = new RulesHandler();
		r.viewRules(i);

	}

	public void viewPopulationRules(String i) {
		PopulationRules r = new PopulationRules();
		r.viewPopulationRules(i);

	}

	public void viewSimulation(int i) {
		SimulationHandler s = new SimulationHandler();
		s.viewSimulation(i);
	}

	public void viewSimulationId(String i) {
		SimulationHandler s = new SimulationHandler();
		System.out.println(s.viewSimulationId(i));
	}
	
	public void viewPopulationSimulation(String i) {
		TurnsHandler t = new TurnsHandler();
		t.viewPopulationSimulation(i);
	}
}
