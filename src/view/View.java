package view;

public interface View {
	
	void displayData(int[][]data);
	void displayScore(int score);
	void displayGameOver(boolean gameOver);
	void displayNoMoreMoves(boolean noMoreMoves);
	int getUserCommand();
	String getString();
	void displayWin(boolean win);
	void setLength(int length);
}