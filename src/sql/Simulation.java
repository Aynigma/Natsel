package sql;

import java.sql.*;

/**
 * Classe gèrant l'esemble des appels de la base de donnée liée aux simulations
 *
 * @author Baptiste
 */

public class Simulation {
	private int simulationId;
	private String nameSimulation;
	private String description;

	/**
	 * @return the id_sim : Int
	 */
	public int getSimulationId() {
		return simulationId;
	}

	/**
	 * @param SimulationId : Int the id_sim to set
	 */
	public void setSimulationId(int SimulationId) {
		this.simulationId = SimulationId;
	}

	/**
	 * @return the nomsim : String
	 */
	public String getNameSimulation() {
		return nameSimulation;
	}

	/**
	 * @param nameSimulation : String the nomsim to set
	 */
	public void setNameSimulation(String nameSimulation) {
		this.nameSimulation = nameSimulation;
	}

	/**
	 * @return the description : String
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description : String the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Permet de retourner le nom et la description avec l'id de la simulation
	 *
	 * @return Arraylist String
	 */
	public String viewSimulation() {

		try {
			Connection cn = DriverManager.getConnection(
					"jdbc:mysql://localhost/algo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "root");
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(
					"SELECT name, description FROM `simulation` where idSimulation='" + getSimulationId() + "'");
			while (rs.next()) {
				setNameSimulation(rs.getString(1));
				setDescription(rs.getString(2));
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		System.out.println(getNameSimulation() + " " + getDescription());

		return getNameSimulation() + getDescription();

	}

	/**
	 * Permet de retrourner l'id de la simulation grâce à son nom
	 *
	 * @return Int id
	 */

	public int viewSimulationId() {
		try {
			Connection cn = DriverManager.getConnection(
					"jdbc:mysql://localhost/algo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "root");
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery("SELECT idSimulation FROM `simulation` where name='" + getNameSimulation() + "'");
			while (rs.next()) {
				setSimulationId(rs.getInt(1));
			}
		} catch (SQLException e) {
			System.out.println(e);
		}

		return getSimulationId();
	}

	/**
	 * Permet de placer dans la base de donnée une nouvelle simulation
	 *
	 * @return String = réussi
	 */

	public String setSimulation() {
		try {
			Connection cn = DriverManager.getConnection(
					"jdbc:mysql://localhost/algo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "root");
			Statement st = cn.createStatement();
			st.executeUpdate("INSERT INTO `simulation`(`name`, `description`) VALUES ('" + getNameSimulation() + "','"
					+ getDescription() + "')");
		} catch (SQLException e) {
			System.out.println(e);
		}

		return "réussi";
	}

	/**
	 * Permet de placer dans la base de donnée une nouvelle simulation
	 *
	 * @param String nom
	 * @param Int    Description
	 */

	public void uploadSimulation(String simulationName, String desccription) {
		setNameSimulation(simulationName);
		setDescription(desccription);
		setSimulation();
	}

	/**
	 * Permet d'afficher le resulatat de la recherche avec l'id passer en paramètre
	 *
	 * @param Int id
	 */

	public void viewSimulation(int simulationId) {
		setSimulationId(simulationId);
		viewSimulation();

	}

	/**
	 * Permet d'afficher le resulatat de la recherche avec le nom passer en
	 * paramètre
	 *
	 * @param String nom
	 */

	public int viewSimulationId(String name) {
		setNameSimulation(name);
		return viewSimulationId();
	}

}
