import java.util.ArrayList;
import java.util.Collections;

public class PCB {
	
	int time;
	int degree;
	ArrayList<Job> jobs;
	ArrayList<Job> started;
	ArrayList<Job> ioWait;
	
	int waitTime;
	
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
		
		initString(x);
		
		int counter = 0;
		
		this.sortingType = x;
		System.out.println("Time \tEvent");
		System.out.println("--------------------");
		int time = this.time;
		int currentTime = 0;
		
		ArrayList<Job> jobs = this.jobs;
		ArrayList<Job> started = this.started;
		addReadyJobsInit(jobs, started);
			
		while(!(noJobs())) {
			
			boolean iot = false;
			String currentJob = "";
			
		int timePassed = 0;
			sort();
			
			if(started.size() > 0) {
				
				currentJob = started.get(0).getName();
					int currentJobSize = Integer.parseInt(started.get(0).getNext());
					
					if(Integer.parseInt(started.get(0).getNext()) <= time) { 
						
						System.out.println(currentTime + "\t" + started.get(0).getName() + " running");
						currentTime += currentJobSize;
						started.get(0).pop();
						timePassed = currentJobSize;
						started.get(0).addCPUTime(timePassed);			
						
						

						try {
							if(started.get(0).getNext().equals("I") || started.get(0).getNext().equals("O") || started.get(0).getNext().equals("T")) {
								System.out.println(currentTime + "\t" + started.get(0).getName() + " needs " + started.get(0).getNext());
								iot = true;
								sendToWait(started, ioWait);
							} else {
								System.out.println(currentTime + "\t" + started.get(0).getName() + " timed out" + "\t" + started.get(0).getName() + " ready");
							}
						} catch(Exception e) {

						}
						
						
						
					} else if(Integer.parseInt(started.get(0).getNext()) > time) {
						
					
						System.out.println(currentTime + "\t" + started.get(0).getName() + " running");
						started.get(0).update((currentJobSize - time)+"");
						currentTime += time;
						timePassed = time;
						started.get(0).addCPUTime(timePassed);
					
						
						
						try {
							if(started.get(0).getNext().equals("I") || started.get(0).getNext().equals("O") || started.get(0).getNext().equals("T")) {
								System.out.println(currentTime + "\t" + started.get(0).getName() + " needs " + started.get(0).getNext());
								iot = true;
								sendToWait(started, ioWait);
							} else {
								System.out.println(currentTime + "\t" + started.get(0).getName() + " timed out" + "\t" + started.get(0).getName() + " ready");
							}
						} catch(Exception e) {

						}
						
					}
				
			}
			
			if(started.size() <= 0) {
				
				currentTime += time;
				timePassed = time;
			}
			
			final int tempTimePassed = timePassed;
			waitTime = currentTime;
			
			if(started.size()>0) {
				if (started.get(0).finished()) {
					System.out.println(currentTime + "\t" + started.get(0).getName() + " done");
					
					started.get(0).setComplete(currentTime);
					finished[counter++] = started.remove(0);
					addReadyJobs(jobs, started, currentTime);
				}
				else if (!iot){
					if(this.sortingType == 0) {
						started.add(0,started.remove(0));
					}
					else if(this.sortingType == 1){
						started.add(started.remove(0));
					}
					else {
						
					}
					
				}
			}
			
			ArrayList<Job> tempUnload = new ArrayList<Job>();
			
			
			
			if(started.size() <= 0) {
				currentJob = "";
			}
			
			final String CURRENT_JOB = currentJob;
			
				ioWait.forEach((n) -> {
					n.updateWait(tempTimePassed, CURRENT_JOB);
					if(n.isReady()) {
						System.out.println();
						System.out.println(waitTime + "\t" + "I/O complete" + "\t" + n.getName() + " ready");
						System.out.println();
						tempUnload.add(n);
						started.add(n);
					}
				});
			
			
			tempUnload.forEach((n) -> {
				ioWait.remove(n);
			});
			
		}		
		
		System.out.println();
		System.out.println("All jobs done at "+currentTime);
		System.out.println();
		finalPrint();
	}
	
	private void sort() {
		switch(sortingType) {
		case 0:
			//Round Robin No sorting possibly pop first and add to end?
			break;
		case 1:
			Collections.sort(started, new SortByShortest()); //Shortest Job First
			break;
		case 2:
			Collections.sort(started, new SortByPriority()); //Priority Jobs First
			break;
		}
	}
	
	private void sendToWait(ArrayList<Job> started, ArrayList<Job> ioWaiting) {
		Job temp = started.remove(0);
		
		if(temp.getNext().equals("O") || temp.getNext().equals("I")){
			temp.waitFor(50);
			temp.pop();		
			temp.addIOTime(50);
			
		}
		else if(temp.getNext().equals("T")) {
			temp.waitFor(200);
			temp.pop();
			temp.addIOTime(200);
			
		}
		else {
			System.out.println("Error here!!");
		}
		ioWaiting.add(temp);
	}
	
	private void addReadyJobs(ArrayList<Job> jobs, ArrayList<Job> started, int time) {
	
			while(started.size() < degree && jobs.size() > 0) {
				
				Job temp = jobs.remove(0); //Pop job from waiting queue, Need to consider time on waiting
				System.out.println();
				System.out.println(time + "\t"+temp.getName() + " loaded and ready");
				System.out.println();
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
		
		System.out.println();

	}
	
	protected void finalPrint() {
		System.out.println("Job \tArrival Time \tLoad Time \tCompletion Time \tCpu Time \tTime For I/O \tTime Spent Ready");
		for(Job x : finished) {
			System.out.println(x);
		}
	}
	
	public boolean noJobs() {
		
		return((jobs.size() < 1) && (started.size() < 1) && (ioWait.size() < 1));
		
	}
	
	public void initString(int x) {
		if(x == 0) {
			System.out.println("RR/FCFS:");
		}
		else if(x ==1) {
			System.out.println("SJF:");
		}
		else {
			System.out.println("P:");
		}
	}
	
	

}
