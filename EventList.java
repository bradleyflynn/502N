//
// EVENTLIST.JAVA
// Skeleton code for your EventList collection type.
//
import java.util.*;

class EventList {
    
    Random randseq;
    public final static Integer INF = Integer.MAX_VALUE;
    public ListEntry head, tail;
    public int entries;
    public int highestLevel;
    
    
    ////////////////////////////////////////////////////////////////////
    // Here's a suitable geometric random number generator for choosing
    // pillar heights.  We use Java's ability to generate random booleans
    // to simulate coin flips.
    ////////////////////////////////////////////////////////////////////
    
    int randomHeight()
    {
	int v = 1;
	while (randseq.nextBoolean()) { v++; }
	return v;
    }
    
    //
    // Constructor
    //
    public EventList()
    {
	randseq = new Random(58243); // You may seed the PRNG however you like.
	this.head = new ListEntry(-INF);		//	Start by creating the head an tail nodes 
	this.tail = new ListEntry(INF);			//	of your skip list
	
	this.entries = 0;						// 	Keeping track of the number of entries
	this.highestLevel = 0;					//	No entries -> highest level = 0
	this.head.level = 1;					//	head and tail heights are always
	this.tail.level = 1;					// 	going to be one higher than highest level 
	
	this.head.right = this.tail;			// the head points to the tail
    }

    public ListEntry find(int year)
    {
    	ListEntry p = this.head;				//////////////////////////////////////////////
    	while(true)								//	This find function searches for the 	//
    	{										//	lowest node that is less than			//
    		while(p.right.year <= year)			//	or equal to what you're searching		//
    			p = p.right;					//	for. So if your year isn't found, 		//
    		if (p.down != null) p = p.down;		//	it will return the one that should be 	//
    		else break;							//	to the left of it.						//
    	}										//////////////////////////////////////////////

    	return p;
    }
    
    public void addNewEmptyLevel()
    {
    	ListEntry h = new ListEntry(-INF);
		ListEntry t = new ListEntry(INF);		//////////////////////////////////////////
		h.right = t;							//	Just like the name of the method,	//
		h.down = this.head;						//	when this is called, it will		//
		t.down = this.tail;						//	create a new empty layer of 		//
		h.level = this.head.level;				//	head and tail nodes that will		//
		t.level = this.tail.level;				//	have the head point to the tail. 	//
		this.head = h;							//										//
		this.tail = t;							//	Keeps tack of levels for the list	//
		this.highestLevel += 1;					//	and head and tail nodes				//
		this.head.level += 1;					//////////////////////////////////////////
		this.tail.level += 1;

    }
    
    public ListEntry prevEntry(int level, int year)
    {
    	ListEntry p = this.head;
    	
    	int i = p.level;						//////////////////////////////////////////////////
    	while (i > level)						//	This function finds the previous ListEntry	// 
    	{										//	at any level in the list.					//
    		while (p.right.year < year)			//////////////////////////////////////////////////
    		{
    			p = p.right;
    		}
    		p = p.down;
    		i--;
    	}
    	while (p.right.year < year)
		{
			p = p.right;
		}
    	return p;
    }
    
    //
    // Add an Event to the list.
    //
    public void insert(Event e)
    {
    	ListEntry p = find(e.year);			//	Look for the event

    	if (p.year == e.year)				//	If found, we can just add  
    	{									//	the event to that node
    		p.events.add(e);			
    		this.entries += 1;
    		return;
    	}
    																	//////////////////////////////////////////////
    	ListEntry q = new ListEntry(e.year);							//	If we got here, this means we are		//
    	q.events.add(e);												//	adding a new node to our list.			//
    	q.level = this.randomHeight();									//	We'll give it some random height, and	//
    	while (q.level > this.highestLevel) this.addNewEmptyLevel();   	//	if it exceeds our highest level so far,	//
    	int i = 1;														//	we can add empty layers to accommodate. //
    																	//////////////////////////////////////////////	
    	ListEntry r = prevEntry(i, e.year);		//////////////////////////////////////////////
    	q.right = r.right;						//	Find what should be the previous 		//
    	r.right = q;							//	entry at level 1 and establish pointers	//
    	i++;									//////////////////////////////////////////////
    	
    	for (;i <= q.level; i++)
    	{
    		ListEntry t = new ListEntry(e.year);		//////////////////////////////////////////////
    		t.events.add(e);							//	Now if our randomly generated height	//
    		ListEntry s = prevEntry(i, e.year);			//	is greater than 1, we can go through 	//
    		t.level = q.level;							//	what each previous Entry at every level	//
    		t.right = s.right;							//	and "insert" our new node at that point	//
    		t.down = q;									//////////////////////////////////////////////
    		q = t;
    	}
    	
    	this.entries += 1;
    	return;	
    }

    
    //
    // Remove all Events in the list with the specified year.
    //
    public void remove(int year)
    {
    	ListEntry p = find(year);			//	A simple check if you're trying to 
    	if (p.year != year) return;			//	delete something not in the list.
    	
    	for (int i = p.level; i > 0; i--)				//////////////////////////////////////////
    	{												//	We found a node to delete, so we	// 
    		ListEntry q = prevEntry(i, year);			//	must go through every level of		//
    		q.right = p.right;							//	the list and dereference it			//
    	}												//////////////////////////////////////////
    	
    	this.entries -= 1;
    	return;
    }
    
    //
    // Find all events with greatest year <= input year
    //
    public Event [] findMostRecent(int year)
    {
    	ListEntry p = find(year);					//	Straightforward, find implements a 
    	if (p.events.isEmpty()) return null;		//	"most recent" type of search, which carries
    	return p.events.toArray(new Event[]{});    	//	over nicely
    }
    
    
    //
    // Find all Events within the specific range of years (inclusive).
    //
    public Event [] findRange(int first, int last)
    {
    	ListEntry p = find(first);						//////////////////////////////////////////////////////
    													//	Look for your first node, if it's not equal		//
    	List<Event> events = new ArrayList<Event>();	//	to the year, we haven't gone far enough, so 	//
    													//	a simple move to right solves this issue.		//
    	if (p.year < first) p = p.right;				//	The iteratively add the events from each		//
    	while (p.year <= last)							//	node to a grand output list, then convert it	// 
    	{												//	to an array.									//
	    	events.addAll(p.events);					//////////////////////////////////////////////////////
    		p = p.right;
    	}
    	if (events.isEmpty()) return null;
    	return events.toArray(new Event[]{});
    }
}
