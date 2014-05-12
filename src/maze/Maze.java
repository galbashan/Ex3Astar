package maze;

import java.awt.Point;
import java.io.Serializable;

import model.algorithms.State;

	
public class Maze implements Serializable{
	private static final long serialVersionUID = 1L;
	private Point start;
	private Point goal;
	
	int[][] matrix =  {
			 { 1, 0, -1 },
			{ -1, 0, 0, },
			{ 0, -1, 0 }, 
			{0,-1,2}};
	
	
	public Maze() {
		start = new Point(0,0);
		goal = new Point(3,2);
	}
	
	public Maze(Maze maze) {
		matrix = maze.getMaze();
		start = maze.getStart();
		goal = maze.getGoal();
	}

	public int get(int x, int y) {
		if (y >= matrix[0].length || y < 0 || x >= matrix.length || x < 0)
			return -1;
		return matrix[x][y];
	}

	public State getStartState() {
		State state = new State();
		state.setState(start);
		return state;
	}

	public State getGoalState() {
		State state = new State();
		state.setState(goal);
		return state;
	}
	
	public void print() {
		for (int i=0; i < matrix.length; i++ )
		{
			for (int j=0; j < matrix[0].length; j++ )
			{
				System.out.print(matrix[i][j]+" ");
			}
			System.out.println();
		}
	}
	
	public int[][] getMaze() {
		return matrix;
	}
	
	public void setMaze(int[][] matrix) {
		this.matrix= matrix;
	}
	
	public void setMaze(Maze maze) {
		this.matrix= maze.getMaze();
	}

	public Point getStart() {
		return start;
	}

	public void setStart(Point start) {
		this.start = start;
	}

	public Point getGoal() {
		return goal;
	}

	public void setGoal(Point goal) {
		this.goal = goal;
	}
	
	
}
