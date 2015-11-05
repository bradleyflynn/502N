public class ClosestPairNaive {
    
    public final static double INF = java.lang.Double.POSITIVE_INFINITY;
    
    //
    // findClosestPair()
    //
    // Given a collection of nPoints points, find and ***print***
    //  * the closest pair of points
    //  * the distance between them
    // in the form "NAIVE (x1, y1) (x2, y2) distance"
    //
    
    // INPUTS:
    //  - points sorted in nondecreasing order by X coordinate
    //  - points sorted in nondecreasing order by Y coordinate
    //
    
    public static void findClosestPair(XYPoint points[], boolean print)
    {
	// int nPoints = points.length;
	
	//
	// Your code goes here!
	//
	
	// if (print)
	//   System.out.println("NAIVE " + ...);
    	double minDist = INF;
    	int j = 0;
    	int n = points.length;
    	XYPoint[] pair = new XYPoint[2];
    	
    	while (j <= n-2)
    	{
    		int k = j+1;
    		while (k <= n-1) 
    		{
    			double d = points[j].dist(points[k]);
    			if (d < minDist)
    			{
    				minDist = d;
    				pair[0] = points[j];
    				pair[1] = points[k];
    			}
    			k++;
    		}
    		j++;
    	}
    	
    	if (print) System.out.println("NAIVE " + pair[0] + 
    			" " + pair[1] + " " + minDist);
    	
    }
}
