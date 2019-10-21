import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Driver {

	public static void main(String[] args) throws FileNotFoundException {
		int timer = 0;
		int degreeMultiProg = 0;
		Scanner scan = new Scanner(new File("jobs.dat"));
		
		ArrayList<Job> jobs = new ArrayList<Job>();
		
		timer = scan.nextInt();
		degreeMultiProg = scan.nextInt();
		
		String name = "";
		int priority = 99;
		
		
		while(scan.hasNextLine()) {
			ArrayList<String> desc = new ArrayList<String>();
			name = scan.next();
			priority = scan.nextInt();
			for (String x : scan.nextLine().trim().split(" ")) {
				desc.add(x);
			}
			jobs.add(new Job(name,priority,desc));
		}
		
		Collections.sort(jobs, new SortByShortest());
		
		for(Object x : jobs.toArray()) {
			System.out.println(x);
		}
		
		PCB pcb = new PCB(timer, degreeMultiProg, jobs);
		pcb.run(0);

	}

}
