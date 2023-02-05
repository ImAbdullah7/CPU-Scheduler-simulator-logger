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
		try {
        	Thread.sleep(amount);
        } catch(Exception e) {
        	// Approved by the professor
        	for(int i = 0; i < amount; i++);	
        }
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
