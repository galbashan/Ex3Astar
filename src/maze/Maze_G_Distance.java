package maze;

import java.awt.Point;

import model.algorithms.Distance;
import model.algorithms.State;

public class Maze_G_Distance implements Distance {
	
	// Calculate the value of the move.
	// 10 for up, down, left or right.
	// 15 for up-right, up-left, down-right, down-left.
	@Override
	public double getDistance(State from, State to) {
		Point pFrom = (Point) from.getState();
		Point pTo = (Point) to.getState();

		int dx = pTo.x - pFrom.x;
		int dy = pTo.y - pFrom.y;

		if (dx == 0 || dy == 0)
			return 10;
		return 15;
	}

}
