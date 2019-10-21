import java.util.Comparator;

public class SortByShortest implements Comparator<Job>{
	
	public int compare(Job a, Job b) {
		int result = Integer.parseInt(a.getNext()) - Integer.parseInt(b.getNext());
		return result;
	}

}
