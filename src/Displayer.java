import java.util.ArrayList;

public class Displayer {

	static void display(String algName, ArrayList<Job> jobs, float avgWaiting, float avgCompletion) {
		printBorder();
		System.out.print("\n");
		
		System.out.println(algName);
		printNameBorder(algName);
		System.out.print("\n\n");
		
		GanttChart.display(jobs);
		
		printAverageCompletionAndWaiting(avgWaiting, avgCompletion);
		
		System.out.print("\n");
		printBorder();
		System.out.print("\n\n");
	}
	
	static void waitingAndCompletionTimeComparison(CPUSchedulerModel[] arr) {
		printBorder();
		System.out.print("\n");
		
		String name = "Average Waiting & Completion Time Comparison";
		System.out.println(name);
		printNameBorder(name);
		System.out.print("\n\n");

		
		int limit = 10;
		int borderLength = 7;
		String title1 = "NAME";
		String title2 = "AWT";
		String title3 = "ACT";
		
		System.out.print(title1);
		
		for (int i = 0; i < limit - title1.length(); i++)
			System.out.print(" ");
		
		System.out.print(title2 + "\t\t" + title3 + "\n");
		
		System.out.print("-------");
		
		for (int i = 0; i < limit - borderLength; i++)
			System.out.print(" ");

		System.out.print("-------\t-------\n");
		
		for (int i = 0; i < arr.length; i++)
			printComparisonInfo(arr[i].shortName(), limit, arr[i].averageWaitingTime(), arr[i].averageCompletionTime());
		
		System.out.print("\n");
		printBorder();
	}
	
	static void printComparisonInfo(String algName, int limit, float avgWaiting, float avgCompletion) {
		int nameLength = algName.length();
		int margin = limit - nameLength;
		
		System.out.printf("%s", algName);
		
		for (int i = 0; i < margin; i++)
			System.out.printf(" ");
		
		System.out.printf("%.4f ms\t%.4f ms\n", avgWaiting, avgCompletion);
	}
	
	private static void printAverageCompletionAndWaiting(float avgWaiting, float avgCompletion) {
		System.out.printf("\n\nAverage waiting time    : %.4f ms\n", avgWaiting);
		System.out.printf("Average completion time : %.4f ms\n", avgCompletion);
	}
	
	private static void printBorder() {
		System.out.println("......................................................................");
	}
	
	private static void printNameBorder(String name) {
		for (int i = 0; i < name.length(); i++)
			System.out.print(">");
	}
}
