import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class RoundRobin extends CPUSchedulerModel {

	private int quantum;
	
	public RoundRobin (ArrayList<RawJob> rawJobs, int quantum) {
		this.quantum = quantum;
		addToReadyQueue(rawJobs);
		perform();
	}
	
	protected void perform() {

		if (readyQueue.size() == 0)
			return;
		
		// There is two approaches for this algorithm,
		// I did them both because I'm not sure if I have
		// to do it in one specific way.
		
		// Approach 1: Slice jobs with burst time > quantum in the
		// readyQueue, such that all jobs will be ready to execute
		// in the readyQueue before the execution just like FCFS.
		
		// Approach 2: Before every job execution we will go to the
		// readyQueue and if burst time > quantum we will slice it
		// and return the one with burst time = quantum (the sliced one). 
		
		
		// Approach 1
//		sliceAllInReadyQueue();

		// Approach 2
		RawJob firstRawJob = slice();
		
		// Approach 1
//		RawJob firstRawJob = readyQueue.poll();
		
		Job firstJob = new Job(firstRawJob.id, firstRawJob.name, firstRawJob.burstTime, 0, firstRawJob.burstTime);
		jobs.add(firstJob);
		waitFor(firstJob.burstTime);
			
		while(readyQueue.size() > 0) {
			// Approach 2
			RawJob rawJob = slice();
			
			// Approach 1
//			RawJob rawJob = readyQueue.poll();
			
			int waiting = jobs.get(jobs.size() - 1).completion;
			int completion = waiting + rawJob.burstTime;
			Job job = new Job(rawJob.id, rawJob.name, rawJob.burstTime, waiting, completion);
			jobs.add(job);
			waitFor(job.burstTime);
		}
		
		
	}
	
	private RawJob slice() {
		RawJob rawJob = readyQueue.poll();

		if (rawJob.burstTime > quantum) {
				RawJob copy = rawJob.copy();
				copy.burstTime = quantum;
				rawJob.burstTime -= quantum;
				readyQueue.add(rawJob);
				rawJob = copy;
			}
		
		return rawJob;
	}
	
	private void sliceAllInReadyQueue() {
		ArrayList<RawJob> list = new ArrayList<RawJob>(readyQueue);
		
		int i = 0;
		while(i < list.size()) {
		RawJob rawJob = list.get(i++);

		if (rawJob.burstTime > quantum) {
				RawJob copy = rawJob.copy();
				copy.burstTime -= quantum;
				rawJob.burstTime = quantum;
				list.add(copy);
			}
		}
		
		Queue<RawJob> queue = new LinkedList<RawJob>(list);
		readyQueue = queue;
	}
	
	@Override
	public float averageWaitingTime() {
		ArrayList<Integer> p = new ArrayList<Integer>();
		float sum = 0;
		for (int i = 0; i < jobs.size(); i++) {
			int id = jobs.get(i).id;
			if (p.contains(id) == false) {
				p.add(id);
				int largerCompletion = jobs.get(i).completion;
				int sumOfBurstTimes = jobs.get(i).burstTime;
				for (int j = i + 1; j < jobs.size(); j++)
					if (jobs.get(j).id == id) {
						largerCompletion = jobs.get(j).completion;
						sumOfBurstTimes += jobs.get(j).burstTime;
					}
				
				sum += largerCompletion - sumOfBurstTimes;
			}
		}

		return sum / p.size();
	}
	
	@Override
	public float averageCompletionTime() {
		ArrayList<Integer> p = new ArrayList<Integer>();
		float sum = 0;
		for (int i = 0; i < jobs.size(); i++) {
			int id = jobs.get(i).id;
			if (p.contains(id) == false) {
				p.add(id);
				int largerCompletion = jobs.get(i).completion;
				for (int j = i + 1; j < jobs.size(); j++)
					if (jobs.get(j).id == id)
						largerCompletion = jobs.get(j).completion;

				sum += largerCompletion;
			}
		}

		return sum / p.size();
	}
	
	protected String shortName() {
		return "RR-" + quantum;
	}
	
	protected String name() {
		return "Round Robin (" + shortName() + ")";
	}
	
}
