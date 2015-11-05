//
// LISTENTRY.JAVA
//
import java.util.*;

public class ListEntry {
												//////////////////////////////////////////////
	public int year;							//	Created a new class to help with 		//
	public int level;							//	list operations. In a singly-linked		//
    public ListEntry down, right;				//	list, we only need to look down and		//
    public List<Event> events;					//	to the right, which saves on memory.	//
    											//////////////////////////////////////////////
	public ListEntry(int year) {					
		this.year = year;						
		this.events = new ArrayList<Event>();
	}

}
