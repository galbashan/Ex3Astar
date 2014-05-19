/* 
 * Copyright (C) 2014 Vasilis Vryniotis <bbriniotis at datumbox.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package model.algorithms.minimax;


import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Model2048;


public class Minimax implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Player vs Computer enum class
     */
    public enum Player {
        /**
         * Computer
         */
        COMPUTER, 

        /**
         * User
         */
        USER
    }
    
    
    public static int findBestMove(Model2048 m, int depth) throws CloneNotSupportedException {
        //Map<String, Object> result = minimax(theBoard, depth, Player.USER);
        
        Map<String, Integer> result = alphabeta(m, depth, -1, Integer.MAX_VALUE, Player.USER);
        
        return (int)result.get("Direction");
    }
    
 
    private static Map<String, Integer> minimax(Model2048 m, int depth, Player player) throws CloneNotSupportedException {
        Map<String, Integer> result = new HashMap<>();
        
       int bestDirection=0;
        int bestScore;
        
        if(depth==0 || m.isTerminate()) {
            bestScore=heuristicScore(m.getScore(),m.getEmptyCells(),calculateClusteringScore(m.getData()));
        }
        else {
            if(player == Player.USER) {
                bestScore = Integer.MIN_VALUE;

                for(int i=0; i<4; i++) {
                    Model2048 newM = new Model2048(m);

                    int points=newM.move(i);
                    
                    if(points==0 && newM.isEqual(m)) {
                    	continue;
                    }

                    Map<String, Integer> currentResult = minimax(newM, depth-1, Player.COMPUTER);
                    int currentScore=((Number)currentResult.get("Score")).intValue();
                    if(currentScore>bestScore) { //maximize score
                        bestScore=currentScore;
                        bestDirection=i;
                    }
                }
            }
            else {
                bestScore = Integer.MAX_VALUE;

                List<Integer> moves = m.getEmptyCellIds();
                if(moves.isEmpty()) {
                    bestScore=0;
                }
                int[] possibleValues = {2, 4};

                int i,j;
                for(Integer cellId : moves) {
                    i = cellId/m.getLength();
                    j = cellId%m.getLength();

                    for(int value : possibleValues) {
                    	Model2048 newM = new Model2048(m);
                        newM.setEmptyCell(i, j, value);

                        Map<String, Integer> currentResult = minimax(newM, depth-1, Player.USER);
                        int currentScore=((Number)currentResult.get("Score")).intValue();
                        if(currentScore<bestScore) { //minimize best score
                            bestScore=currentScore;
                        }
                    }
                }
            }
        }
        
        result.put("Score", bestScore);
        result.put("Direction", bestDirection);
        
        return result;
    }
    
   
    private static Map<String, Integer> alphabeta(Model2048 m, int depth, int alpha, int beta, Player player) throws CloneNotSupportedException {
        Map<String, Integer> result = new HashMap<>();
        
        int bestDirection = 0;
        int bestScore;
        
      if(m.isTerminate() == true)
      {
         if(m.isWin()) {
                bestScore=Integer.MAX_VALUE; //highest possible score
           }
          else {
               bestScore=Math.min(m.getScore(), 1); //lowest possible score
            }
      }
            //else
      else if(depth==0) {
            bestScore=heuristicScore(m.getScore(),m.getEmptyCells(),calculateClusteringScore(m.getData()));
        }
        	else {
            if(player == Player.USER) {
            	   for(int i=0; i<4; i++)
            	   {
            		   Model2048 newM= new Model2048(m);
            		   int points=newM.move(i);
            		   if(points==0 && newM.isEqual(m)) {
            			   continue;
            		   }
            		   Map<String, Integer> currentResult = alphabeta(newM, depth-1, alpha, beta, Player.COMPUTER);
            		   int currentScore=((Number)currentResult.get("Score")).intValue();           
            		   if(currentScore>alpha) { //maximize score ///
            			   alpha=currentScore;
            			   bestDirection=i;
            		   }
            		   if(beta<=alpha) {
            			   break; //beta cutoff
            		   }
                }
                
                bestScore = alpha;
            }
            else {
                List<Integer> moves = m.getEmptyCellIds();
                int[] possibleValues = {2, 4};

                int i,j;
                abloop: for(Integer cellId : moves) {
                    i = cellId/m.getLength();
                    j = cellId%m.getLength();

                    for(int value : possibleValues) {
                        Model2048 newM = new Model2048(m);
                        m.setEmptyCell(i, j, value);

                        Map<String, Integer> currentResult = alphabeta(newM, depth-1, alpha, beta, Player.USER);
                        int currentScore=((Number)currentResult.get("Score")).intValue();
                        if(currentScore<beta) { //minimize best score
                            beta=currentScore;
                        }
                        
                        if(beta<=alpha) {
                            break abloop; //alpha cutoff
                        }
                    }
                }
                
                bestScore = beta;
                
                if(moves.isEmpty()) {
                    bestScore=0;
                }
            }
        }
        
        result.put("Score", bestScore);
        result.put("Direction", bestDirection);
        
        return result;
    }
    
    /**
     * Estimates a heuristic score by taking into account the real score, the
     * number of empty cells and the clustering score of the board.
     * 
     * @param actualScore
     * @param numberOfEmptyCells
     * @param clusteringScore
     * @return 
     */
    private static int heuristicScore(int actualScore, int numberOfEmptyCells, int clusteringScore) {
        int score = (int) (actualScore+Math.log(actualScore)*numberOfEmptyCells -clusteringScore);
        return Math.max(score, Math.min(actualScore, 1));
    }
    
    /**
     * Calculates a heuristic variance-like score that measures how clustered the
     * board is.
     * 
     * @param boardArray
     * @return 
     */
    private static int calculateClusteringScore(int[][] boardArray) {
        int clusteringScore=0;
        
        int[] neighbors = {-1,0,1};
        
        for(int i=0;i<boardArray.length;++i) {
            for(int j=0;j<boardArray.length;++j) {
                if(boardArray[i][j]==0) {
                    continue; //ignore empty cells
                }
                
                //clusteringScore-=boardArray[i][j];
                
                //for every pixel find the distance from each neightbors
                int numOfNeighbors=0;
                int sum=0;
                for(int k : neighbors) {
                    int x=i+k;
                    if(x<0 || x>=boardArray.length) {
                        continue;
                    }
                    for(int l : neighbors) {
                        int y = j+l;
                        if(y<0 || y>=boardArray.length) {
                            continue;
                        }
                        
                        if(boardArray[x][y]>0) {
                            ++numOfNeighbors;
                            sum+=Math.abs(boardArray[i][j]-boardArray[x][y]);
                        }
                        
                    }
                }
                
                clusteringScore+=sum/numOfNeighbors;
            }
        }
        
        return clusteringScore;
    }

}
