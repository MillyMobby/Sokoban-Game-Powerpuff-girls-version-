import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;


public class ImagePanel extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private SokobanGameFrame frame;
	boolean mouseOnNewGame, mouseOnLoadGame,mouseOnExit;
	int width = 175;
	int height = 63;
	Image bubblesButton;
	Image blossomButton;
	Image buttercupButton;
	Image backgroundImage;
	Timer timer;
	int xSpeed = 1;
	int x1;
	int x2; 
	int x3;
	int y1 = 115;
	int y2 = 210;
	int y3 = 295;
	
	ImagePanel(SokobanGameFrame frame){
		x1 = x2 = x3 = 50;
		setLayout(null);
		setSize(frame.getHeight(),frame.getWidth());		 
		//this.frame = frame;
		
		bubblesButton = new ImageIcon("bubblesButton.png").getImage();	
		blossomButton = new ImageIcon("blossomButton.png").getImage();	
		buttercupButton = new ImageIcon("buttercupButton.png").getImage();	
		backgroundImage = new ImageIcon("bg.png").getImage();
			
				
		//setBackground(Color.GREEN);				
	}
	
	public void paint(Graphics g) {		
		//super.paint(g); // paint background		
		Graphics2D g2D = (Graphics2D) g;		
		g2D.drawImage(backgroundImage, 0, 0, null);
		g2D.drawImage(bubblesButton, x1, y1, width, height, null);
		g2D.drawImage(blossomButton, x2, y2, width, height, null);
		g2D.drawImage(buttercupButton, x3, y3, width, height, null);
	}
	
	public void activateTimer() {
		timer = new Timer(3, this);
		timer.start();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (mouseOnNewGame==true ) {
			if(x1>=50 && x1<230) { 
				x1 = x1 + xSpeed;			
					if(x1==230) { timer.stop();}
			}
		}
		if (mouseOnNewGame==false) {
			if(x1>50 ) {
				timer.start();
				x1 = x1 - xSpeed;
			}
		}
		
		if (mouseOnLoadGame==true ) {
			if(x2>=50 && x2<230) { 
				x2 = x2 + xSpeed;			
					if(x2==230) { timer.stop();}
			}
		}
		if (mouseOnLoadGame==false) {
			if(x2>50 ) {
				timer.start();
				x2 = x2 - xSpeed;
			}
		}
		
		if (mouseOnExit==true ) {
			if(x3>=50 && x3<230) { 
				x3 = x3 + xSpeed;			
					if(x3==230) { timer.stop();}
			}
		}
		if (mouseOnExit==false) {
			if(x3>50 ) {
				timer.start();
				x3 = x3 - xSpeed;
			}
		}
		repaint();
	}
	
	public void setMouseOnNewGame (boolean bool) {
		mouseOnNewGame = bool;
		System.out.println("mouse set on button 1");
		//activateTimer();
	}
	
	public boolean getMouseOnNewGame () {
		return mouseOnNewGame ;
	}
	
	public void setMouseOnLoadGame (boolean bool) {
		mouseOnLoadGame = bool;
	}
	
	public void setMouseOnExit (boolean bool) {
		mouseOnExit = bool;
	}
	}

