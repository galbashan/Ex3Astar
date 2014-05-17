package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class Board extends Composite{
	
	int[][] boardData; // the data of the board
	int N;
	int length;
	Tile tiles[][];
	
	public Board(Composite parent, int style, int length) {
	
		super(parent, style); // Canvas c'tor
		this.length = length;
		boardData = new int[length][length];
		setLayout(new GridLayout(length, true));
		
		tiles = new Tile[length][length];
		
		// Initialize the tiles
		for (int i = 0; i < length; i++){
			for (int j = 0; j < length; j++){
				tiles[i][j] = new Tile(this, SWT.BORDER);
				tiles[i][j].setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true));
				tiles[i][j].setValue(boardData[i][j]);
				tiles[i][j].setEnabled(false);
			}
		}
	}
	
	public int[][] getBoardData() {
		return boardData;
	}

	public void setBoardData(int[][] data) {
		this.boardData = data;
		for (int i = 0; i < length; i++)
			for (int j = 0; j < length; j++)
				tiles[i][j].setValue(boardData[i][j]);
	}
}//