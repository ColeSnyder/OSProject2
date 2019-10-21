import java.util.ArrayList;
import java.util.Collections;

public class PCB {
	
	int time;
	int degree;
	ArrayList<Job> jobs;
	ArrayList<Job> started;
	Job[] finished;
	int sortingType;
	
	public PCB(int time, int degree, ArrayList<Job> jobs) {
		this.time = time;
		this.degree = degree;
		this.jobs = jobs;
		this.started = new ArrayList<Job>();
		finished = new Job[jobs.size()];
	}
	
	public void run(int x) {
		this.sortingType = x;
		System.out.println("Time \tEvent");
		System.out.println("--------------------");
		int time = this.time;
		ArrayList<Job> jobs = this.jobs;
		ArrayList<Job> started = this.started;
		finished[0] = jobs.get(0);
		addReadyJobsInit(jobs, started);
		sort();

		finalPrint();
	}
	
	private void sort() {
		switch(sortingType) {
		case 0:
			//Round Robin No sorting possibly pop first and add to end?
			break;
		case 1:
			Collections.sort(jobs, new SortByShortest()); //Shortest Job First
			break;
		case 2:
			Collections.sort(jobs, new SortByPriority()); //Priority Jobs First
			break;
		}
	}
	
	private void addReadyJobs(ArrayList<Job> jobs, ArrayList<Job> started, int time) {
	
			while(started.size() < degree && jobs.size() > 0) {
				
				Job temp = jobs.remove(0); //Pop job from waiting queue, Need to consider time on waiting
				System.out.println(time + "\t"+temp.getName() + " loaded and ready");
				temp.setArrive(time); //This is the time that the job gets moved in
				temp.setLoad(time);
				started.add(temp); //Add popped job to running
			}

	}
	
	private void addReadyJobsInit(ArrayList<Job> jobs, ArrayList<Job> started) { //Initial Job arrival

		while(started.size() < degree && jobs.size() > 0) {
			System.out.println(0 + "\t"+jobs.get(0).getName() + " arrived");
			Job temp = jobs.remove(0); //Pop job from waiting queue, Need to consider time on waiting
			
			temp.setArrive(0); //This is the time that the job gets moved in will be 0 at start
			temp.setLoad(0);
			started.add(temp); //Add popped job to running
			System.out.println(0 + "\t"+temp.getName() + " loaded and ready");
		}
		
		jobs.forEach((n) -> {
			System.out.println(0 + "\t"+n.getName() + " arrived");
		});

	}
	
	private void finalPrint() {
		System.out.println("Job \tArrival Time \tLoad Time \tCompletion Time \tCpu Time \tTime For I/O \tTime Spent Ready");
		for(Job x : finished) {
			System.out.println(x);
		}
	}
	
	

}
