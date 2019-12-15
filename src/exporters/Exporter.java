package exporters;

import java.io.FileWriter;
import java.io.IOException;
import gui.models.TurnDataRowModel;

/**
 * This abstract class is meant to export easily turn data into different format and to put in common most of exporters architecture
 * @author Aynigma
 */
public abstract class Exporter {
	
	protected String[] headers;
	protected TurnDataRowModel[] turnDatas;
	
	
	public Exporter(String[] headers, TurnDataRowModel... turnDatas) {
		this.headers = headers;
		this.turnDatas = turnDatas;
	}
	
	
	
	/**
	 * This method enables to convert raw data into a String of formated data
	 * @return a String representing exporter's raw data into desired format
	 */
	public abstract String convert();
	
	
	/**
	 * This method enables to save into a file converted data
	 * @param filePath the absolute path of the desired file
	 * @return -true if export succeeded
	 * <br/>- false if export failed 
	 */
	public abstract boolean export(String filePath);
	

	/**
	 * This method enables write into a file desired String
	 * @param filePath the absolute path of the desired file
	 * @param fileContent the content to be put into the file
	 * @return -true if write succeeded
	 * <br/>- false if write failed 
	 */
	protected boolean write(String filePath, String fileContent) {
		
		FileWriter writer = null;
		try {
			writer = new FileWriter(filePath);
			
			writer.write(fileContent, 0, fileContent.length());
			
		} 
		catch (IOException e) {
			e.printStackTrace();
			return false;
		} 
		finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return true;
	}
}
