package maze;

import java.util.ArrayList;

import model.algorithms.Action;
import model.algorithms.a_star.AStar;
//import model.algorithms.bfs.BFS;

public class MazeRun {

	public static void main(String[] args) {
		Maze maze = new Maze();
		maze.print();
		AStar as = new AStar(new MazeDomain(maze),new Maze_G_Distance(), new Maze_H_Distance());
		//BFS as = new BFS(new MazeDomain(maze),new GMaze());
		ArrayList<Action> actions = as.search(maze.getStartState(), maze.getGoalState());
		for (Action a : actions)
			System.out.println(a.getName());
	System.out.println("number of evaluated node:" + as.getNumOfEvaluatedNodes());
	}
}
