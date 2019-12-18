package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author baptis
 * 
 */
public class PopulationRules {

	private int ruleId;
	private int populationId;


	/**
	 * @return the id_regle : Int
	 */
	public int getRuleId() {
		return ruleId;
	}

	/**
	 * @param ruleId : Int the id_regle to set
	 */
	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}

	/**
	 * @return the id_pop : Int
	 */
	public int getPopulationId() {
		return populationId;
	}

	/**
	 * @param populationId : Int the id_pop to set
	 */
	public void setPopulationId(int populationId) {
		this.populationId = populationId;
	}


	/**
	 * Permet d'afficher l'ensemble des règles liées à une seule population
	 *
	 * @return ArrayList String
	 */

	public ArrayList<String> viewPopulationRules() {
		ArrayList<String> regle = new ArrayList<String>();
		String pop = "";
		try {
			Connection cn = DriverManager.getConnection(
					"jdbc:mysql://localhost/algo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "root");
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(
					"SELECT p.name, r.name FROM lead l INNER JOIN population p ON p.idPopulation=l.idPopulation  INNER JOIN rule r ON r.idRule = l.idRule where p.idPopulation="
							+ getPopulationId() + "");
			while (rs.next()) {
				pop = rs.getString(1);
				regle.add(rs.getString(2));
			}
			cn.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		System.out.println(pop + " " + regle);

		return regle;
	}

	/**
	 * Permet de placer dans la base de donnée une nouvelle liaison règle,
	 * population
	 *
	 * @return String = réussi
	 */

	public String setLinkPopulationRules() {
		try {
			Connection cn = DriverManager.getConnection(
					"jdbc:mysql://localhost/algo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "root");
			Statement st = cn.createStatement();
			st.executeUpdate(
					"INSERT INTO `lead`(`idRule`, `idPopulation`) VALUES (" + getRuleId() + "," + getPopulationId() + ")");
			cn.close();
		} catch (SQLException e) {
			System.out.println(e);
		}

		return "réussi";
	}

	/**
	 * Permet d'afficher l'ensemble des règles liées à une seule population avec le
	 * nom de la population passé en paramèrtre
	 *
	 * @param name
	 */
	public void viewPopulationRules(String name) {
		PopHandler p = new PopHandler();
		int populationId = p.viewPopulationId(name);
		setPopulationId(populationId);
		viewPopulationRules();

	}

	/**
	 * Permet de placer dans la base de donnée une nouvelle liaison règle,
	 * population avec en paramètre le nom de la population et le nom de la règle.
	 *
	 * @param nom
	 */
	public void uploadLinkPopulationRule(String population, String rules) {
		PopHandler p = new PopHandler();
		int populationId = p.viewPopulationId(population);
		setPopulationId(populationId);
		RulesHandler r = new RulesHandler();
		int RulesId = r.viewRulesId(rules);
		setRuleId(RulesId);
		setLinkPopulationRules();
	}

}
