package gui.models;

import java.util.Arrays;

/**
 * This model Class is meant to represent population quantities' data during each turn of the simulation.
 * @author Aynigma
 */
public class TurnDataRowModel {

	private int turn;


	private int[] populationQuantities;
	
	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public TurnDataRowModel(int turn, int...populationQuantities) {
		this.turn = turn;
		this.populationQuantities = populationQuantities;
	}
	
	public String toString() {
		return "turn : "+turn+", quantities : "+Arrays.toString(populationQuantities);
	}

	public int[] getPopulationQuantities() {
		return populationQuantities;
	}

	public void setPopulationQuantities(int[] populationQuantities) {
		this.populationQuantities = populationQuantities;
	}
	
}
