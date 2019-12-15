package sql;

import java.sql.*;

/**
 * Classe gèrant l'esemble des appels de la base de donnée liée a la population
 *
 * @author Baptiste
 */
public class Population {

	private int populationId;
	private String name;
	private String description;

	/**
	 * @return the id_pop : int
	 */
	public int getPopulationId() {
		return populationId;
	}

	/**
	 * @return the nom : String
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name : String the nom to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description : String
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description : int the nbpop to set
	 */
	public void setDescription(String desccription) {
		this.description = desccription;
	}

	/**
	 * @param populationId : int the id_pop to set
	 */
	public void setPopulationId(int populationId) {
		this.populationId = populationId;
	}

	/**
	 * Permet de retourner le nom et le nombre de population avec le nom de l'id de
	 * la population
	 *
	 * @return Arraylist String
	 */
	public String viewPopulation() {
		try {
			Connection cn = DriverManager.getConnection(
					"jdbc:mysql://localhost/algo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "root");
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(
					"SELECT name, description FROM `population` where idPopulation='" + getPopulationId() + "'");
			while (rs.next()) {
				setName(rs.getString(1));
				setDescription(rs.getString(2));
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		System.out.println(getName() + " " + getDescription());

		return getName() + getDescription();
	}

	/**
	 * Permet de retrourner l'id de la population grâce à son nom
	 *
	 * @return Int id
	 */
	public int viewPopulationId() {
		try {
			Connection cn = DriverManager.getConnection(
					"jdbc:mysql://localhost/algo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "root");
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery("SELECT idPopulation FROM `population` where name='" + getName() + "'");
			while (rs.next()) {
				setPopulationId(rs.getInt(1));
			}
		} catch (SQLException e) {
			System.out.println(e);
		}

		return getPopulationId();
	}

	/**
	 * Permet de placer dans la base de donnée une nouvelle population
	 *
	 * @return String = réussi
	 */
	public boolean setPopulation() {
		try {
			Connection cn = DriverManager.getConnection(
					"jdbc:mysql://localhost/algo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "root");
			Statement st = cn.createStatement();
			st.executeUpdate(
					"INSERT INTO `population`(`name`, `description`) VALUES ('" + getName() + "','" + getDescription() + "')");
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}

		return true;
	}

	/**
	 * Permet de placer dans la base de donnée une nouvelle population
	 *
	 * @param String nom
	 * @param String Desc
	 */
	public void uploadPopulation(String name, String desccription) {
		setName(name);
		setDescription(desccription);
		setPopulation();

	}

	/**
	 * Permet d'afficher le resulatat de la recherche avec l'id passer en paramètre
	 *
	 * @param Int id
	 */
	public void viewPopulation(int populationId) {
		setPopulationId(populationId);
		viewPopulation();

	}

	/**
	 * Permet d'afficher le resulatat de la recherche avec le nom passer en
	 * paramètre
	 *
	 * @param String nom
	 */
	public int viewPopulationId(String name) {
		setName(name);
		return viewPopulationId();
	}

}
