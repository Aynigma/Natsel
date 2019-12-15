package tests;

import sql.Population;
import sql.PopulationRules;
import sql.Rules;
import sql.Simulation;
import sql.Turns;

public class Upload {

	public Upload() {
		//uploadregle("regle5", "Une regle peut en cacher une autre");
		//uploadpop("tutu", "une autre population...");
		//uploadpopregle("toto", "regle2");
		//uploadsim("simu3", "il y a pas que les italiens qui simulent");
		uploadPopulationTurn(6, "toto", 230, "simu1");
		uploadPopulationTurn(6, "tata", 5, "simu1");
		

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Upload();

	}

//permet d'insérer une population ainsi que le nombre de départ
	public void uploadPopulation(String nom, String pop) {
		Population p = new Population();
		p.uploadPopulation(nom, pop);

	}

//permet d'uploader une regle ainsi qu'une description
	public void uploadRules(String nom, String desc) {
		Rules r = new Rules();
		r.uploadRegle(nom, desc);
	}

//permet d'uploader la jointure d'une regle et d'une pop
	public void uploadPopulationRules(String pop, String regle) {
		PopulationRules r = new PopulationRules();
		r.uploadLinkPopulationRule(pop, regle);
	}
	//permet d'uploader une nouvelle siulation
	public void uploadSimulation(String sim, String desc) {
		Simulation s = new Simulation();
		s.uploadSimulation(sim, desc);
	}
	//permet d'uploader un noouveau tour dans une simulation
	public void uploadPopulationTurn(int simTurn, String pop, int nbPop, String sim) {
		Turns t = new Turns();
		t.uploadPopulationTurn(simTurn, pop, nbPop, sim);
	}
}
