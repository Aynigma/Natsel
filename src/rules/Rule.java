package rules;

import sim.*;

public abstract class Rule implements Cloneable
{
	private Pop pop;
		public Pop getPop() {return this.pop;}
		public void setPop(Pop parentPop) {this.pop = parentPop;}
	
	private Simulation simulation;
		public Simulation getSimulation() {return this.simulation;}
		public void setSimulation(Simulation parentSimulation) {this.simulation = parentSimulation;}
		
	private boolean done;
		public boolean isDone() {return this.done;}

	public abstract boolean behave();
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	
}
