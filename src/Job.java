import java.util.ArrayList;
import java.util.Arrays;

public class Job{
	
	private String name;
	private int priority;
	private ArrayList<String> description;
	
	private int waitingFor;
	
	private int arrivalTime;
	private int loadTime;
	private int completeTime;
	private int cpuTime;
	private int timeIO;
	private int timeSpentReady;
	
	
	public Job(String name, int priority, ArrayList<String> desc) {
		this.name = name;
		this.priority = priority;
		this.description = desc;
		
	}
	
	public void waitFor(int time) {
		waitingFor = time;
	}
	
	public boolean isReady() {
		return waitingFor <= 0;
	}
	
	public void setArrive(int time) {
		arrivalTime = time;
	}
	
	public void setLoad(int time) {
		loadTime = time;
	}
	
	public String getName() {
		return name;
	}
	
	public int getPriority() {
		return priority;
	}
	
	public boolean finished() {
		return description.size() <= 0;
	}
	
	public String getNext() {
		return description.get(0);
	}
	
	public void updateWait(int time) {
		waitingFor -= time;
	}
	
	public void addCPUTime(int time) {
		cpuTime += time;
	}
	
	public void setComplete(int time) {
		completeTime = time;
	}
	
	public int getDescription() {
		return description.size();
	}
	
	public String getNextCpuBurst() {
		if(description.get(0).equals("O") || description.get(0).equals("I") || description.get(0).equals("T")) {
			if(description.get(1) != null) {
				return description.get(1);
			}
			else {
				System.out.println("Check line 65 JOB, did not return correct cpu burst");
				return "";
			}
		}
		else {
			return description.get(0);
		}
	}
	
	public void pop() {
		description.remove(0);
	}
	
	public void update(String x) {
		description.set(0, x);
	}
	
	public String toString() {
		return name+"\t"+arrivalTime+"\t\t"+loadTime+"\t\t"+completeTime+"\t\t\t"+cpuTime+"\t\t"+timeIO+"\t\t"+timeSpentReady;
	}
	
	
}
