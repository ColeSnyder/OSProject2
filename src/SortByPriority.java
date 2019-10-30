import java.util.Comparator;

public class SortByPriority implements Comparator<Job> {
	
	public int compare(Job a, Job b) {
		int result;

		
		result = a.getPriority() - b.getPriority();
		if(result == 0) {
			result = 1;
		}
		return result;
	}

}
