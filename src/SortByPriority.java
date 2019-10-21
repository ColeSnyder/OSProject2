import java.util.Comparator;

public class SortByPriority implements Comparator<Job> {
	
	public int compare(Job a, Job b) {
		int result = a.getPriority() - b.getPriority();
		return result;
	}

}
