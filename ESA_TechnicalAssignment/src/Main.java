import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class Main 
{
	public static void main(String [] args) throws InterruptedException
	{
		SimpleBisectionCounter bisectionCounter = new SimpleBisectionCounter();
		FragmentedBisectionCounter fragmentedBisectionCounter = new FragmentedBisectionCounter();
		
		Benchmark(bisectionCounter);
		Benchmark(fragmentedBisectionCounter);
	}
	
	private static void Benchmark(RealTimeCounter realTimeCounter) throws InterruptedException
	{
		System.out.println("Testing: " + realTimeCounter.getClass().getName());
		
		long epochStart = Instant.now().toEpochMilli();
		
		for(int i =0; i < 10000000; i++)
		{
			realTimeCounter.increment();
		}
		
		long epochPopulate = Instant.now().toEpochMilli();
		
		System.out.println("Populated in: " + (epochPopulate - epochStart) + " ms");
		
		TimeUnit.MILLISECONDS.sleep(1000-(epochPopulate - epochStart));
		
		epochStart = Instant.now().toEpochMilli();
		
		for (int i = 0; i < 10000000; i++)
		{
			realTimeCounter.getCountInLastSecond();
		}
		
		long epochCheck = Instant.now().toEpochMilli();
		
		System.out.println("Checked in: " + (epochCheck - epochStart) + " ms");
	}
}
