package controller.boot;

import view.ViewMaze;
import maze.SmallMaze;
import model.ModelMaze;


public class RunMaze {
	
	public static void main(String[] args) {
		
		ViewMaze ui = new ViewMaze();
		ModelMaze m = new ModelMaze(new SmallMaze());
		Presenter p = new Presenter(m, ui);
		ui.addObserver(p);
		m.addObserver(p);
		new Thread(ui).start();
		//
	}
}