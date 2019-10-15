import java.util.ArrayList;

public class Job {
	
	private String name;
	private int priority;
	private ArrayList<String> description;
	
	
	public Job(String name, int priority, ArrayList<String> desc) {
		this.name = name;
		this.priority = priority;
		this.description = desc;
		
	}
	
	public int getPriority() {
		return priority;
	}
	
	public String getNext() {
		return description.get(0);
	}
	
	public void pop() {
		description.remove(0);
	}
	
	public void update(String x) {
		description.set(0, x);
	}
	
	public String toString() {
		return name+" "+priority;
	}
	
	
}