import java.time.Instant;
import java.util.Vector;

//Performs better for the smaller sets of data. 
//For large sets of data the vector reallocation has a huge impact on performance.
//This could be solved by knowing an approximate sample size and preallocating vector.
public class SimpleBisectionCounter extends BisectionCounter
{
	private static final int _initialVectorSize = 1024;
	
	private final Vector<Long> _timestamps; 

	public SimpleBisectionCounter()
	{
		this._timestamps = new Vector<>(SimpleBisectionCounter._initialVectorSize);
	}
	
	public void increment() 
	{
		this._timestamps.add(Instant.now().toEpochMilli());
	}

	protected int getCountInLastMilliseconds(long timeSpan)
	{
		long nowEpoch = Instant.now().toEpochMilli();
		long targetEpoch = nowEpoch - timeSpan;
		
		if(this._timestamps.size() == 0 || this._timestamps.lastElement() < targetEpoch)
		{
			return 0;
		}
		else if (this._timestamps.firstElement() >= targetEpoch)
		{
			return this._timestamps.size();
		}
		
		return Bisection.countGreaterOrEqualValues(this._timestamps, targetEpoch);
	}
}
