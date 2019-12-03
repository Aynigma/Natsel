package rules;

import sim.*;

public class Reproduce extends Rule 
{
	
	private float eatenNeed;
	private int children;
	
	public Reproduce(float newNeed)
	{
		this.eatenNeed = newNeed;
		this.children = 1;
	}
	public Reproduce(float newNeed, int newChildren)
	{
		this.eatenNeed = newNeed;
		this.children = newChildren;
	}
	
	@Override
	public boolean behave()
	{
		if(this.getPop().getEaten() >= eatenNeed)
		{
			for(int i = 0; i < children; i++)
			{
				Pop child = new Pop(getPop().getName(), getPop().getDescription(), getPop().getSimulation(), getPop().getFoodType(), getPop().getFoodValue());
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
		return true;
	}
	
}
