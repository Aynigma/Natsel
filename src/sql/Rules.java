package sql;

import java.sql.*;
import java.util.ArrayList;

/**
 * Classe gèrant l'esemble des appels de la base de donnée liée aux regles
 *
 * @author Baptiste
 */
public class Rules {

	private int rulesId;
	private String name;
	private String desccription;

	/**
	 * @return the idregles : int
	 */
	public int getRulesId() {
		return rulesId;
	}

	/**
	 * @param idregles : int the idregles to set
	 */
	public void setRulesId(int idregles) {
		this.rulesId = idregles;
	}

	/**
	 * @return the nom : String
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param nom : String the nom to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the desc : String
	 */
	public String getDescription() {
		return desccription;
	}

	/**
	 * @param description : String the desc to set
	 */
	public void setDescription(String description) {
		this.desccription = description;
	}

	/**
	 * Permet de retourner le nom et la description avec l'id de la regle
	 *
	 * @return Arraylist String
	 */
	public ArrayList<String> viewRules() {
		ArrayList<String> rules = new ArrayList<String>();
		try {
			Connection cn = DriverManager.getConnection(
					"jdbc:mysql://localhost/algo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "root");
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery("SELECT name, description FROM `rule` where idRule='" + getRulesId() + "'");
			while (rs.next()) {
				setName(rs.getString(1));
				setDescription(rs.getString(2));
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		System.out.println(getName() + " " + getDescription());
		rules.add(getName());
		rules.add(getDescription());

		return rules;
	}

	/**
	 * Permet de retrourner l'id de la règle grâce à son nom
	 *
	 * @return Int id
	 */

	public int viewRulesId() {
		try {
			Connection cn = DriverManager.getConnection(
					"jdbc:mysql://localhost/algo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "root");
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery("SELECT idRule FROM `rule` where name='" + getName() + "'");
			while (rs.next()) {
				setRulesId(rs.getInt(1));
			}
		} catch (SQLException e) {
			System.out.println(e);
		}

		return getRulesId();
	}

	/**
	 * Permet de placer dans la base de donnée une nouvelle règle
	 *
	 * @return String = réussi
	 */
	public String setRules() {
		try {
			Connection cn = DriverManager.getConnection(
					"jdbc:mysql://localhost/algo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "root");
			Statement st = cn.createStatement();
			st.executeUpdate(
					"INSERT INTO `rule`(`name`, `description`) VALUES ('" + getName() + "','" + getDescription() + "')");
		} catch (SQLException e) {
			System.out.println(e);
		}

		return "réussi";
	}

	/**
	 * Permet de placer dans la base de donnée une nouvelle règle
	 *
	 * @param String nom
	 * @param Int    Description
	 */
	public void uploadRegle(String name, String desccription) {
		setName(name);
		setDescription(desccription);
		setRules();

	}

	/**
	 * Permet d'afficher le resulatat de la recherche avec l'id passer en paramètre
	 *
	 * @param Int id
	 */
	public void viewRules(int rulesId) {
		setRulesId(rulesId);
		viewRules();

	}

	/**
	 * Permet d'afficher le resulatat de la recherche avec le nom passer en
	 * paramètre
	 *
	 * @param String nom
	 */
	public int viewRulesId(String name) {
		setName(name);
		return viewRulesId();
	}

}
