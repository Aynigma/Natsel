package tests;

import sql.PopHandler;
import sql.PopulationRules;
import sql.RulesHandler;
import sql.SimulationHandler;
import sql.TurnHandler;



public class Upload {

	public Upload() {
		//uploadregle("regle5", "Une regle peut en cacher une autre");
		uploadPopulation("chocobkb", "encore une autre population...");
		//uploadpopregle("toto", "regle2");
		//uploadSimulation("skjfnakjfna", "il y a pas que les italiens qui simulent");
		//uploadPopulationTurn(7, "choco", 230, "simu1");
		//uploadPopulationTurn(0, "tata", 5, "simu1");
		

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Upload();

	}

//permet d'insérer une population ainsi que le nombre de départ
	public void uploadPopulation(String nom, String pop) {
		PopHandler p = new PopHandler();
		p.uploadPopulation(nom, pop);

	}

//permet d'uploader une regle ainsi qu'une description
	public void uploadRules(String nom, String desc) {
		RulesHandler r = new RulesHandler();
		r.uploadRegle(nom, desc);
	}

//permet d'uploader la jointure d'une regle et d'une pop
	public void uploadPopulationRules(String pop, String regle) {
		PopulationRules r = new PopulationRules();
		r.uploadLinkPopulationRule(pop, regle);
	}
	//permet d'uploader une nouvelle siulation
	public void uploadSimulation(String sim, String desc) {
		SimulationHandler s = new SimulationHandler();
		s.uploadSimulation(sim, desc);
	}
	//permet d'uploader un noouveau tour dans une simulation
	public void uploadPopulationTurn(int simTurn, String pop, int nbPop, String sim) {
		TurnHandler t = new TurnHandler();
		t.uploadPopulationTurn(simTurn, pop, nbPop, sim);
	}
}
