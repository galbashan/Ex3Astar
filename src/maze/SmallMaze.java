package maze;

import java.awt.Point;

public class SmallMaze extends Maze {
	private static final long serialVersionUID = 1L;
	
	int length=8;
	
	public SmallMaze() {
		matrix = new int[length][length];
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				matrix[i][j] = 0;
			}
		}
		matrix[0][0] = 1;
		matrix[7][7] = -2;
		matrix[0][6]= -1;
		matrix[0][7]= -1;
		matrix[1][1]= -1;
		matrix[1][2]= -1;
		matrix[1][4]= -1;
		matrix[2][1]= -1;
		matrix[2][2]= -1;
		matrix[2][6]= -1;
		matrix[3][3]= -1;
		matrix[3][4]= -1;
		matrix[4][1]= -1;
		matrix[4][3]= -1;
		matrix[5][5]= -1;
		matrix[5][6]= -1;
		matrix[6][0]= -1;
		matrix[6][3]= -1;
		matrix[7][0]= -1;
		matrix[7][1]= -1;
		matrix[7][4]= -1;
		matrix[7][5]= -1;
		matrix[7][6]= -1;
		this.setMaze(matrix);
		this.setStart(new Point(0,0));
		this.setGoal(new Point(7,7));
	}

//
	
}