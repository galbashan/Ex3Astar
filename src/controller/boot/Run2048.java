package controller.boot;


import view.View2048;
import model.Model2048;


public class Run2048 {
	
	public static void main(String[] args) {
		
		View2048 ui = new View2048();
		Model2048 m = new Model2048();
		Presenter p = new Presenter(m, ui);
		ui.addObserver(p);
		m.addObserver(p);
		new Thread(ui).start();
	}	
}