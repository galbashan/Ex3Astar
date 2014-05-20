package model;

import java.awt.Point;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;

import maze.Maze_G_Distance;
import maze.Maze_H_Distance;
import maze.Maze;
import maze.MazeDomain;
import model.algorithms.Action;
import model.algorithms.a_star.AStar;


public class ModelMaze extends Observable implements Model,Serializable {
	
	private static final long serialVersionUID = 1L;
	private int[][] maze;
	private LinkedList<int[][]> undodata;
	private LinkedList<Integer> undoscore;
	private int score;
	private Point current;
	private Point goal;
	private AStar as;
	private MazeDomain md;
	private ArrayList<Action> bestPath;
	private LinkedList<Point> path;
	private Maze newMaze;
	private boolean gameOver;
	private boolean noMoreMoves;
	private boolean win;


	public ModelMaze(Maze m){
		maze = m.getMaze();
		current = m.getStart();
		goal = m.getGoal();
		undodata = new LinkedList<int[][]>();
		undoscore = new LinkedList<Integer>();
		md = new MazeDomain(m);
		as = new AStar(md, new Maze_G_Distance(), new Maze_H_Distance());
		bestPath = as.search(md.getStartState(), md.getGoalState());
		path = new LinkedList<Point>();
		score=0;
		newMaze = new Maze(m);
		win = false;
		gameOver = false;
		noMoreMoves = false;
		setChanged();
		notifyObservers();
	}
	
	
	public ModelMaze(){
	}
	
	// Moves
	@Override
	public boolean MoveUp() {
		win = false;
		noMoreMoves = false;
		gameOver = false;
		undodata.addLast(dataClone());
		undoscore.addLast(scoreClone());
		if (get((current.x)-1,current.y) != -1)
		{
			path.addLast(current);
			maze[(current.x)-1][current.y]=1;
			maze[current.x][current.y]=0;
			current = new Point((current.x)-1,current.y);
			score=score+10;
			checkWin(current);
			setChanged();
			notifyObservers();
			return true;
		}
		else{
			maze=undodata.removeLast();
			score=undoscore.removeLast();
			setChanged();
			notifyObservers();
			return false;
		}
	}

	@Override
	public  boolean MoveDown() {
		win = false;
		noMoreMoves = false;
		gameOver = false;
		undodata.addLast(dataClone());
		undoscore.addLast(scoreClone());
		if (get((current.x)+1,current.y) != -1)
		{
			path.addLast(current);
			maze[(current.x)+1][current.y]=1;
			maze[current.x][current.y]=0;
			current = new Point((current.x)+1,current.y);
			score=score+10;
			checkWin(current);
			setChanged();
			notifyObservers();
			return true;
		}
		else{
			maze=undodata.removeLast();
			score=undoscore.removeLast();
			setChanged();
			notifyObservers();
			return false;
		}
		
	}

	@Override
	public  boolean MoveLeft() {
		win = false;
		noMoreMoves = false;
		gameOver = false;
		undodata.addLast(dataClone());
		undoscore.addLast(scoreClone());
		if (get(current.x,(current.y)-1) != -1)
		{
			path.addLast(current);
			maze[current.x][(current.y)-1]=1;
			maze[current.x][current.y]=0;
			current = new Point(current.x,(current.y)-1);
			score=score+10;
			checkWin(current);
			setChanged();
			notifyObservers();
			return true;
		}
		else{
			maze=undodata.removeLast();
			score=undoscore.removeLast();
			setChanged();
			notifyObservers();
			return false;
		}
		
	}

	@Override
	public  boolean MoveRight() {
		win = false;
		noMoreMoves = false;
		gameOver = false;
		undodata.addLast(dataClone());
		undoscore.addLast(scoreClone());
		if (get(current.x,(current.y)+1) != -1)
		{
			path.addLast(current);
			maze[current.x][(current.y)+1]=1;
			maze[current.x][current.y]=0;
			current = new Point(current.x,(current.y)+1);
			score=score+10;
			checkWin(current);
			setChanged();
			notifyObservers();
			return true;
		}
		else{
			maze=undodata.removeLast();
			score=undoscore.removeLast();
			setChanged();
			notifyObservers();
			return false;
		}
		
	}
	

	
	@Override
	public boolean MoveUpRight() {
		win = false;
		noMoreMoves = false;
		gameOver = false;
		undodata.addLast(dataClone());
		undoscore.addLast(scoreClone());
		if (get((current.x)-1,(current.y)+1) != -1)
		{
			path.addLast(current);	
			maze[(current.x)-1][(current.y)+1]=1;
			maze[current.x][current.y]=0;
			current = new Point((current.x)-1,(current.y)+1);
			score=score+15;
			checkWin(current);
			setChanged();
			notifyObservers();
			return true;
		}
		else{
			maze=undodata.removeLast();
			score=undoscore.removeLast();
			setChanged();
			notifyObservers();
			return false;
		}
		
	}
	
	@Override
	public boolean MoveUpLeft() {
		win = false;
		noMoreMoves = false;
		gameOver = false;
		undodata.addLast(dataClone());
		undoscore.addLast(scoreClone());
		if (get((current.x)-1,(current.y)-1) != -1)
		{
			path.addLast(current);	
			maze[(current.x)-1][(current.y)-1]=1;
			maze[current.x][current.y]=0;
			current = new Point((current.x)-1,(current.y)-1);
			score=score+15;
			checkWin(current);
			setChanged();
			notifyObservers();
			return true;
		}
		else{
			maze=undodata.removeLast();
			score=undoscore.removeLast();
			setChanged();
			notifyObservers();
			return false;
		}
		
	}
	
	@Override
	public boolean MoveDownLeft() {
		win = false;
		noMoreMoves = false;
		gameOver = false;
		undodata.addLast(dataClone());
		undoscore.addLast(scoreClone());
		if (get((current.x)+1,(current.y)-1) != -1)
		{
			path.addLast(current);	
			maze[(current.x)+1][(current.y)-1]=1;
			maze[current.x][current.y]=0;
			current = new Point((current.x)+1,(current.y)-1);
			score=score+15;
			setChanged();
			notifyObservers();
			return true;
		}
		else{
			maze=undodata.removeLast();
			score=undoscore.removeLast();
			setChanged();
			notifyObservers();
			return false;
		}
		
	}
	
	@Override
	public boolean MoveDownRight() {
		win = false;
		noMoreMoves = false;
		gameOver = false;
		undodata.addLast(dataClone());
		undoscore.addLast(scoreClone());
		if (get((current.x)+1,(current.y)+1) != -1)
		{
			path.addLast(current);	
			maze[(current.x)+1][(current.y)+1]=1;
			maze[current.x][current.y]=0;
			current = new Point((current.x)+1,(current.y)+1);
			score=score+15;
			checkWin(current);
			setChanged();
			notifyObservers();
			return true;
		}
		else{
			maze=undodata.removeLast();
			score=undoscore.removeLast();
			setChanged();
			notifyObservers();
			return false;
		}
		
	}
	
	private int get(int x, int y) {
		if (y >= maze[0].length || y < 0 || x >= maze.length || x < 0)
			return -1;
		return maze[x][y];
	}


	public int[][] getData() {
		return maze;
	}
		
	
	public void print() {
		for (int i=0; i < maze.length; i++ )
		{
			for (int j=0; j < maze[0].length; j++ )
			{
				System.out.print(maze[i][j]+" ");
			}
			System.out.println();
		}
	}

	public int getScore() {
		return score;
	}
	
	// Perform undo
	@Override
	public void undo() {
		if (undodata.size() != 0){
			current=path.removeLast();
			maze=undodata.removeLast();
			score=undoscore.removeLast();
			gameOver=false;
		}
		else 
			noMoreMoves = true;
		setChanged();
		notifyObservers();
		
	}
	
	// Check if the mouse got the cheese in the best way.
	private boolean checkWin(Point p) {
		if (p.equals(goal))
		{
			if (path.size() > bestPath.size()){
				maze[p.x][p.y]=-4;
				gameOver=true;
				return true;
			}
			else{
				maze[p.x][p.y]=-3;
				win = true;
				return true;
			}
		}
		return false;
	}
	
	// Clone the data.
	private int[][] dataClone()
	{
		int[][] c = new int[maze.length][maze[0].length];
		for (int i=0; i < maze.length; i++ )
			for (int j=0; j < maze[0].length; j++ )	
				c[i][j] = maze[i][j];
		return c;
	}
	
	// Clone the score.
	private int scoreClone()
	{
		int c = score;
		return c;
	}
	
	// Save the game to a file.
	@Override
	public boolean saveGame(String str)
	{
		if (str == null){
			return false;
		}
		 try {
	            
	            XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(str)));
	            encoder.writeObject(this);
	            encoder.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		 return true;
	}
	
	// Load the game from a saved file.
	@Override
	public boolean loadGame(String str) 
	{
		if (str == null){   
			return false;
		}
		ModelMaze m=null;
		XMLDecoder decoder=null;
		try {
			decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(str)));
			m = (ModelMaze) decoder.readObject();
			decoder.close();
			maze=m.getMaze();
			score=m.getScore();
			current=m.getCurrent();
			goal=m.getGoal();
			undodata=m.getUndodata();
			undoscore=m.getUndoscore();
			as=m.getAs();
			md=m.getMd();
			bestPath=m.getBestPath();
			path=m.getPath();
			newMaze=m.getNewMaze();
			win = false;
			noMoreMoves = false;
			gameOver = false;
			setChanged();
			notifyObservers();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	// Restart the game.
	@Override
	public void restartGame()
	{
		ModelMaze m = new ModelMaze(newMaze);
		maze = m.dataClone();
		current = m.getCurrent();
		undodata = new LinkedList<int[][]>();
		undoscore = new LinkedList<Integer>();
		path = new LinkedList<Point>();
		score=0;
		noMoreMoves = false;
		gameOver=false;
		win = false;
		setChanged();
		notifyObservers();
	}

	
	public int[][] getMaze() {
		return maze;
	}


	public LinkedList<int[][]> getUndodata() {
		return undodata;
	}


	public LinkedList<Integer> getUndoscore() {
		return undoscore;
	}


	public Point getCurrent() {
		return current;
	}


	public Point getGoal() {
		return goal;
	}

	public void setMaze(int[][] maze) {
		this.maze = maze;
	}

	public void setUndodata(LinkedList<int[][]> undodata) {
		this.undodata = undodata;
	}

	public void setUndoscore(LinkedList<Integer> undoscore) {
		this.undoscore = undoscore;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void setCurrent(Point current) {
		this.current = current;
	}

	public void setGoal(Point goal) {
		this.goal = goal;
	}


	@Override
	public boolean getGameOver() {
		return gameOver;
	}


	@Override
	public boolean getNoMoreMoves() {
		return noMoreMoves;
	}


	public AStar getAs() {
		return as;
	}


	public void setAs(AStar as) {
		this.as = as;
	}


	public MazeDomain getMd() {
		return md;
	}


	public void setMd(MazeDomain md) {
		this.md = md;
	}


	public ArrayList<Action> getBestPath() {
		return bestPath;
	}


	public void setBestPath(ArrayList<Action> bestPath) {
		this.bestPath = bestPath;
	}


	public LinkedList<Point> getPath() {
		return path;
	}


	public void setPath(LinkedList<Point> path) {
		this.path = path;
	}



	public Maze getNewMaze() {
		return newMaze;
	}


	public void setNewMaze(Maze newMaze) {
		this.newMaze = newMaze;
	}


	@Override
	public boolean isWin() {
		return win;
	}
	
	@Override
	public int getLength(){
		return maze.length;
	}


	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}


	public void setNoMoreMoves(boolean noMoreMoves) {
		this.noMoreMoves = noMoreMoves;
	}


	public void setWin(boolean win) {
		this.win = win;
	}


	@Override
	public int move(int i) {
		return 0;
		
	}


	@Override
	public int connectServer() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int minimax() {
		// TODO Auto-generated method stub
		return 0;
	}
}