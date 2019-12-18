package exporters;

import java.util.Arrays;
import gui.models.TurnDataRowModel;

/**
 * This class is an implementation of the Exporter class meant to export easily turn data into CSV format.
 * @author Aynigma
 * @see Exporter
 */
public class CSVExporter extends Exporter{
	
	public CSVExporter(String[] headers, TurnDataRowModel... turnDatas) {
		super(headers, turnDatas);
	}
	
	
	/**
	 * This method enables to convert raw data into a String of Comma Separated Values formated data
	 * @return a String representing exporter's raw data into desired format
	 */
	@Override
	public String convert() {

		String output = "";
		
		//table header
		output += ("Turn"+((headers.length > 0)
				? (", "+Arrays.toString(headers).substring(1, Arrays.toString(headers).length()-1))
				: "")
				+"\n");
		
		
		//table content
		for (TurnDataRowModel data : turnDatas) {
			int[] quantities = data.getPopulationQuantities();
			output += (data.getTurn()+((quantities.length > 0)
					? (", "+Arrays.toString(quantities).substring(1, Arrays.toString(quantities).length()-1))
					: "")
					+"\n");
		}

		return output;
	}
	
	
	@Override
	public boolean export(String filePath) {
		return write(filePath, convert());
	}

}
