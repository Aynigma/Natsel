package exporter;

import dataStruct.SimpleData2D;

public class CSVExporter extends Exporter{

	public CSVExporter(SimpleData2D dataSheet) {
		super(dataSheet);
	}
	
	
	@Override
	public String Convert() {

		String output = "";
		
		//table header
		output += "Tour, ";
		for (int i = 0; i < dataSheet.header.length-1; i++) {
			output += dataSheet.header[i] + ", ";
		}
		output += dataSheet.header[dataSheet.header.length - 1] + "\n";
		
		//table content
		int rowCounter = 0;
		
		//iterration des lignes
		for (int[] popQuantities : dataSheet.datas) {
			output += rowCounter + ", ";
			
			//iterration des données de chaque ligne
			for (int i = 0; i < popQuantities.length-1; i++) {
				output += popQuantities[i] + ", ";
			}
			
			output += popQuantities[popQuantities.length - 1];
			
			if(rowCounter < dataSheet.datas.size() - 1) {
				output += "\n";
			}
			
			rowCounter++;
		}
		
		return output;
	}
	
	
	@Override
	public boolean Export(String fileName) {
		return Write(fileName + ".csv", Convert());
	}

}
