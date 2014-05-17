package maze;

import java.awt.Point;
import java.util.ArrayList;

import model.algorithms.Action;
import model.algorithms.Domain;
import model.algorithms.State;

public class MazeDomain implements Domain {
	
	private Maze mMaze;
	
	public MazeDomain() {
		this.mMaze = new Maze();
	}
	
	public MazeDomain(Maze maze) {
		this.mMaze = maze;
	}

	
	public ArrayList<Action> getActions(State state) {
		Point point = (Point) state.getState();
		ArrayList<Action> actions = new ArrayList<Action>();
		
		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				if (dx == 0 && dy == 0)
					continue;
				int x = point.x + dx;
				int y = point.y + dy;
				if (mMaze.get(x, y) != -1) {
					actions.add(new MazeAction(dx, dy));
					
				}
			}
		}

		return actions;
	}

	@Override
	public State getStartState() {
		return mMaze.getStartState();
	}


	@Override
	public State getGoalState() {
		return mMaze.getGoalState();
	}
	

	public Maze getMaze() {
		return mMaze;
	}


	public void setMaze(Maze maze) {
		this.mMaze = maze;
	}
//
}

