
public class RawJob {
	
	public int id;
	public String name;
	public int burstTime;
	
	public RawJob(int id, String name, int burstTime) {
		this.id = id;
		this.name = name;
		this.burstTime = burstTime ;
	}
	
	public RawJob copy() {
		return new RawJob(id, name, burstTime);
	}
}
