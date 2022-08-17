import java.awt.*;

import java.awt.event.*;
import javax.swing.*;

public class AnimatedBG extends JLayeredPane{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int width = 180;
	int height = 180;
	
	Timer timer;
	int xVelocity = 1;
	int yVelocity = 1;
	int x = 0;
	int y = 0;
	private ImagePanel img;
	
	AnimatedBG(SokobanGameFrame frame, MainMenu menu, ImagePanel img){
		setLayout(null);
		setSize(frame.getHeight(),frame.getWidth());
		
		
		this.img = img;
		
		//backgroundImage = new ImageIcon("sokobanBG.png").getImage();
		//add(menu);
		add(menu,Integer.valueOf(1));
		
		add(img, Integer.valueOf(0));

		//setBackground(Color.GREEN);
		
		
	}
	
	public ImagePanel getImagePanel() {return img;}
	
	public void sendMouseEvent (int button, boolean bool) {
		if (button==0) img.setMouseOnNewGame(bool);
		else if (button==1) img.setMouseOnLoadGame(bool);
		else if (button==2) img.setMouseOnExit(bool);
		
		img.activateTimer();
	}
	

	
	
}

