package maze;

import java.awt.Point;

import model.algorithms.Distance;
import model.algorithms.State;

public class Maze_H_Distance implements Distance {
	
	// Calculate the air distance between two points.
	@Override
	public double getDistance(State from, State to) {
		Point pFrom = (Point) from.getState();
		Point pTo = (Point) to.getState();

		int dx = Math.abs(pTo.x - pFrom.x);
		int dy = Math.abs(pTo.y - pFrom.y);
		return Math.sqrt(Math.pow(dx,2) + Math.pow(dy,2))*10;
	}

}
