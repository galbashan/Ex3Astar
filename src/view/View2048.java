package view;

import java.util.Observable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.DragDetectListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class View2048 extends Observable implements View, Runnable {

	Display display;
	Shell shell;
	int userCommand;
	Board board;
	Label scoreLabel;
	Point newPoint;
	Point oldPoint;
	boolean dragFlag;
	MessageBox messageBoxNewGame;
	MessageBox messageBoxSave;
	MessageBox messageBoxGameOver;
	MessageBox messageBoxNoMoreMoves;
	MessageBox messageBoxWin;
	MessageBox messageBoxClose;
	MessageBox messageBoxError;
	String file;
	int flag;
	int length;
	int loop;
	int depth;

	public View2048() {
		userCommand = 200;
	}
	
	/**
	 * Initialize the shell
	 */
	private void initComponents() {
		display = new Display();
		shell = new Shell(display);
		shell.setLayout(new GridLayout(2, false));
		shell.setSize(400, 300);
		shell.setText("my 2048 game");
		
		// Prompt the user before closing the game.
		shell.addShellListener(new ShellListener() {
			
			@Override
			public void shellIconified(ShellEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void shellDeiconified(ShellEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void shellDeactivated(ShellEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void shellClosed(ShellEvent e) {
				messageBoxClose = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
				messageBoxClose.setMessage("Do you really want to close the game?");
				messageBoxClose.setText("Exit");
				int response = messageBoxClose.open();
				if (response == SWT.YES) {
					messageBoxSave = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
					messageBoxSave.setMessage("Do you want to save the game?");
					messageBoxSave.setText("Exit");
					response = messageBoxSave.open();
					if (response == SWT.YES){
						userCommand = 400;
						FileDialog saveFd = new FileDialog(shell, SWT.SAVE);
						saveFd.setText("Save");
						saveFd.setFilterPath("C:/");
						String[] filterExtension = { "*.xml", "*.txt" };
						saveFd.setFilterExtensions(filterExtension);
						file = saveFd.open();
						setChanged();
						notifyObservers();
						System.exit(0);
					}
					else{
						System.exit(0);
					}
				}
				else {
					e.doit = false;
				}
			}
			
			@Override
			public void shellActivated(ShellEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});

		// Score label
		scoreLabel = new Label(shell, SWT.FILL);
		scoreLabel.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));

	    // Create file menu
	    Menu menuBar = new Menu(shell, SWT.BAR);
	    MenuItem fileMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
	    fileMenuHeader.setText("File");

	    Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
	    fileMenuHeader.setMenu(fileMenu);
	    
	    // Create save option in file menu
	    MenuItem fileSaveItem = new MenuItem(fileMenu, SWT.PUSH);
	    fileSaveItem.setText("Save");
	    Image saveImage = new Image(display, "resources/save.jpg");
	    fileSaveItem.setImage(saveImage);
	    fileSaveItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				userCommand = 400;
				FileDialog saveFd = new FileDialog(shell, SWT.SAVE);
				saveFd.setText("Save");
				saveFd.setFilterPath("C:/");
				String[] filterExtension = { "*.xml", "*.txt" };
				saveFd.setFilterExtensions(filterExtension);
				file = saveFd.open();
				setChanged();
				notifyObservers();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	    
	    // Create load option in file menu
	    MenuItem fileLoadItem = new MenuItem(fileMenu, SWT.PUSH);
	    fileLoadItem.setText("Load");
	    Image loadImage = new Image(display, "resources/load.jpg");
	    fileLoadItem.setImage(loadImage);
	    fileLoadItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				userCommand = 300;
				FileDialog loadFd = new FileDialog(shell, SWT.OPEN);
				loadFd.setText("Open");
				loadFd.setFilterPath("C:/");
				String[] filterExtension = { "*.xml", "*.txt" };
				loadFd.setFilterExtensions(filterExtension);
				file = loadFd.open();
				setChanged();
				notifyObservers();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	    
	    // Create exit option in file menu
	    MenuItem fileexitItem = new MenuItem(fileMenu, SWT.PUSH);
	    fileexitItem.setText("Exit");
	    Image exitImage = new Image(display, "resources/exit.jpg");
	    fileexitItem.setImage(exitImage);
	    fileexitItem.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				messageBoxClose = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
				messageBoxClose.setMessage("Do you really want to close the game?");
				messageBoxClose.setText("Exit");
				int response = messageBoxClose.open();
				if (response == SWT.YES) {
					messageBoxSave = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
					messageBoxSave.setMessage("Do you want to save the game?");
					messageBoxSave.setText("Exit");
					response = messageBoxSave.open();
					if (response == SWT.YES){
						userCommand = 400;
						FileDialog saveFd = new FileDialog(shell, SWT.SAVE);
						saveFd.setText("Save");
						saveFd.setFilterPath("C:/");
						String[] filterExtension = { "*.xml", "*.txt" };
						saveFd.setFilterExtensions(filterExtension);
						file = saveFd.open();
						setChanged();
						notifyObservers();
						System.exit(0);
					}
					else{
						System.exit(0);
					}
				}
				else {
					e.doit = false;
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
	    
	    // Create edit menu
	    MenuItem editMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
	    editMenuHeader.setText("Edit");

	    Menu editMenu = new Menu(shell, SWT.DROP_DOWN);
	    editMenuHeader.setMenu(editMenu);
	    
	    // Create undo option in edit menu
	    MenuItem editUndoItem = new MenuItem(editMenu, SWT.PUSH);
	    editUndoItem.setText("Undo");
	    Image undoImage = new Image(display, "resources/undo.jpg");
	    editUndoItem.setImage(undoImage);
	    editUndoItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				userCommand = 100;
				setChanged();
				notifyObservers();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	    
	    // Create hint option in edit menu
	    MenuItem editHintItem = new MenuItem(editMenu, SWT.PUSH);
	    editHintItem.setText("Hint");
	    Image hintImage = new Image(display, "resources/hint.gif");
	    editHintItem.setImage(hintImage);
	    editHintItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				userCommand = 500;
				NumberInput numdialog = new NumberInput(shell);
				loop = numdialog.open();
				DepthInput depthdialog = new DepthInput(shell);
				depth = depthdialog.open();
				setChanged();
				notifyObservers(); 
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	    
	    // Create restart option in edit menu
	    MenuItem editRestartItem = new MenuItem(editMenu, SWT.PUSH);
	    editRestartItem.setText("New game");
	    Image restartImage = new Image(display, "resources/restart.jpg");
	    editRestartItem.setImage(restartImage);
	    editRestartItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				messageBoxNewGame = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
				messageBoxNewGame.setMessage("Do you really want to restart the game?");
				messageBoxNewGame.setText("Restart game");
				int response = messageBoxNewGame.open();
				if (response == SWT.YES) {
					userCommand = 200;
					setChanged();
					notifyObservers();
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		 // Game board
 		board = new Board(shell, SWT.BORDER, length);
 		board.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 8));
 		board.setBackground(new Color(display, 187, 174, 160));
 		board.addKeyListener(new KeyAdapter() {
 			public void keyPressed(KeyEvent e) {
 				if (e.keyCode == SWT.ARROW_UP) {
 					userCommand = 8;
 					setChanged();
 					notifyObservers();
 				}
 				if (e.keyCode == SWT.ARROW_DOWN) {
 					userCommand = 2;
 					setChanged();
 					notifyObservers();
 				}
 				if (e.keyCode == SWT.ARROW_LEFT) {
 					userCommand = 4;
 					setChanged();
 					notifyObservers();
 				}
 				if (e.keyCode == SWT.ARROW_RIGHT) {
 					userCommand = 6;
 					setChanged();
 					notifyObservers();
 				}
 			}
 		});

 		board.addMouseListener(new MouseListener() {
 			
 			@Override
 			public void mouseUp(MouseEvent e) {
 				newPoint = new Point(e.x,e.y);
 				checkMouseDirection(oldPoint, newPoint);
 			}
 			
 			@Override
 			public void mouseDown(MouseEvent e) {
 				oldPoint = new Point(e.x,e.y);
 			}
 			
 			@Override
 			public void mouseDoubleClick(MouseEvent arg0) {
 				// TODO Auto-generated method stub
 				
 			}
 		});
 		
 		board.addDragDetectListener(new DragDetectListener() {
 			
 			@Override
 			public void dragDetected(DragDetectEvent e) {
 				dragFlag = true;
 			}
 		});
		
		// Undo button
		//Image undoImage = new Image(display, "resources/undo.jpg");
		Button undo = new Button(shell, SWT.PUSH);
		undo.setText("Undo");
		undo.setImage(undoImage);
		undo.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		undo.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				userCommand = 100;
				setChanged();
				notifyObservers();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		// Hint button
		//Image hintImage = new Image(display, "resources/hint.gif");
		Button hintMinimax = new Button(shell, SWT.PUSH);
		hintMinimax.setText("Hint");
		hintMinimax.setImage(hintImage);
		hintMinimax.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		hintMinimax.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				userCommand = 500;
				NumberInput numdialog = new NumberInput(shell);
				loop = numdialog.open();
				DepthInput depthdialog = new DepthInput(shell);
				depth = depthdialog.open();
				setChanged();
				notifyObservers(); 
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		// New game button
		//Image restartImage = new Image(display, "resources/restart.jpg");
		Button restart = new Button(shell, SWT.PUSH);
		restart.setText("New game");
		restart.setImage(restartImage);
		restart.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		restart.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				messageBoxNewGame = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
				messageBoxNewGame.setMessage("Do you really want to start a new game?");
				messageBoxNewGame.setText("New game");
				int response = messageBoxNewGame.open();
				if (response == SWT.YES) {
					userCommand = 200;
					setChanged();
					notifyObservers();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		// Save button
		//Image saveImage = new Image(display, "resources/save.jpg");
		Button save = new Button(shell, SWT.PUSH);
		save.setText("Save Game");
		save.setImage(saveImage);
		save.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		save.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				userCommand = 400;
				FileDialog saveFd = new FileDialog(shell, SWT.SAVE);
				saveFd.setText("Save");
				saveFd.setFilterPath("C:/");
				String[] filterExtension = { "*.xml", "*.txt" };
				saveFd.setFilterExtensions(filterExtension);
				file = saveFd.open();
				setChanged();
				notifyObservers();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		// Load button
		//Image loadImage = new Image(display, "resources/load.jpg");
		Button load = new Button(shell, SWT.PUSH);
		load.setText("Load Game");
		load.setImage(loadImage);
		load.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		load.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				userCommand = 300;
				FileDialog loadFd = new FileDialog(shell, SWT.OPEN);
				loadFd.setText("Open");
				loadFd.setFilterPath("C:/");
				String[] filterExtension = { "*.xml", "*.txt" };
				loadFd.setFilterExtensions(filterExtension);
				file = loadFd.open();
				setChanged();
				notifyObservers();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		// Exit button
		//Image exitImage = new Image(display, "resources/exit.jpg");
		Button exit = new Button(shell, SWT.PUSH);
		exit.setText("Exit");
		exit.setImage(exitImage);
		exit.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		exit.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				messageBoxClose = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
				messageBoxClose.setMessage("Do you really want to close the game?");
				messageBoxClose.setText("Exit");
				int response = messageBoxClose.open();
				if (response == SWT.YES) {
					messageBoxSave = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
					messageBoxSave.setMessage("Do you want to save the game?");
					messageBoxSave.setText("Exit");
					response = messageBoxSave.open();
					if (response == SWT.YES){
						userCommand = 400;
						FileDialog saveFd = new FileDialog(shell, SWT.SAVE);
						saveFd.setText("Save");
						saveFd.setFilterPath("C:/");
						String[] filterExtension = { "*.xml", "*.txt" };
						saveFd.setFilterExtensions(filterExtension);
						file = saveFd.open();
						setChanged();
						notifyObservers();
						System.exit(0);
					}
					else{
						System.exit(0);
					}
				}
				else {
					e.doit = false;
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		shell.setMenuBar(menuBar);
		shell.open();
	}

	@Override
	public void run() {
		initComponents();
		setChanged();
		notifyObservers();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {

				display.sleep();
			}
		}
		display.dispose();
	}
	
	/**
	 * Perform changes in board according user's drag mouse
	 * @param oldPoint
	 * @param newPoint
	 */
	private void checkMouseDirection(Point oldPoint, Point newPoint)
	{
		boolean up = false;
		boolean down = false;
		boolean right = false;
		boolean left = false;
		
		if (dragFlag == true)
		{
			int temp_x = newPoint.x - oldPoint.x;
			int temp_y = newPoint.y - oldPoint.y;
			
			if (temp_x < 0)
				temp_x = temp_x*(-1);
			
			if (temp_y < 0)
				temp_y = temp_y*(-1);
			
			if (temp_x > 20)
			{	
				if (newPoint.x < oldPoint.x) {
					left = true;
				}
				if (newPoint.x > oldPoint.x) {
					right = true;
				}
			}
			
			if (temp_y > 20)
			{
				if (newPoint.y < oldPoint.y) {
					up = true;
				}
				if (newPoint.y > oldPoint.y) {
					down = true;
				}
			}
			
			if (up)
				userCommand = 8;
			else if (down)
				userCommand = 2;
			else if (left)
				userCommand = 4;
			else if (right)
				userCommand = 6;
		}
		
		dragFlag = false;
		
		if (up || down || left || right)
		{
			setChanged();
			notifyObservers();
		}
	}
	
	/**
	 * Update the board in the GUI
	 */
	@Override
	public void displayData(int[][] data) {
		board.setBoardData(data);
		board.forceFocus();
	}

	/**
	 * User command for updating the presenter
	 */
	@Override
	public int getUserCommand() {
		return userCommand;
	}

	/**
	 * Get the file path that the user save or load
	 */
	public String getString() {
		return file;
	}

	/**
	 * Update the score in the GUI
	 */
	@Override
	public void displayScore(int score) {
		scoreLabel.setText("Score:    " + score);
	}

	/**
	 * Display game over message box 
	 */
	@Override
	public void displayGameOver(boolean gameOver) {
		if (gameOver == true) {
			messageBoxGameOver = new MessageBox(shell, SWT.OK);
			messageBoxGameOver.setMessage("You Lose :(" + "\n" + "No more moves available.");
			messageBoxGameOver.setText("Game over");
			messageBoxGameOver.open();
		}
	}
	
	/**
	 * Display no more moves message box
	 */
	@Override
	public void displayNoMoreMoves(boolean noMoreMoves) {
		if (noMoreMoves == true) {
			messageBoxNoMoreMoves = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
			messageBoxNoMoreMoves.setMessage("No more moves to revert!");
			messageBoxNoMoreMoves.setText("Undo");
			messageBoxNoMoreMoves.open();
		}
	}
	
	/**
	 * Display win message box
	 */
	@Override
	public void displayWin(boolean win) {
		if (win == true) {
			messageBoxWin = new MessageBox(shell, SWT.OK);
			messageBoxWin.setMessage("You reached 2048!" + "\n" + "You can continue playing or start a new game.");
			messageBoxWin.setText("You win!!!");
			messageBoxWin.open();
		}

	}
	
	/**
	 * Display error message when connection to server failed
	 */
	@Override
	public void displayServerError (String error){
		messageBoxError = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
		messageBoxError.setMessage(error);
		messageBoxError.setText("Connection failed");
		messageBoxError.open();
	}
	
	@Override
	public void setLength(int length) {
		this.length = length;
	}

	@Override
	public int getLoop() {
		return loop;
	}
	
	public int getDepth() {
		return depth;
	}
}