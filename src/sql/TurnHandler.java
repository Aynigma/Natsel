package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
* This class enables to put in the database results of each turn of the simulation
* @author Baptiste
*/
public class TurnHandler {

	private int turn;
	private int populationNumber;
	private int SimulationId;
	private int populationId;

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public int getSimulationId() {
		return SimulationId;
	}

	public void setSimulationId(int simulationId) {
		this.SimulationId = simulationId;
	}

	public int getPopulationNumber() {
		return populationNumber;
	}

	public void setPopulationNumber(int populationNumber) {
		this.populationNumber = populationNumber;
	}

	public int getPopulationId() {
		return populationId;
	}

	public void setpopulationId(int populationId) {
		this.populationId = populationId;
	}

	/**
	 * This method return a table of turn data 
	 *
	 * @return String[][] getRestrictedTurnPopulation(ArrayList<String>, ArrayList<String>)
	 */
	public String[][] getNumberTurnPopulation() {
		ArrayList<String> numberTurn = new ArrayList<String>();
		ArrayList<String> population = new ArrayList<String>();

		try {
			Connection cn = DriverManager.getConnection(
					"jdbc:mysql://localhost/algo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "root");
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery("CALL affPopSim('" + getSimulationId() + "')");
			while (rs.next()) {
				numberTurn.add(rs.getString(1));
				population.add(rs.getString(2));
			}
			cn.close();
		} catch (SQLException e) {
			System.out.println(e);
		}

		return getRestrictedTurnPopulation(numberTurn, population);

	}
	/**
	 * Permet de retourner le nombre de tours et les différentes populations comprise dans la simulation
	 * en enlevant les doublons
	 *
	 * @return String[][] viewArraySimulation(ArrayList<String> ArrayList<String>)
	 */
	public String[][] getRestrictedTurnPopulation(ArrayList<String> inNumberTurn, ArrayList<String> inPopulation) {

		ArrayList<String> numberTurn = new ArrayList<String>();
		ArrayList<String> population = new ArrayList<String>();

		for (int i = 0; i < inNumberTurn.size(); i++) {
			if (!numberTurn.contains(inNumberTurn.get(i))) {
				numberTurn.add(inNumberTurn.get(i));
			}
		}

		for (int i = 0; i < inPopulation.size(); i++) {
			if (!population.contains(inPopulation.get(i))) {
				population.add(inPopulation.get(i));
			}
		}
		return viewArraySimulation(numberTurn, population);
	}

	/**
	 * Permet de retourner le tableau général d'une simulation
	 *
	 * @return String[][] tab
	 */
	
	public String[][] viewArraySimulation(ArrayList<String> inNumberTurn, ArrayList<String> inPopulation){
		String[][] tab = new String[inNumberTurn.size()+1][inPopulation.size()+1];
		int numberPopulationLength = tab.length;
		int numberTurnLength = tab[0].length;
		for (int line = 0; line<numberPopulationLength; line++)
		{
			for(int column = 0 ; column<numberTurnLength ; column++)
			{
				if(line==0 && column==0)
				{
					tab[line][column] = " ";
				}
				else if(line==0)
				{
					tab[line][column] = inPopulation.get(column-1);
				}
				else if(column==0)
				{
					tab[line][column] = inNumberTurn.get(line-1);
				}
				else
				{
					try {
						Connection cn = DriverManager.getConnection(
								"jdbc:mysql://localhost/algo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
								"root", "root");
						Statement st = cn.createStatement();
						ResultSet rs = st.executeQuery("CALL AffPopQu('" + inNumberTurn.get(line-1) +"','"
								+ inPopulation.get(column-1) +"')");
						while (rs.next()){
							tab[line][column] = rs.getString(1);
						}
						cn.close();
					}
					catch (SQLException e) {
						System.out.println(e);
					}
				}
			}
		}

		for (int line = 0; line<numberPopulationLength; line++)
		{
			for(int column = 0 ; column<numberTurnLength ; column++)
			{
				System.out.print(tab[line][column] + " ");
			}
			System.out.println();
		}
		return tab;
	}

	/**
	 * Permet de placer dans la base de donnée un nouveau tour et l'aspect de la
	 * simulation
	 *
	 * @return String = réussi
	 */
	public String setPopulationTurn() {
		try {
			Connection cn = DriverManager.getConnection(
					"jdbc:mysql://localhost/algo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "root");
			Statement st = cn.createStatement();
			st.executeUpdate("CALL insertPopSim ('" + getPopulationNumber() + "','" + getPopulationId() + "','" + getTurn() + "','"
					+ getSimulationId() + "')");
			cn.close();
		} catch (SQLException e) {
			System.out.println(e);
		}

		return "réussi";
	}

	/**
	 * Permet de savoi si un tours existe dans la base de donnée 
	 *
	 * @return String = réussi
	 */

	
	public boolean tryTurn() {
		try {
			int s = 0;
			Connection cn = DriverManager.getConnection(
					"jdbc:mysql://localhost/algo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "root");
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery("SELECT idSimulationTurn FROM simulationturn WHERE  turn=" + getTurn()
					+ " AND idSimulation = " + getSimulationId() + "");
			while (rs.next()) {
				s++;
			}
			if (s < 1) {
				st.executeUpdate("INSERT INTO `simulationturn`(`turn`, `idSimulation`) VALUES ('" + getTurn() + "','"
						+ getSimulationId() + "');");
			}
			cn.close();
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}

		return true;
	}

	/**
	 * This method puts in the database data about a turn
	 *
	 * @return String = réussi
	 */

	
	public void uploadPopulationTurn(int simulationTurn, String population, int populationNumber, String simulation) {

		setPopulationNumber(populationNumber);
		SimulationHandler s = new SimulationHandler();
		int SimulationId = s.viewSimulationId(simulation);
		PopHandler p = new PopHandler();
		int populationId = p.viewPopulationId(population);
		setpopulationId(populationId);
		setTurn(simulationTurn);
		setSimulationId(SimulationId);
		tryTurn();
		setPopulationTurn();

	}
	
	/**
	 * Permet d'afficher un tableau de toute la simuation
	 *
	 * @return String = réussi
	 */


	public void viewPopulationSimulation(String simulation) {

		SimulationHandler s = new SimulationHandler();
		int simulationId = s.viewSimulationId(simulation);
		setSimulationId(simulationId);
		getNumberTurnPopulation();

	}

}
