import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.QuickFindUF;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


public class MainPrompt {
	
	public static void main(String[] args)
	{	
		int N = 0;
		
		StdOut.println("This is Project 1 - Percolation!\n");
		  
		StdOut.println("Enter the size of the NxN grid: \n");
	
		  N = StdIn.readInt();
		  
		  
		 //creates the NxN Grid 
		 Percolation PC = new Percolation(N); 
		 
		 //while loop variable determined by percolates function
		 boolean perc = false;
		 
		 while (perc != true) { //Monte Carlo Simulation
			 
			 int row = StdRandom.uniform(N) + 1; //[1,N]
			 int col = StdRandom.uniform(N) + 1;
			 
			 PC.open(row,col);
			 
			 perc = PC.percolates();
		 }
		 
		 StdOut.println("Number of Open Sites: " + PC.numberofOpenSites());
	
		 double p = (double)PC.numberofOpenSites() / (double)(N*N);
		 
		 StdOut.printf("Threshold(p*): %.4f", p);
	}

}
