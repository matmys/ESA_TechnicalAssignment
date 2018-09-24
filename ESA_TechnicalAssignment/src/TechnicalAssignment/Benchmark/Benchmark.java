package TechnicalAssignment.Benchmark;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import TechnicalAssignment.Counters.RealTimeCounter;

public class Benchmark 
{
	public static void Run(RealTimeCounter realTimeCounter, int samples) throws InterruptedException
	{
		System.out.println("Testing: " + realTimeCounter.getClass().getSimpleName());
		
		long epochStart = Instant.now().toEpochMilli();
		
		for(int i =0; i < samples; i++)
		{
			realTimeCounter.increment();
		}
		
		long epochPopulate = Instant.now().toEpochMilli();
		
		System.out.println("Populated in: " + (epochPopulate - epochStart) + " ms");
		
		TimeUnit.MILLISECONDS.sleep(1000-(epochPopulate - epochStart));
		
		epochStart = Instant.now().toEpochMilli();
		
		for (int i = 0; i < samples; i++)
		{
			realTimeCounter.getCountInLastSecond();
		}
		
		long epochCheck = Instant.now().toEpochMilli();
		
		System.out.println("Checked in: " + (epochCheck - epochStart) + " ms");
	}
}
