
public class Job {

	public int id;
	public String name;
	public int burstTime;
	public int waiting;
	public int completion;
	
	public Job(int id, String name, int burstTime, int waiting, int completion) {
		this.id = id;
		this.name = name;
		this.burstTime = burstTime;
		this.waiting = waiting;
		this.completion = completion;
	}
	
}
