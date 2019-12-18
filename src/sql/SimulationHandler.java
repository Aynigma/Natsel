package sql;

import java.sql.*;

/**
 * This class handles exchanges between database and the simulation
 * @author Baptiste
 */

public class SimulationHandler {
	private int simulationId;
	private String simulationName;
	private String description;

	public int getSimulationId() {
		return simulationId;
	}

	public void setSimulationId(int SimulationId) {
		this.simulationId = SimulationId;
	}

	public String getSimulationName() {
		return simulationName;
	}

	public void setSimulationName(String simulationName) {
		this.simulationName = simulationName;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * This method return name and description of the simulation through it's id
	 *
	 * @return simulation name and description : String
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
				setSimulationName(rs.getString(1));
				setDescription(rs.getString(2));
			}
			cn.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		System.out.println(getSimulationName() + " " + getDescription());

		return getSimulationName() + getDescription();

	}

	/**
	 * This method return id of the simulation through it's name
	 *
	 * @return id : int
	 */

	public int viewSimulationId() {
		try {
			Connection cn = DriverManager.getConnection(
					"jdbc:mysql://localhost/algo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "root");
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery("SELECT idSimulation FROM `simulation` where name='" + getSimulationName() + "'");
			while (rs.next()) {
				setSimulationId(rs.getInt(1));
			}
			cn.close();
		} catch (SQLException e) {
			System.out.println(e);
		}

		return getSimulationId();
	}

	/**
	 * This method put in the database this simulation
	 *
	 * @return "réussi" : String
	 */

	public String setSimulation() {
		try {
			Connection cn = DriverManager.getConnection(
					"jdbc:mysql://localhost/algo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "root");
			Statement st = cn.createStatement();
			st.executeUpdate("INSERT INTO `simulation`(`name`, `description`) VALUES ('" + getSimulationName() + "','"
					+ getDescription() + "')");
			cn.close();
		} catch (SQLException e) {
			System.out.println(e);
		}

		return "réussi";
	}

	/**
	 * This method put in the database a simulation
	 *
	 * @param simulationName : String
	 * @param desccription : String
	 */

	public void uploadSimulation(String simulationName, String desccription) {
		setSimulationName(simulationName);
		setDescription(desccription);
		setSimulation();
	}

	/**
	 * This method prints results of found simulation through it's id
	 * @param simulationId : int
	 */

	public void viewSimulation(int simulationId) {
		setSimulationId(simulationId);
		viewSimulation();

	}

	/**
	 * This method prints results of found simulation through it's name
	 * @param name : String
	 */

	public int viewSimulationId(String name) {
		setSimulationName(name);
		return viewSimulationId();
	}

}
