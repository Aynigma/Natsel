package tests;

import exporters.CSVExporter;
import gui.models.TurnDataRowModel;

public class TestCSV {

	public static void main(String[] args) {
		System.out.println(new CSVExporter(new String[] {"red","green","blue"},
				new TurnDataRowModel(0, 1, 2, 3),
				new TurnDataRowModel(1, 2, 4, 6),
				new TurnDataRowModel(2, 4, 6, 8)
				).convert());
	}

}
