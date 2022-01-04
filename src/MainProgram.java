import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;

public class MainProgram {

	public static void main(String[] args) {

	    ArrayList<RawJob> rawJobs = readRawJobs("testdata3.txt");
	    
		if (rawJobs.size() == 0)
			return;
						
		CPUSchedulerModel fcfs = new FirstComeFirstServed(rawJobs);
		fcfs.printInfo();

		CPUSchedulerModel sjf = new ShortestJobFirst(rawJobs);
		sjf.printInfo();

		CPUSchedulerModel rr3 = new RoundRobin(rawJobs, 3);
		rr3.printInfo();

		CPUSchedulerModel rr5 = new RoundRobin(rawJobs, 5);
		rr5.printInfo();

		CPUSchedulerModel[] algs = { fcfs, sjf, rr3, rr5 };
		Displayer.waitingAndCompletionTimeComparison(algs);
	}

	static public ArrayList<RawJob> readRawJobs(String title) {
		ArrayList<RawJob> rawJobs = new ArrayList<RawJob>();
		
		try {
			File file = new File(title);
			Scanner sc = new Scanner(file);
			int i = 0;
		    while (sc.hasNextLine()) {
		    	String name = sc.nextLine();
		    	if (sc.hasNextInt()) {
		    		int burstTime = sc.nextInt();
		    		if (burstTime < 1) { continue; }
		    		rawJobs.add(new RawJob(i++, name, burstTime));
		    	}
		  }
		    sc.close();
		    
		} catch (Exception e) {
			System.out.println("ERROR: Something went wrong");
		}
		
//	    for (RawJob line : rawJobs) {
//            System.out.println(line.name + "\n" + line.burstTime);
//        }
	    
	    return rawJobs;
	}
}

