package rules;

public class NeedFood extends Rule

{
	private float need, survivalRate;
	
	public NeedFood(float newNeed)
	{
		this.need = newNeed;
		this.survivalRate = 0;
	}
	public NeedFood(float newNeed, float newSurvivalRate)
	{
		this.need = newNeed;
		this.survivalRate = newSurvivalRate;
	}
	
	@Override
	public void behave() 
	{
		if(getPop().getEaten() < this.need)
		{
			float trueSurvivalRate = (int) (getPop().getEaten()/need) * survivalRate;
			int scenarium = (int) (Math.random() * 100);
			if(scenarium > survivalRate) getPop().kill();
		}
	}

}
