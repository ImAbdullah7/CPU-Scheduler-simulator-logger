import java.util.ArrayList;

public class GanttChart {
	
	static void display(ArrayList<Job> jobs) {

		if (jobs.size() == 0)
			return;

		int blockWidthMult = 2;

		printLine(jobs, blockWidthMult);
		System.out.print("\n");

		printNamesAndSeparators(jobs, blockWidthMult);
		System.out.print("\n");

		printLine(jobs, blockWidthMult);
		System.out.print("\n");

		printTimes(jobs, blockWidthMult);
		System.out.print("\n");
	}

	private static void printLine(ArrayList<Job> jobs, int mult) {
		// Print a line as tall as each block
		// Print a space before and after each block
		
		// The space before the first block
		System.out.print(" ");

		for (int i = 0; i < jobs.size(); i++) {
			int burstTime = jobs.get(i).burstTime;
			for (int j = 0; j < blockWidth(burstTime, mult); j++)
				System.out.print("-");

			System.out.print(" ");
		}
	}


	private static void printNamesAndSeparators(ArrayList<Job> jobs, int blockWidthMult) {
		// Print every separator on the begging and end of every block,
		// print process name in the middle of each block (if possible),
		// if the process name larger than the block process name will be truncated.
		
		for (int i = 0; i < jobs.size(); i++) {
			System.out.print("|");
			int burstTime = jobs.get(i).burstTime;
			int blockWidth = blockWidth(burstTime, blockWidthMult);
			int nameLength = jobs.get(i).name.length();

			if (blockWidth == nameLength)
				System.out.print(jobs.get(i).name);
			
			else if (blockWidth > nameLength) {
				int margin = blockWidth - nameLength;
				
				// Left margin
				for (int j = 0; j < margin / 2; j++)
					System.out.print(" ");

				System.out.print(jobs.get(i).name);

				// Right margin
				for (int j = 0; j < Math.ceil(margin / 2.0); j++)
					System.out.print(" ");

			} else {
				// Truncate if block width smaller than name length
				String name = jobs.get(i).name;
				int x = nameLength - blockWidth;
				String newName = name.substring(0, nameLength - x - 2) + "..";
				System.out.print(newName);

			}
		}

		System.out.print("|");
	}
	
	private static void printTimes(ArrayList<Job> jobs, int blockWidthMult) {
		// This method will print start and completion time 
		// for each process.
		// To print every number on the right place we need to calculate
		// how many spaces needed to be printed between each number,
		// if the length (number of digits) of last process's completion time > 1
		// for example: 10 . it will effect how many spaces will be printed. 
		// number of printed spaces = block width - (number of digits of the last process's completion time - 1)
		
		// Waiting time of the first process
		System.out.print("0");

		 
		int numberOfDigitsOfLastCompletionTime = 1;
		for (int i = 0; i < jobs.size(); i++) {
			int burstTime = jobs.get(i).burstTime;
			for (int j = 0; j < blockWidth(burstTime, blockWidthMult) - (numberOfDigitsOfLastCompletionTime - 1); j++)
				System.out.print(" ");
			

			String e = jobs.get(i).completion + "";
			numberOfDigitsOfLastCompletionTime = e.length();
			System.out.print(jobs.get(i).completion);
		}
	}
	
	private static int blockWidth(int burstTime, int mult) {
		// Each block's width will be burst time * mult,
		// to not make the chart long for large burst times
		// there will be a limit, if burst time exceeded the limit
		// we will take 30% of the exceeded part : (burstTime - limit) * 0.3  .
		// For small burst Times we will add to the block's width.   
		
		int limit = 20;
		if (burstTime > limit) {
			int x = limit * mult;
			int y = (int)((burstTime - limit) * 0.3);
			return x + y;
			
		} else {
			return burstTime * mult + extraWidthForSmallBlocks(burstTime);
		}
	}

	private	static int extraWidthForSmallBlocks(int width) {
		if (width == 1)
			return 2;
		else if (width == 2)
			return 1;
		else
			return 0;
	}

}
