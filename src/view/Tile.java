package view;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class Tile extends Canvas{
	
	private int value;
	private FontMetrics fm;
	private Image imWall = new Image(Display.getDefault(),"resources/wall.jpg");
	private Image imCheese = new Image(Display.getDefault(),"resources/cheese.jpg");
	private Image imMouse = new Image(Display.getDefault(),"resources/mouse.jpg");
	private Image imWin = new Image(Display.getDefault(),"resources/win.jpg");
	private Image imLoose = new Image(Display.getDefault(),"resources/loose.jpg");
	
	public Tile(Composite parent, int style) {
		super(parent, style); // Board c'tor ?
		value = 0;
		
		Font font = getFont();
		Font newFont = new Font(getDisplay(), font.getFontData()[0].getName(), 16, SWT.BOLD);
		setFont(newFont);
		
		// Locate the numbers or images in the tile and set the color.
		addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				fm = e.gc.getFontMetrics();
				int width = fm.getAverageCharWidth();
				int mx = getSize().x/2 - ("" + value).length() * width/2;
				int my  = getSize().y/2  - fm.getHeight()/2 - fm.getDescent();
				if (value > 0){
					e.gc.drawString("" + value, mx, my);
				}
				switch (value) {
				case -1:
				{
					e.gc.drawImage(new Image(Display.getDefault(), imWall.getImageData().scaledTo(e.width, e.height)), 0, 0);
					break;
				}
				case -2:
				{
					e.gc.drawImage(new Image(Display.getDefault(), imCheese.getImageData().scaledTo(e.width, e.height)), 0, 0);
					break;
				}
				case -3:
				{
					e.gc.drawImage(new Image(Display.getDefault(), imWin.getImageData().scaledTo(e.width, e.height)), 0, 0);
					break;
				}
				case -4:
				{
					e.gc.drawImage(new Image(Display.getDefault(), imLoose.getImageData().scaledTo(e.width, e.height)), 0, 0);
					break;
				}
				case 0:  
				{
					setBackground(new Color(getDisplay(), 204,192,178));
					break;
				}
				case 1:  
				{
					e.gc.drawImage(new Image(Display.getDefault(), imMouse.getImageData().scaledTo(e.width, e.height)), 0, 0);
					break;
				}
				case 2:
				{
					setBackground(new Color(getDisplay(), 237,229,219));
					setForeground(new Color(getDisplay(), 119,110,101));
					break;
				}
				case 4:  {
					setBackground(new Color(getDisplay(), 242,227,177));
					setForeground(new Color(getDisplay(), 119,110,101));
					break;
				}
				case 8:  {
					setBackground(new Color(getDisplay(), 246,176,125));
					setForeground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
					break;
				}
				case 16:  {
					setBackground(new Color(getDisplay(), 247,154,101));
					setForeground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
					break;
				}
				case 32:  {
					setBackground(new Color(getDisplay(), 246,124,91));
					setForeground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
					break;
				}
				case 64:  {
					setBackground(new Color(getDisplay(), 244,95,55));
					setForeground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
					break;
				}
				case 128:  {
					setBackground(new Color(getDisplay(), 232,208,121));
					setForeground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
					break;
				}
				case 256:  {
					setBackground(new Color(getDisplay(), 233,202,92));
					setForeground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
					break;
				}
				case 512:  {
					setBackground(new Color(getDisplay(), 235,197,63));
					setForeground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
					break;
				}
				case 1024:  {
					setBackground(new Color(getDisplay(), 232,187,31));
					setForeground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
					break;
				}
				case 2048:  {
					setBackground(new Color(getDisplay(), 224,177,13));
					setForeground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
					break;
				}
				case 4096:  {
					setBackground(new Color(getDisplay(), 22,217,176));
					setForeground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
					break;
				}
				case 8196:  {
					setBackground(new Color(getDisplay(), 22,184,150));
					setForeground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
					break;
				}
				default: {
					setBackground(getDisplay().getSystemColor(SWT.COLOR_BLACK));
					setForeground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
					break;
				}
			}
			}
		});
		
	}
	
	public void setValue(int value){
		this.value = value;
		redraw();
	}
//
}
