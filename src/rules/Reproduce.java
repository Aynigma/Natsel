package rules;

import sim.*;

public class Reproduce extends Rule 
{
	
	private float eatenNeed;
	
	public Reproduce(float newNeed)
	{
		this.eatenNeed = newNeed;
	}
	
	@Override
	public void behave()
	{
		if(this.getPop().getEaten() >= eatenNeed)
		{
			Pop child = new Pop(getPop().getName(), getPop().getSimulation(), getPop().getFoodType(), getPop().getFoodValue());
			for(Rule rule : this.getPop().getRules())
				try {child.addRule((Rule)rule.clone());} 
				catch (CloneNotSupportedException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			getSimulation().addChild(child);
		}
	}
	
}
