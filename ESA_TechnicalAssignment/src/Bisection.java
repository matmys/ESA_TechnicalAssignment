import java.util.Vector;


public class Bisection 
{
	public static int countGreaterOrEqualValues(Vector<Long> vector, long targetValue)
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
