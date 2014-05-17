package model.algorithms.solver;
import model.Model2048;

public class Solver {


	
	 public int findBestMove(Model2048 m){
		 
		 int bestDirection=0;
		 
		 Model2048 newM1 = new Model2048(m);
		 Model2048 newM2 = new Model2048(m);
		 Model2048 newM3 = new Model2048(m);
		 Model2048 newM4 = new Model2048(m);
		 
		 boolean flag1=newM1.MoveDown();
		 boolean flag2=newM2.MoveLeft();
		 boolean flag3=newM3.MoveRight();
		 boolean flag4=newM4.MoveUp();
		 
		if (newM1.getScore()>=newM2.getScore() && newM1.getScore()>=newM3.getScore() && newM1.getScore()>=newM3.getScore() && flag1 == true)
		{
			
			bestDirection = 1;
		}
		else
		{
			if (newM2.getScore()>=newM3.getScore() && newM2.getScore()>=newM4.getScore() && flag2==true)
			{
				bestDirection = 2;
			}
			else if(flag3 == true)
			{
				bestDirection = 3;
			}
		}
	    
		return bestDirection;
	 }
}

