package model;


public interface Model {
	
	boolean MoveUp();
	boolean MoveDown();
	boolean MoveLeft();
	boolean MoveRight();
	boolean MoveUpRight();
	boolean MoveUpLeft();
	boolean MoveDownRight();
	boolean MoveDownLeft();
	int[][] getData();
	void undo();
	void restartGame();
	int getScore();
	boolean saveGame(String str);
	boolean loadGame(String str);
	public boolean getGameOver();
	public boolean getNoMoreMoves();
	public boolean isWin();
	public int getLength();
	int minimax();
	int move(int i);
	int connectServer();
}
