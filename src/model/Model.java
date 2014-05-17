package model;


public interface Model {
	
	boolean MoveUp();
	boolean MoveDown();
	boolean MoveLeft();
	boolean MoveRight();
	void MoveUpRight();
	void MoveUpLeft();
	void MoveDownRight();
	void MoveDownLeft();
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
	void hint();
}
