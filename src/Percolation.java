import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {
	
	private Site[] siteArray;
	private Site virtualTop = new Site();
	private Site virtualBottom = new Site();
	int rowLength = 0;
	private WeightedQuickUnionUF WQU;
	private int numOfOpenSites = 0;
	
	public class Site {        //class to hold data within the siteArray
		public boolean isOpen;
		public boolean isFull;
		public int id;
		
		public Site()
		{
			this.isOpen = false;
			this.isFull = false;
		}
	}
	
	public Percolation(int N)
	{
		if ( N < 1 ) throw new IllegalArgumentException("Invalid Grid Length. (N > 0)");
		
		siteArray = new Site[N*N+2];
		int k = 1;
		this.rowLength = N;
		this.virtualTop.id = 0; //need these to refer to all full elements in the first and last rows
		this.virtualBottom.id = N*N+1;
		
		WQU = new WeightedQuickUnionUF(N*N+2); /**creates id array in QuickFind. access granted for
											union, connect, and find functions.
		                                   */
		siteArray[0] = new Site();
		siteArray[0] = virtualTop;
		siteArray[N*N+1] = new Site();
		siteArray[N*N+1] = virtualBottom;
		
		for (int i=1; i<=N*N; i++) {	 //initialize boolean array isOpen with boolean objects site all set to false(blocked) 
				
			Site site = new Site();
			siteArray[i] = new Site();
				
			siteArray[i] = site; // initialized to blocked
			siteArray[i].id = k; /*given unique site name (used to refer to the index of the
				                         id array in QuickFind */
			
			     if ( i <= rowLength )         WQU.union(siteArray[i].id, virtualTop.id);//virtual top site
			else if ( (N*N)+1-i <= rowLength ) WQU.union(siteArray[i].id, virtualBottom.id); //virtual bottom site
			k++;
		}
	}
	
	//converts a 2d index into a 1d index
	public int siteIndex(int row, int col) throws IndexOutOfBoundsException
	{
		if (row < 1 && row > rowLength || col < 1 && col > rowLength)
		 throw new IndexOutOfBoundsException(row +" or "+col+" out of bounds");
		
		else return (row-1)*rowLength+(col); 
	}
	
	public void open(int row, int col) 
	{	
		//make sure that row and col are valid indices range= [1,rowLength]
		if (row < 1 && row > rowLength || col < 1 && col > rowLength)
			 throw new IndexOutOfBoundsException(row +" or "+col+" out of bounds");
		
		//check if the site is already open
		if ( isOpen(row,col) == true) return;  
				
		//open the site
		siteArray[siteIndex(row,col)].isOpen = true; 
		this.numOfOpenSites++;
		
		//set topmost open sites to full
		if (row == 1) siteArray[siteIndex(row,col)].isFull = true; 
		
		//bounds checking for adjacent site unioning
		if (row - 1 >= 1) 
		{
			if (isOpen(row-1,col) == true) WQU.union(siteArray[siteIndex(row,col)].id ,siteArray[siteIndex(row-1,col)].id);
				 if (isFull(row-1,col) == true) siteArray[siteIndex(row,col)].isFull = true;
		}
		
		if (row + 1 <= rowLength) 
		{
			if (isOpen(row+1,col) == true) WQU.union(siteArray[siteIndex(row,col)].id ,siteArray[siteIndex(row+1,col)].id);
				 if (isFull(row+1,col) == true) siteArray[siteIndex(row,col)].isFull = true;
		}
		
		if (col - 1 >= 1) 
		{
			if (isOpen(row,col-1) == true) WQU.union(siteArray[siteIndex(row,col)].id ,siteArray[siteIndex(row,col-1)].id);
				 if (isFull(row,col-1) == true) siteArray[siteIndex(row,col)].isFull = true;
		}
		
		if (col + 1 <= rowLength) 
		{
			if (isOpen(row,col+1) == true) WQU.union(siteArray[siteIndex(row,col)].id ,siteArray[siteIndex(row,col+1)].id);
				 if (isFull(row,col+1) == true) siteArray[siteIndex(row,col)].isFull = true;
		}
		return;
	}
	
	//check if the site in the site array is full
	public boolean isFull(int row, int col) 
	{
		//make sure that row and col are valid indices range= [1,rowLength]
		if (row < 1 && row > rowLength || col < 1 && col > rowLength)
			 throw new IndexOutOfBoundsException(row +" or "+col+" out of bounds");
				
		if (siteArray[siteIndex(row,col)].isFull == false) return false;
		else return true;
	}
	
	//function to check if the index in the site array is open
	public boolean isOpen(int row, int col) 
	{
		//make sure that row and col are valid indices range= [1,rowLength]
		if (row < 1 && row > rowLength || col < 1 && col > rowLength)
			 throw new IndexOutOfBoundsException(row +" or "+col+" out of bounds");
		
		if (siteArray[siteIndex(row,col)].isOpen == false) return false;
		else return true;
	}
	
	//returns number of open sites(including full sites)
	public int numberofOpenSites() 
	{
		return numOfOpenSites;
	}
	
	//determines if the flow percolates or not
	public boolean percolates() 
	{
		 if (WQU.connected(virtualTop.id, virtualBottom.id)) {
			 StdOut.println("Flow Percolated!!!");
			 return true;
			 }
		 else return false;
	}
}

