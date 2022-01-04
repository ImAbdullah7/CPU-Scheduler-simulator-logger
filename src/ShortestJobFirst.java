import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class ShortestJobFirst extends CPUSchedulerModel {
	
	public ShortestJobFirst (ArrayList<RawJob> rawJobs) {
		addToReadyQueue(rawJobs);
		perform();
	}
	
	protected void perform() {

		if (readyQueue.size() == 0)
			return;
		
		// There is two approaches for this algorithm,
		// I did them both because I'm not sure if I have
		// to do it in one specific way.
		
		// Approach 1: Sort all jobs in the readyQueue by 
		// smallest burst time after that all jobs will be ready to execute
		// in the readyQueue before the execution just like FCFS.
		
		// Approach 2: Before every job execution we will go to the
		// readyQueue and get smallest burst time job.
		
		// Approach 1:
//		sortBySmallestBurstTime();

		// Approach 2:
		RawJob firstRawJob =  smallestBurstTimeJob();

		// Approach 1:		
//		RawJob firstRawJob =  readyQueue.poll();
		
		Job firstJob = new Job(firstRawJob.id, firstRawJob.name, firstRawJob.burstTime, 0, firstRawJob.burstTime);
		jobs.add(firstJob);
		waitFor(firstJob.burstTime);
			
		while(readyQueue.size() > 0) {
			// Approach 2:
			RawJob rawJob = smallestBurstTimeJob();
			
			// Approach 1:
//			RawJob rawJob = readyQueue.poll();
			
			int waiting = jobs.get(jobs.size() - 1).completion;
			int completion = waiting + rawJob.burstTime;
			Job job = new Job(rawJob.id, rawJob.name, rawJob.burstTime, waiting, completion);
			jobs.add(job);
			waitFor(job.burstTime);
		}
	}
	
	private RawJob smallestBurstTimeJob() {
		RawJob smallest = readyQueue.peek();
		
		for(int i = 0; i < readyQueue.size(); i++) {
			RawJob r = readyQueue.poll();
			if (r.burstTime < smallest.burstTime)
				smallest = r;
			readyQueue.add(r);
		}
		
		readyQueue.remove(smallest);
		return smallest;
	}
	
	private void sortBySmallestBurstTime() {
		ArrayList<RawJob> rawJobs = new ArrayList<RawJob>(readyQueue); 
		
		for(int i = 0; i < rawJobs.size(); i++) {
			for(int j = i + 1; j < rawJobs.size(); j++) {
				if (rawJobs.get(j).burstTime < rawJobs.get(i).burstTime) {
					RawJob temp = rawJobs.get(j);
					rawJobs.set(j, rawJobs.get(i));
					rawJobs.set(i, temp);
				}
			}
		}
		
		Queue<RawJob> q = new LinkedList<RawJob>(rawJobs);
		readyQueue = q;
	}

	protected String shortName() {
		return "SJF";
	}
	
	protected String name() {
		return "Shortest Job First (" + shortName() + ")"; 
	}
}
