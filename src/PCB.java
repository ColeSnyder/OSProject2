import java.util.ArrayList;
import java.util.Collections;

public class PCB {
	
	int time;
	int degree;
	ArrayList<Job> jobs;
	ArrayList<Job> started;
	int sortingType;
	
	public PCB(int time, int degree, ArrayList<Job> jobs) {
		this.time = time;
		this.degree = degree;
		this.jobs = jobs;
		this.started = new ArrayList<Job>();
	}
	
	public void run(int x) {
		
		int time = this.time;
		ArrayList<Job> jobs = this.jobs;
		ArrayList<Job> started = this.started;
		
		addReadyJobs(jobs, started);
		
		sort();
	}
	
	private void sort() {
		switch(sortingType) {
		case 0:
			//Round Robin No sorting
			break;
		case 1:
			Collections.sort(jobs, new SortByShortest()); //Shortest Job First
			break;
		case 2:
			Collections.sort(jobs, new SortByPriority()); //Priority Jobs First
			break;
		}
	}
	
	private void addReadyJobs(ArrayList<Job> jobs, ArrayList<Job> started) {
	
			while(started.size() < degree && jobs.size() > 0) {
				started.add(jobs.remove(0));
				System.out.println(started);
			}

	}
	
	

}
