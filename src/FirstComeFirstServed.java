import java.util.ArrayList;

public class FirstComeFirstServed extends CPUSchedulerModel {

	public FirstComeFirstServed (ArrayList<RawJob> rawJobs) {
		addToReadyQueue(rawJobs);
		perform();
	}

	protected void perform() {
		if (readyQueue.size() == 0)
			return;
		
		RawJob firstRawJob = readyQueue.poll();
		Job firstJob = new Job(firstRawJob.id, firstRawJob.name, firstRawJob.burstTime, 0, firstRawJob.burstTime);
		jobs.add(firstJob);
		waitFor(firstJob.burstTime);
				
		while(readyQueue.size() > 0) {
			RawJob rawJob = readyQueue.poll();
			int waiting = jobs.get(jobs.size() - 1).completion;
			int completion = waiting + rawJob.burstTime;
			Job job = new Job(rawJob.id, rawJob.name, rawJob.burstTime, waiting, completion);
			jobs.add(job);
			waitFor(job.burstTime);
		}
	 }
	
	protected String shortName() {
		return "FCFS";
	}
	
	protected String name() {
		return "First Come First Serve (" + shortName() + ")";
	}
}
