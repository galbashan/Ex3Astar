package model.algorithms.solver;
import java.io.Serializable;

import model.Model2048;


public class Solver implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int nextMove=1;
	
	 public int findBestMove(Model2048 m){
		 return solver(m); 
	   
	 }
	 
	 public int solver(Model2048 m)
	 {
		
		 Model2048[][] arr = new Model2048[4][4];
		 int bestScore=0;
		 int bestDirection=0;
		 
		 for(int i=0; i<m.getLength(); i++)
			 for(int j=0; j<m.getLength(); j++)
			 {
				 arr[i][j] = new Model2048(m);
				 arr[i][j].move(nextMove);
				 arr[i][j].move(i);
				 arr[i][j].move(j);
				 if(arr[i][j].getScore() > bestScore)
				 {
					 bestScore = arr[i][j].getScore();
					 bestDirection = i;
				 }
			 }
		int temp=nextMove;
		nextMove=bestDirection;
		return temp;
		 
	 }
}

