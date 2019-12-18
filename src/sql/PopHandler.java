package sql;

import java.sql.*;

/**
 * This class enable interraction between population and their representation in the database
 *
 * @author Baptiste
 */
public class PopHandler {

	private int popId;
	private String name;
	private String description;

	public int getPopId() {
		return popId;
	}

	public String getName() {return name;}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String desccription) {
		this.description = desccription;
	}

	public void setPopId(int popId) {
		this.popId = popId;
	}

	/**
	 * This method return population name and description as they are in the DataBase for this pop id
	 *
	 * @return name + description : String
	 */
	public String viewPopulation() {
		try {
			Connection cn = DriverManager.getConnection(
					"jdbc:mysql://localhost/algo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "root");
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(
					"SELECT name, description FROM `population` where idPopulation='" + getPopId() + "'");
			while (rs.next()) {
				setName(rs.getString(1));
				setDescription(rs.getString(2));
			}
			cn.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		System.out.println(getName() + " " + getDescription());

		return getName() + getDescription();
	}

	/**
	 * This method enables to recover population id through this population name
	 *
	 * @return popId : int
	 */
	public int viewPopulationId() {
		try {
			Connection cn = DriverManager.getConnection(
					"jdbc:mysql://localhost/algo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "root");
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery("SELECT idPopulation FROM `population` where name='" + getName() + "'");
			while (rs.next()) {setPopId(rs.getInt(1));}
			cn.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		return getPopId();
	}

	/**
	 * This method tries to put this population in the database.
	 *
	 * @return success : boolles
	 */
	private boolean setPopulation() {
		try {
			Connection cn = DriverManager.getConnection(
					"jdbc:mysql://localhost/algo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "root");
			Statement st = cn.createStatement();
			st.executeUpdate(
					"INSERT INTO `population`(`name`, `description`) VALUES ('" + getName() + "','" + getDescription() + "')");
			cn.close();
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}

		return true;
	}

	/**
	 * This method tries to put a population in the database
	 *
	 * @param name : String
	 * @param desccription : String
	 */
	public void uploadPopulation(String name, String desccription) {
		setName(name);
		setDescription(desccription);
		setPopulation();
	}

	/**
	 * This method prints the results of population search through it's id
	 *
	 * @param populationId : int
	 */
	public void viewPopulation(int populationId) {
		setPopId(populationId);
		viewPopulation();

	}

	/**
	 * This method prints the results of population search through it's name
	 *
	 * @param name : String
	 */
	public int viewPopulationId(String name) {
		setName(name);
		return viewPopulationId();
	}

}
