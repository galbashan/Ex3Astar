package maze;

import java.awt.Point;

import model.algorithms.Action;
import model.algorithms.State;

public class MazeAction implements Action {
	
	private int dx;
	private int dy;

	public MazeAction(){
		
	}
	
	public MazeAction(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}

	@Override
	public State doAction(State state) {
		Point point = (Point) state.getState();
		State newState = new State();
		Point newPoint = new Point();

		newPoint.x = point.x + dx;
		newPoint.y = point.y + dy;

		newState.setState(newPoint);
		return newState;
	}

	@Override
	public String getName() {
		return "MazeAction:(" + dx + "," + dy + ")";
	}
}
