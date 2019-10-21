import java.util.Comparator;

public class SortByShortest implements Comparator<Job>{
	
	public int compare(Job a, Job b) {
		int result = Integer.parseInt(a.getNextCpuBurst()) - Integer.parseInt(b.getNextCpuBurst());
		return result;
	}

}
