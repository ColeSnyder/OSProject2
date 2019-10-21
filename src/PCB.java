import java.util.ArrayList;
import java.util.Collections;

public class PCB {
	
	int time;
	int degree;
	ArrayList<Job> jobs;
	ArrayList<Job> started;
	ArrayList<Job> ioWait;
	
	Job[] finished;
	int sortingType;
	
	public PCB(int time, int degree, ArrayList<Job> jobs) {
		this.time = time;
		this.degree = degree;
		this.jobs = jobs;
		this.started = new ArrayList<Job>();
		this.ioWait = new ArrayList<Job>();
		finished = new Job[jobs.size()];
	}
	
	public void run(int x) {
		
		int counter = 0;
		
		this.sortingType = x;
		System.out.println("Time \tEvent");
		System.out.println("--------------------");
		int time = this.time;
		int currentTime = 0;
		
		ArrayList<Job> jobs = this.jobs;
		ArrayList<Job> started = this.started;

		addReadyJobsInit(jobs, started);
		
//		System.out.println(started);
		
		while(!(noJobs())) {
			
		int timePassed = 0;
			sort();
			
			if(started.get(0).getNext().equals("I") || started.get(0).getNext().equals("O") || started.get(0).getNext().equals("T")) {
				sendToWait(started, ioWait);
				System.out.println(currentTime + "\t" + started.get(0).getName() + " needs " + started.get(0).getNext());
			} else {
				
				int currentJobSize = Integer.parseInt(started.get(0).getNext());
				
				if(Integer.parseInt(started.get(0).getNext()) <= time) { 
					
					System.out.println(currentTime + "\t" + started.get(0).getName() + " running");
					currentTime += currentJobSize;
					started.get(0).pop();
					timePassed = currentJobSize;
					System.out.println(currentTime + "\t" + started.get(0).getName() + " timed out");
					started.get(0).addCPUTime(timePassed);
					
				} else if(Integer.parseInt(started.get(0).getNext()) > time) {
				
					System.out.println(currentTime + "\t" + started.get(0).getName() + " running");
					started.get(0).update((currentJobSize - time)+"");
					currentTime += time;
					timePassed = time;
					System.out.println(currentTime + "\t" + started.get(0).getName() + " timed out");
					started.get(0).addCPUTime(timePassed);
					
				}
			
			}
			final int tempTimePassed = timePassed;
			if (started.get(0).finished()) {
				started.get(0).setComplete(currentTime);
				finished[counter++] = started.remove(0);
				addReadyJobs(jobs, started, currentTime);
			} else {
				started.add(started.remove(0));
			}
			
			ioWait.forEach((n) -> {
				n.updateWait(tempTimePassed);
				if(n.isReady()) {
					ioWait.remove(n);
					started.add(n);
				}
			});
			
		}		
		

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
	
	private void sendToWait(ArrayList<Job> started, ArrayList<Job> ioWaiting) {
		Job temp = started.remove(0);
		if(temp.getNext().equals("O") || temp.getNext().equals("I")){
			temp.waitFor(50);

		}
		else if(temp.getNext().equals("T")) {
			temp.waitFor(200);
			
		}
		else {
			System.out.println("Error here!!");
		}
		ioWaiting.add(temp);
	}
	
	private void addReadyJobs(ArrayList<Job> jobs, ArrayList<Job> started, int time) {
	
			while(started.size() < degree && jobs.size() > 0) {
				
				Job temp = jobs.remove(0); //Pop job from waiting queue, Need to consider time on waiting
				System.out.println(time + "\t"+temp.getName() + " loaded and ready");
				temp.setArrive(0); //This is the time that the job gets moved in
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
	
	public boolean noJobs() {
		
		return((jobs.size() < 1) && (started.size() < 1) && (ioWait.size() < 1));
		
	}
	
	

}
