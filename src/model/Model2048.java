package model;

import java.awt.Point;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Random;

import javax.jws.soap.InitParam;

import model.algorithms.minimax.Minimax;


public class Model2048 extends Observable implements Model {
	
	private int N;
	private int[][] data;
	private boolean[][] dataflag;
	private LinkedList<int[][]> undodata;
	private LinkedList<Integer> undoscore;
	private int score;
	private boolean terminate;
	private boolean gameOver;
	private boolean noMoreMoves;
	private boolean win;
	private Minimax minimax;
	
	// Default c'tor
	public Model2048(){
		N=4;
		score = 0;
		gameOver = false;
		terminate = false;
		noMoreMoves = false;
		win = false;
		data =  new int[N][N];
		dataflag =  new boolean[N][N];
		for (int i=0; i < N; i++ )
			for (int j=0; j < N; j++ )
			{
				data[i][j]=0;
				dataflag[i][j]=true;
			}
		undodata = new LinkedList<int[][]>();
		undoscore = new LinkedList<Integer>();
		minimax = new Minimax();
	}
	
	// copy c'tor
	public Model2048(Model2048 model){
		this.setData(model.dataClone());
		N=4;
		score = model.getScore();
		gameOver = model.getGameOver();
		noMoreMoves = model.getNoMoreMoves();
		terminate = model.isTerminate();
		win = model.isWin();
		dataflag =  new boolean[N][N];
		initFlag();
		undodata = model.getUndodata();
		undoscore = model.getUndoscore();
		minimax=model.getminimax();

	}
	
	// Moves
	@Override
	public void MoveUp() {
		undodata.addLast(dataClone());
		undoscore.addLast(scoreClone());
		initFlag();
		noMoreMoves = false;
		boolean flag = true;
		int count = 0;
		while (flag == true)
		{
			flag = false;
			for(int i=1; i<N; i++)
			{
				for(int j=0; j<N; j++)
				{
					if (data[i][j]==0)
						continue;
					else
						if (data[i-1][j] == 0)
						{
							data[i-1][j]=data[i][j];
							data[i][j]=0;
							flag = true;
							count++;
						}
						else if ((data[i-1][j] == data[i][j]) && (dataflag[i-1][j] == true)
								&& (dataflag[i][j] == true))
						{
							score=score+data[i][j]*2;
							data[i-1][j]=data[i][j]*2;
							dataflag[i-1][j]=false;
							data[i][j]=0;
							checkWin(data[i-1][j]);
							flag = true;
							count++;
						}
				}
			}
		}
		if (count !=0)
			addBrick();
		else{
			data=undodata.removeLast();
			score=undoscore.removeLast();
		}
		checkLoose();
		setChanged();
		notifyObservers();	
	}

	@Override
	public void MoveDown() {
		undodata.addLast(dataClone());
		undoscore.addLast(scoreClone());
		initFlag();
		noMoreMoves = false;
		boolean flag = true;
		int count = 0;
		while (flag == true)
		{
			flag = false;
			for(int i=N-2; i>-1; i--)
			{
				for(int j=0; j<N; j++)
				{
					if (data[i][j]==0)
						continue;
					else
						if (data[i+1][j] == 0)
						{
							data[i+1][j]=data[i][j];
							data[i][j]=0;
							flag = true;
							count++;
						}
						else if ( (data[i+1][j] == data[i][j]) && (dataflag[i+1][j] == true)
								&& (dataflag[i][j] == true))
						{
							score=score+data[i][j]*2;
							data[i+1][j]=data[i][j]*2;
							dataflag[i+1][j]=false;
							data[i][j]=0;
							checkWin(data[i+1][j]);
							flag = true;
							count++;
						}
				}
			}
		}
		
		if (count !=0)
			addBrick();
		else{
			data=undodata.removeLast();
			score=undoscore.removeLast();
		}
		checkLoose();
		setChanged();
		notifyObservers();
	}

	@Override
	public void MoveLeft() {
		undodata.addLast(dataClone());
		undoscore.addLast(scoreClone());
		initFlag();
		noMoreMoves = false;
		boolean flag = true;
		int count = 0;
		while (flag == true)
		{
			flag = false;
			for(int i=0; i<N; i++)
			{
				for(int j=1; j<N; j++)
				{
					if (data[i][j] == 0)
						continue;
					else
						if (data[i][j-1] == 0)
						{
							data[i][j-1]=data[i][j];
							data[i][j]=0;
							flag = true;
							count++;
						}
						else if ((data[i][j-1] == data[i][j]) && (dataflag[i][j-1] == true)
								&& (dataflag[i][j] == true))
						{
							score=score+data[i][j]*2;
							data[i][j-1]=data[i][j]*2;
							dataflag[i][j-1]=false;
							data[i][j]=0;
							checkWin(data[i][j-1]);
							flag = true;
							count++;

						}
				}
			}
		}
		
		if (count !=0)
			addBrick();
		else{
			data=undodata.removeLast();
			score=undoscore.removeLast();
		}
		checkLoose();
		setChanged();
		notifyObservers();
	}

	@Override
	public void MoveRight() {
		undodata.addLast(dataClone());
		undoscore.addLast(scoreClone());
		initFlag();
		noMoreMoves = false;
		boolean flag = true;
		int count = 0;
		while (flag == true)
		{
			flag = false;
			for(int i=0; i<N; i++)
			{
				for(int j=N-2; j>-1; j--)
				{
					if (data[i][j] ==0)
						continue;
					else
						if (data[i][j+1] == 0)
						{
							data[i][j+1]=data[i][j];
							data[i][j]=0;
							flag = true;
							count++;
						}
						else if ((data[i][j+1] == data[i][j])&& (dataflag[i][j+1] == true)
								&& (dataflag[i][j] == true))
						{
							score=score+data[i][j]*2;
							data[i][j+1]=data[i][j]*2;
							dataflag[i][j+1]=false;
							data[i][j]=0;
							checkWin(data[i][j+1]);
							flag = true;
							count++;
						}
				}
			}
		}
		if (count !=0)
			addBrick();
		else{
			data=undodata.removeLast();
			score=undoscore.removeLast();
		}
		checkLoose();
		setChanged();
		notifyObservers();
	}

	// Perform undo
	public void undo()
	{
		if (undodata.size() != 0){
			data=undodata.removeLast();
			score=undoscore.removeLast();
		}
		else {
			noMoreMoves = true;
		}
		setChanged();
		notifyObservers();
	}
	
	
	public int[][] getData() {
		return data;
	}
	
	public void print() {
		for (int i=0; i < data.length; i++ )
		{
			for (int j=0; j < data[0].length; j++ )
			{
				System.out.print(data[i][j]+" ");
			}
			System.out.println();
		}
	}
	
	public int getEmptyCells()
	{
		HashMap<Integer, Point> freepoints = new HashMap<Integer, Point>();
		int count = 0;
		for (int i=0; i < data.length; i++ )
			for (int j=0; j < data[0].length; j++ )
				if (data[i][j] == 0)
				{
					freepoints.put(count, new Point(i,j));
					count++;
				}
		return count;
	}
	
	public List<Integer> getEmptyCellIds() {
        List<Integer> cellList = new ArrayList<>();
        
        for(int i=0;i<data.length;++i) {
            for(int j=0;j<data[0].length;++j) {
                if(data[i][j]==0) {
                    cellList.add(data.length*i+j);
                }
            }
        }
        return cellList;
    }
	
	 public void setEmptyCell(int i, int j, int value) {
	        if(data[i][j]==0) {
	            data[i][j]=value;
	        }
	    }
	
	// Add new break after user's action
	private boolean addBrick()
	{
		HashMap<Integer, Point> freepoints = new HashMap<Integer, Point>();
		int count = 0;
		for (int i=0; i < data.length; i++ )
			for (int j=0; j < data[0].length; j++ )
				if (data[i][j] == 0)
				{
					freepoints.put(count, new Point(i,j));
					count++;
				}
		Random rand = new Random();
		int x = rand.nextInt(count);
		Point p=freepoints.get(x);
		x=(rand.nextInt(10));
		if(x==0)
			data[(int) p.getX()][(int) p.getY()]=4;
		else data[(int) p.getX()][(int) p.getY()]=2;
		return true;
	}
	
	private void initFlag()
	{
		for (int i=0; i < N; i++ )
			for (int j=0; j < N; j++ )
				dataflag[i][j]=true;	
	}
	
	// Check if the user has a 2048 tile
	private boolean checkWin(int c)
	{
		if(c == 2048)
		{
			System.out.println("You Win!");
			win = true;
			terminate = true;
			return true;
		}
		return false;
	}
	
	// Check if all the board full
	private boolean checkLoose()
	{
		if (isEmptyCells() == true)
			return false;
		else
		{
			for (int i=0; i < N-1; i++ )
				for (int j=0; j < N; j++ )
					if ( data[i][j]==data[i+1][j])
						return false;
			for (int i=0; i < N; i++ )
				for (int j=0; j < N-1; j++ )
					if ( data[i][j]==data[i][j+1])
						return false;
		}
		gameOver=true;
		terminate = true;
		return true;
	}
	
	// Create new game
	@Override
	public void restartGame()
	{
		Model2048 m = new Model2048();
		data=m.getData();
		dataflag=m.getDataflag();
		score=0;
		undodata = new LinkedList<int[][]>();
		undoscore = new LinkedList<Integer>();
		addBrick();
		addBrick();
		noMoreMoves = false;
		gameOver=false;
		win = false;
		terminate = false;
		setChanged();
		notifyObservers();
	}

	// Clone the matrix's data
	public int[][] dataClone()
	{
		int[][] c = new int[N][N];
		for (int i=0; i < N; i++ )
			for (int j=0; j < N; j++ )	
				c[i][j] = data[i][j];
		return c;
	}
	
	// Clone the score
	private int scoreClone()
	{
		int c = score;
		return c;
	}
	
	// Save the game to a file
	public boolean saveGame(String str)
	{
		if (str == null){
			return false;
		}
		 try {
	            
	            XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(str)));
	            encoder.writeObject(this);
	            encoder.close();
	            setChanged();
				notifyObservers();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		 return true;
	}
	
	// Load a saved game
	@Override
	public boolean loadGame(String str) 
	{
		if (str == null)
			return false;
		Model2048 m=null;
		XMLDecoder decoder=null;
		try {
			decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(str)));
			m = (Model2048) decoder.readObject();
			decoder.close();
			data=m.getData();
			dataflag=m.getDataflag();
			score=m.getScore();
			undodata=m.getUndodata();
			undoscore=m.getUndoscore();
			noMoreMoves = false;
			setChanged();
			notifyObservers();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	@Override
	public int getScore() {
		return score;
	}
	
	// Check if there are empty cells
	private boolean isEmptyCells()
	{
		int count = 0;
		for (int i=0; i < data.length; i++ )
			for (int j=0; j < data[0].length; j++ )
				if (data[i][j] == 0)
				{
					count++;
				}
		if (count == 0) return false;
		else return true;
	}
	
	public boolean isEqual(Model2048 m)
	{
		int[][] newData = m.getData();
		for (int i=0; i<N; i++)
			for (int j=0; j<N; j++)
				if (data[i][j] != newData[i][j])
					return false;
		return true;
	}
	
	@Override
	public void hint()
	{
		int i=0;
		try {
			i = Minimax.findBestMove(this, 7);
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		switch(i){
		case 0:
			System.out.println("up");
			MoveUp();
			break;
		case 1:
			System.out.println("down");
			MoveDown();
			break;
		case 2:
			System.out.println("left");
			MoveLeft();
			break;
		case 3:
			System.out.println("right");
			MoveRight();
			break;
			
		}
		setChanged();
		notifyObservers();
	}
	
	 public int move(int direction) {    
		 int points = 0;
		 switch (direction)
		 {
	        case 0:
	        	MoveUp();
		        points=getScore();
		        break;
	        case 1:
	        	MoveDown();
	            points=getScore();
	            break;
	        case 2:
	        	MoveLeft();
		        points=getScore();
		        break;
	        case 3:
	        	MoveRight();
	            points=getScore();
	            break;
	        }      
		return points;
	}
	
	public boolean[][] getDataflag() {
		return dataflag;
	}

	public void setDataflag(boolean[][] dataflag) {
		this.dataflag = dataflag;
	}

	public LinkedList<int[][]> getUndodata() {
		return undodata;
	}

	public void setUndodata(LinkedList<int[][]> undodata) {
		this.undodata = undodata;
	}

	public LinkedList<Integer> getUndoscore() {
		return undoscore;
	}

	public void setUndoscore(LinkedList<Integer> undoscore) {
		this.undoscore = undoscore;
	}

	public void setData(int[][] data) {
		this.data = data;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public boolean getGameOver(){
		return gameOver;
	}
	
	public boolean getNoMoreMoves(){
		return noMoreMoves;
	}

	@Override
	public boolean isWin() {
		return win;
	}

	@Override
	public int getLength() {
		return N;
	}

	@Override
	public void MoveUpRight() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void MoveUpLeft() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void MoveDownRight() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void MoveDownLeft() {
		// TODO Auto-generated method stub
		
	}


	public Minimax getminimax() {
		return minimax;
	}


	public boolean isTerminate() {
		return terminate;
	}

}