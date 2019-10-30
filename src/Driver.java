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
		ArrayList<Job> jobs1 = new ArrayList<Job>();
		ArrayList<Job> jobs2 = new ArrayList<Job>();
		
		timer = scan.nextInt();
		degreeMultiProg = scan.nextInt();
		
		String name = "";
		int priority = 99;
		
		
		while(scan.hasNextLine()) {
			ArrayList<String> desc = new ArrayList<String>();
			ArrayList<String> desc1 = new ArrayList<String>();
			ArrayList<String> desc2 = new ArrayList<String>();
			name = scan.next();
			priority = scan.nextInt();
			for (String x : scan.nextLine().trim().split(" ")) {
				desc.add(x);
				desc1.add(x);
				desc2.add(x);
			}
			
			jobs.add(new Job(name,priority,(ArrayList<String>) desc));
			jobs1.add(new Job(name,priority,(ArrayList<String>) desc1));
			jobs2.add(new Job(name,priority,(ArrayList<String>) desc2));
		}

		PCB pcb = new PCB(timer, degreeMultiProg, jobs);
		pcb.run(0);
		pcb = new PCB(timer, degreeMultiProg, jobs1);
		System.out.println();
		pcb.run(1);
		pcb = new PCB(timer, degreeMultiProg, jobs2);
		System.out.println();
		pcb.run(2);

	}

}
