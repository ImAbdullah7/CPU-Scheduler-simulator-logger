import java.util.ArrayList;
import java.lang.Thread;
import java.util.LinkedList;
import java.util.Queue;

abstract public class CPUSchedulerModel {
	
	protected Queue<RawJob> readyQueue = new LinkedList<RawJob>();
	protected ArrayList<Job> jobs = new ArrayList<Job>();
	
	abstract protected void perform();
	abstract protected String name();
	abstract protected String shortName();
	
	protected void waitFor(int amount) {
        // There are two valid ways to wait when executing jobs. 
		// 1: Using sleep
		// 2: Using a counter (The doctor did approve this way)
		
		try {
        	Thread.sleep(amount);
        } catch(Exception e) {
        	for(int i = 0; i < amount; i++);	
        }
        
//        for(int i = 0; i < amount; i++);
	}
	
	protected void addToReadyQueue(ArrayList<RawJob> rawJobs) {
		for (int i = 0; i < rawJobs.size(); i++)
			readyQueue.add(rawJobs.get(i).copy());
	}
	
	public float averageWaitingTime() {
		float sumOfStartingTime = 0;
			for (int i = 0; i < jobs.size(); i++)
				sumOfStartingTime += jobs.get(i).waiting;
			
		return sumOfStartingTime / jobs.size();
	}
	
	public float averageCompletionTime() {
		float sumOfEndingTime = 0;
			for (int i = 0; i < jobs.size(); i++)
				sumOfEndingTime += jobs.get(i).completion;
			
		return sumOfEndingTime / jobs.size();
	}
	
	public void printInfo() {
		float avgWaiting = averageWaitingTime();
		float avgCompletion = averageCompletionTime();
		Displayer.display(name(), jobs, avgWaiting, avgCompletion);
	}
}
