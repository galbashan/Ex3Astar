package model;


public interface Model {
	
	void MoveUp();
	void MoveDown();
	void MoveLeft();
	void MoveRight();
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
}
