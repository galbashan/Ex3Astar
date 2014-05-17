package view;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.DragDetectListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class ViewMaze extends Observable implements View, Runnable {

	Display display;
	Shell shell;
	String file;
	Board board;
	int userCommand;
	Label scoreLabel;
	boolean keyFlag;
	int lastKeyCode;
	Point newPoint;
	Point oldPoint;
	boolean dragFlag;
	MessageBox messageBoxRestart;
	MessageBox messageBoxGameOver;
	MessageBox messageBoxNoMoreMoves;
	MessageBox messageBoxWin;
	MessageBox messageBoxClose;
	int length;
	
	public ViewMaze(){
		userCommand = 200;
	}
	
	private void initComponents() {
		display = new Display();
	    shell = new Shell(display);
	    shell.setLayout(new GridLayout(2, false));
	    shell.setText("My Maze");
	    shell.setSize(1000, 1000);
	    
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
					System.exit(0);
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
	    
	    Label label = new Label(shell, SWT.CENTER);
	    label.setBounds(shell.getClientArea());
	    
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
	    
	    // Create restart option in edit menu
	    MenuItem editRestartItem = new MenuItem(editMenu, SWT.PUSH);
	    editRestartItem.setText("Restart");
	    Image restartImage = new Image(display, "resources/restart.jpg");
	    editRestartItem.setImage(restartImage);
	    editRestartItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				messageBoxRestart = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
				messageBoxRestart.setMessage("Do you really want to restart the game?");
				messageBoxRestart.setText("Restart game");
				int response = messageBoxRestart.open();
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
	    
	    // Create the board
	    board = new Board(shell, SWT.BORDER, length);
	    board.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true, 1,8));
	    board.setBackground(new Color(display, 187, 174, 160));
	    board.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(final KeyEvent e) {
				if (keyFlag == false) {
                    keyFlag = true;
                    lastKeyCode = e.keyCode;
                    
                    Timer t = new Timer();

                    t.schedule(new TimerTask() {

                        @Override
                        public void run() {
                            int x=0 ,y=0;
                            if (keyFlag == false && lastKeyCode != e.keyCode) 
                                    {
                                         x = e.keyCode;
                                         y = lastKeyCode;
                                    }
                            
                            
                            else
                            {
                                keyFlag = false;
                                userCommand = e.keyCode;
                            }

                            
                            if (x!=0 || y!=0)
                            {
                                if ((x == SWT.ARROW_DOWN && y == SWT.ARROW_RIGHT) || y == SWT.ARROW_DOWN && x == SWT.ARROW_RIGHT )
                                	userCommand = 3;
                                if ((x == SWT.ARROW_DOWN && y == SWT.ARROW_LEFT) || y == SWT.ARROW_DOWN && x == SWT.ARROW_LEFT )
                                	userCommand = 1;
                                if ((x == SWT.ARROW_UP && y == SWT.ARROW_RIGHT) || y == SWT.ARROW_UP && x == SWT.ARROW_RIGHT )
                                	userCommand = 9;
                                if ((x == SWT.ARROW_UP && y == SWT.ARROW_LEFT) || y == SWT.ARROW_UP && x == SWT.ARROW_LEFT )
                                	userCommand = 7;
                            }
                                
                            else
                            {
                                if (e.keyCode == SWT.ARROW_DOWN)
                                	userCommand = 2;

                                if (e.keyCode == SWT.ARROW_UP)
                                	userCommand = 8;

                                if (e.keyCode == SWT.ARROW_RIGHT)
                                	userCommand = 6;

                                if (e.keyCode == SWT.ARROW_LEFT)
                                	userCommand = 4;

                                if (e.keyCode == SWT.END)
                                	userCommand = 77;
                            }
                            display.asyncExec(new Runnable() {

                                @Override
                                public void run() {
                                    setChanged();
                                    notifyObservers();
                                }
                            });

                        }
                    }, 100); // End tm.schedule
                } 
				else {
                    keyFlag = false;
                    lastKeyCode = e.keyCode;
                }
            }
		});
	    
	    board.addMouseListener(new MouseListener() {
			
			@Override
			// Save the location when the user release the click
			public void mouseUp(MouseEvent e) {
				newPoint = new Point(e.x,e.y);
				checkMouseDirection(oldPoint, newPoint);
			}
			
			@Override
			// Save the location of user's click
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
			
			if (up && right)
				userCommand = 9;
			else if (up && left)
				userCommand = 7;
			else if (down && right)
				userCommand = 3;
			else if (down && left)
				userCommand = 1;
			else if (up)
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
	
	// Update the board in the GUI
	@Override
	public void displayData(int[][] data) {
		board.setBoardData(data);
		board.forceFocus();
	}
	
	// User command for updating the presenter
	@Override
	public int getUserCommand() {
		return userCommand;
	}
	
	// Get the file path that the user save or load
	@Override
	public String getString() {
		return file;
	}

	// Update the score in the GUI
	@Override
	public void displayScore(int score) {
		scoreLabel.setText("Score:    " + score);
	}

	// Display game over message box
	@Override
	public void displayGameOver(boolean gameOver) {
		if (gameOver == true) {
			messageBoxWin = new MessageBox(shell, SWT.OK);
			messageBoxWin.setMessage("This is not the shortest path!");
			messageBoxWin.setText("You loose");
			messageBoxWin.open();
		}
	}

	// Display no more moves message box
	@Override
	public void displayNoMoreMoves(boolean noMoreMoves) {
		if (noMoreMoves == true) {
			messageBoxNoMoreMoves = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
			messageBoxNoMoreMoves.setMessage("No more moves to revert!");
			messageBoxNoMoreMoves.setText("Undo");
			messageBoxNoMoreMoves.open();
		}
	}

	// Display win message box
	@Override
	public void displayWin(boolean win) {
		if (win == true) {
			messageBoxWin = new MessageBox(shell, SWT.OK);
			messageBoxWin.setMessage("You chose the shortest path to the cheese!");
			messageBoxWin.setText("You win!!!");
			messageBoxWin.open();
		}
	}

	@Override
	public void setLength(int length) {
		this.length = length;
	}
}//
