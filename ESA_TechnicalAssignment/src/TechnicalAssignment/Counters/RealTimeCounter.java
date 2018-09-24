package TechnicalAssignment.Counters;

public interface RealTimeCounter 
{
	void increment();
	int getCountInLastSecond();
	int getCountInLastMinute();
	int getCountInLastHour();
	int getCountInLastDay();
}
