public abstract class BisectionCounter implements RealTimeCounter
{
	public abstract void increment(); 

	public int getCountInLastSecond() 
	{
		return this.getCountInLastMilliseconds(1 * 1000);
	}

	public int getCountInLastMinute() 
	{
		return this.getCountInLastMilliseconds(1 * 60 * 1000);
	}

	public int getCountInLastHour() 
	{
		return this.getCountInLastMilliseconds(1 * 60 * 60 * 1000);
	}

	public int getCountInLastDay() 
	{
		return this.getCountInLastMilliseconds(24 * 60 * 60 * 1000);
	}
	
	protected abstract int getCountInLastMilliseconds(long timeSpan);
}
