package TechnicalAssignment.Counters;

import java.util.Vector;

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
	
	protected int countGreaterOrEqualValues(Vector<Long> vector, long targetValue)
	{
		int windowStart = 0;
		int windowEnd = vector.size();
		int windowSize = windowEnd - windowStart;
		
		int currentIndex = 0;
		long currentEpoch = 0;
		
		while(windowSize != 1)
		{
			currentIndex = windowStart + (windowSize / 2);
			currentEpoch = vector.get(currentIndex);
			
			if(currentEpoch >= targetValue)
			{
				windowEnd = currentIndex; 
			}
			else
			{
				windowStart = currentIndex;
			}
			
			windowSize = windowEnd - windowStart;
		}
		
		if(windowStart == 0 && vector.firstElement() >= targetValue)
		{
			return vector.size();
		}
		else
		{
			return vector.size() - windowStart - 1;
		}
	}
}
