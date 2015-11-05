import java.util.Arrays;

public class ClosestPairDC {
    
    public final static double INF = java.lang.Double.POSITIVE_INFINITY;

    //
    // findClosestPair()
    //
    // Given a collection of nPoints points, find and ***print***
    //  * the closest pair of points
    //  * the distance between them
    // in the form "DC (x1, y1) (x2, y2) distance"
    //
    
    // INPUTS:
    //  - points sorted in nondecreasing order by X coordinate
    //  - points sorted in nondecreasing order by Y coordinate
    //
    
    public static Object[] combine(XYPoint[] ptsByY, XYPoint midPoint, 
    		int n, double lrDist, XYPoint[] pair)
    {
    	XYPoint[] yStrip = new XYPoint[n];
    	
    	//
    	//	Creating your y-strip 
    	//
    	int i = 0;
    	for (int l = 0; l < ptsByY.length; l++)
    	{
    		if (Math.abs(ptsByY[l].x-midPoint.x) < lrDist)
    		{
    			yStrip[i] = ptsByY[l];
    			i++;
    		}
    	}
 
    	double minDist = lrDist;
    	
    	for (int j = 0; j < i-1; j++)
    	{
    		int k = j+1;
    		while (k < i && (yStrip[k].y - yStrip[j].y) < lrDist)
    		{
    			double d = yStrip[j].dist(yStrip[k]);
    			if (d < minDist)
    			{
    				minDist = d;
    				pair[0] = yStrip[j];
    				pair[1] = yStrip[k];
    			}
    			k++;
    		}
    	}
    	return new Object[] {pair[0], pair[1], pair[0].dist(pair[1])};
    }
    
    public static Object[] closestPair(XYPoint[] ptsByX, XYPoint[] ptsByY)
    {
    	int n = ptsByX.length;
    	// base cases
    	if (n == 1) return new Object[] {ptsByX[0],ptsByX[0], INF};
    	if (n == 2) return new Object[] {ptsByX[0],ptsByX[1], ptsByX[0].dist(ptsByX[1])};
       	
    	int mid = n/2;
    	
    	//split the arrays
    	XYPoint[] XL = Arrays.copyOfRange(ptsByX, 0, mid);
    	XYPoint[] XR = Arrays.copyOfRange(ptsByX, mid, n);
    	
    	XYPoint[] YL = new XYPoint[XL.length];
    	XYPoint[] YR = new XYPoint[XR.length];
    	
    	int j = 0;
    	int k = 0;
    	for (int i = 0; i < n; i++)
    	{
    		if (ptsByY[i].isLeftOf(ptsByX[mid]))
    		{
    			YL[j] = ptsByY[i];
    			j++;
    		}
    		else
    		{
    			YR[k] = ptsByY[i];
    			k++;
    		}
    	}
    	
    	//recursive call
    	Object[] distL = closestPair(XL,YL);
    	Object[] distR = closestPair(XR,YR);
    	
    	if ((double)distL[2] < (double)distR[2]) 
    		{
    		XYPoint[] pair = {(XYPoint)distL[0],(XYPoint)distL[1]}; 
    		return combine(ptsByY, ptsByX[mid],n, (double)distL[2], pair);
    		}
    	else 
    		{
    		XYPoint[] pair = {(XYPoint)distR[0],(XYPoint)distR[1]};
    		return  combine(ptsByY, ptsByX[mid], n, (double)distR[2], pair);
    		}
    }
    
    public static void findClosestPair(XYPoint pointsByX[], 
				       XYPoint pointsByY[],
				       boolean print)
    {
	   	Object[] ans = closestPair(pointsByX, pointsByY);
    	
    	if (print) System.out.println("DC " + ans[0] + " " + ans[1] + " " + ans[2]);
    }
}
