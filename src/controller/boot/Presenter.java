package controller.boot;

import java.util.Observable;
import java.util.Observer;
import view.View;
import model.Model;


public class Presenter implements Observer{
	
	View ui;
	Model model;
	
	public Presenter(Model model, View ui) {
		this.model=model;
		this.ui=ui;
		ui.setLength(model.getLength());
	}
	
	// Update the model or the view according to the observable it gets
	public void update(Observable o, Object arg) {
			// Activate model's method according to the user command he got from the view
			if (o == ui){
				int input = ui.getUserCommand();
				switch (input) {
				case 100:
					model.undo();
					break;
				case 200:
					model.restartGame();
					break;
				case 300:
					model.loadGame(ui.getString());
					break;
				case 400:
					model.saveGame(ui.getString());
					break;
				case 500:
					for(int i=0; i<ui.getNum(); i++)
					{
						int c = model.connectServer(ui.getDepth());
						model.move(c);
					}
					break;
				case 1:
					model.MoveDownLeft();
					break;
				case 2:
					model.MoveDown();
					break;
				case 3:
					model.MoveDownRight();;
					break;
				case 4:
					model.MoveLeft();
					break;
				case 6:
					model.MoveRight();
					break;
				case 7:
					model.MoveUpLeft();
					break;
				case 8:
					model.MoveUp();
					break;
				case 9:
					model.MoveUpRight();
					break;
				default:
					break;
				}
			}
			// Update the view after the model performs changes
			if (o == model){
				ui.displayData(model.getData());
				ui.displayScore(model.getScore());
				ui.displayGameOver(model.getGameOver());
				ui.displayNoMoreMoves(model.getNoMoreMoves());
				ui.displayWin(model.isWin());
			}
		}
		
	}