package TechnicalAssignment;
import TechnicalAssignment.Benchmark.Benchmark;
import TechnicalAssignment.Counters.FragmentedBisectionCounter;
import TechnicalAssignment.Counters.SimpleBisectionCounter;


public class Main 
{
	public static void main(String [] args) throws InterruptedException
	{
		SimpleBisectionCounter bisectionCounter = new SimpleBisectionCounter();
		FragmentedBisectionCounter fragmentedBisectionCounter = new FragmentedBisectionCounter();
		
		Benchmark.Run(bisectionCounter, 10000000);
		Benchmark.Run(fragmentedBisectionCounter, 10000000);
	}
}
