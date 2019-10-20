package exporter;

//import java.nio.file.Path;
import java.io.FileWriter;
import java.io.IOException;

import dataStruct.SimpleData2D;


public abstract class Exporter {
	
	public SimpleData2D dataSheet;
	
	
	public Exporter(SimpleData2D dataSheet) {
		this.dataSheet = dataSheet;
	}
	
	
	
	/**
	 * TODO
	 * Cette methode permet de convertir unr datasheet en données String du format de l'exporter
	 * @return le String correspondant au contenu de la datasheet au format de l'exporter
	 */
	public abstract String Convert();
	
	
	/**
	 * TODO
	 * Cette methode permet de sauvegarder dans un fichier le contenu d'une datasheet
	 * @param le nom du fichier sans extension
	 * @return true si l'écriture du fichier est réussie sinon false
	 */
	public abstract boolean Export(String fileName);
	

	protected boolean Write(String filePath, String fileContent) {
		
		try {
			FileWriter writer = new FileWriter(filePath);
			
			writer.write(fileContent, 0, fileContent.length());
			writer.close();
			
		} 
		catch (IOException e) {
			// TODO File path error;
			e.printStackTrace();
			return false;
		} 
		finally {
			// TODO: handle finally clause
		}
		
		return true;
	}
}
