package tests;

import java.util.ArrayList;
import dataStruct.SimpleData2D;
import exporter.CSVExporter;

public class TestCSV {

	public static void main(String[] args) {
		/*
		PopulationType red = new PopulationType("rouge");
		PopulationType green = new PopulationType("vert");
		PopulationType blue = new PopulationType("bleu");  
		*/
		
		String[] labels = {"Rouges", "Verts", "Bleus"};
		ArrayList<int[]> datas = new ArrayList<int[]>();
		datas.add(new int[]{0, 50, 90});
		datas.add(new int[]{20, 50, 70});
		datas.add(new int[]{40, 50, 50});
		datas.add(new int[]{60, 50, 30});
		datas.add(new int[]{80, 50, 10});
		datas.add(new int[]{100, 50, 5});
		
		SimpleData2D dataSheet = new SimpleData2D(labels, datas);
		
		CSVExporter csvExporter = new CSVExporter(dataSheet);
		//System.out.println(csvExporter.Convert());
		csvExporter.Export("Test_n1");
		
		
	}

}
