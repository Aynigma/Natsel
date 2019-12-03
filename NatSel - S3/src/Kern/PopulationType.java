package Kern;

import java.util.ArrayList;

public class PopulationType {
	
	public static ArrayList<PopulationType> popTypes;
	public int id;
	public String name;
	
	public PopulationType(String name) {
		if(popTypes == null) {
			popTypes = new ArrayList<PopulationType>();
		}
		
		
		if(popTypes.add(this)) {
			this.id = popTypes.size() - 1;
			this.name = name;
		}
	}
	
}
