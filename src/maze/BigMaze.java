package maze;

import java.awt.Point;

public class BigMaze extends Maze{
	private static final long serialVersionUID = 1L;
	int length=16;

	public BigMaze() {
		int[][] matrix = new int[length][length];
		for (int i = 1; i < length; i++) {
			matrix[1][i] = -1;
			matrix[i][1] = -1;
		}
		
		matrix[15][1] = 0;
		matrix[1][15] = 0;
		matrix[0][0] = -2;
		matrix[15][15] = 1;
		this.setMaze(matrix);
		this.setStart(new Point(15,15));
		this.setGoal(new Point(0,0));
	}
	
}