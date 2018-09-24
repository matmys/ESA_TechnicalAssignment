package TechnicalAssignment.Counters;

import java.time.Instant;
import java.util.Vector;

//This counter is easier to maintain, because it grows dynamically without the needing of extra reallocations. 
//The binary search on the vectors has a minimal impact on overall performance.
//It could be used for a large samples of data with unknown length.
//If we would like to clear the old samples (i.e. older then 24 h), then this counter will also perform faster in such scenarios.
public class FragmentedBisectionCounter extends BisectionCounter
{
	private static final int _innerVectorCapacity = 4096;
	private static final int _outerVectorCapacity = 1000;
	
	private final Vector<Vector<Long>> _timestamps;

	public FragmentedBisectionCounter()
	{
		this._timestamps = new Vector<>(FragmentedBisectionCounter._outerVectorCapacity);
		this._timestamps.add(new Vector<Long>(FragmentedBisectionCounter._innerVectorCapacity));
	}
	
	public void increment() 
	{
		Vector<Long> currentVector = this._timestamps.lastElement();
		
		if(currentVector.size() == FragmentedBisectionCounter._innerVectorCapacity)
		{
			currentVector = new Vector<>(FragmentedBisectionCounter._innerVectorCapacity);
			this._timestamps.add(currentVector);
		}
		
		currentVector.add(Instant.now().toEpochMilli());
	}
	
	protected int getCountInLastMilliseconds(long timeSpan)
	{
		long nowEpoch = Instant.now().toEpochMilli();
		long targetEpoch = nowEpoch - timeSpan;
		
		if(this._timestamps.firstElement().size() == 0 || this._timestamps.lastElement().lastElement() < targetEpoch)
		{
			return 0;
		}
		else if (this._timestamps.firstElement().firstElement() >= targetEpoch)
		{
			return (this._timestamps.size() - 1) * FragmentedBisectionCounter._innerVectorCapacity + 
					this._timestamps.lastElement().size();
		}
		
		int targetVectorIndex = this.findVectorWithTargetEpoch(targetEpoch);
		int countedValuesInTargetVector = this.countGreaterOrEqualValues(this._timestamps.get(targetVectorIndex), targetEpoch);
		
		int result = (this._timestamps.size() - (targetVectorIndex + 1)) * FragmentedBisectionCounter._innerVectorCapacity + 
				countedValuesInTargetVector;
		
		if(targetVectorIndex != this._timestamps.size() - 1)
		{
			result += this._timestamps.lastElement().size() - FragmentedBisectionCounter._innerVectorCapacity;
		}
		
		return result;
	}
	
	private int findVectorWithTargetEpoch(long targetEpoch)
	{
		int windowStart = 0;
		int windowEnd = this._timestamps.size();
		int windowSize = windowEnd - windowStart;
		
		int currentIndex = 0;
		Vector<Long> currentVector = null;
		
		while(windowSize != 1)
		{
			currentIndex = windowStart + (windowSize / 2);
			currentVector = this._timestamps.get(currentIndex);
			
			long vectorStart = currentVector.firstElement();
			long vectorEnd = currentVector.lastElement();
			
			if(vectorStart >= targetEpoch)
			{
				windowEnd = currentIndex;
			}
			else if(vectorEnd < targetEpoch)
			{
				windowStart = currentIndex;
			}
			else
			{
				return currentIndex;
			}
			
			windowSize = windowEnd - windowStart;
		}
		
		currentIndex = windowStart + (windowSize / 2);
		
		return currentIndex;
	}
}
