import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Box;
import javax.swing.BoxLayout;

import javax.swing.JButton;


public class MainMenu extends JLayeredPane implements MouseListener{
	
	private ArrayList<JButton> Buttons = new ArrayList<JButton>(3);
	private SokobanGameFrame frame;	
	private CharacterChoice characters = new CharacterChoice() ;	
	AnimatedBG BGpanel;
	GridBagLayout gridBagLayout = new GridBagLayout();
	
	public MainMenu(SokobanGameFrame frame) {
		setLayout(null);
				
		this.frame = frame;
		this.BGpanel = frame.getPanel();
		setSize(frame.getHeight(),frame.getWidth());
		
		//add(panel);
		add(characters);
		createButtons();		
	}
	
	
	/**
	 * creates the buttons and adds it to panel
	 */
	private void createButtons() {
		System.out.println();
		int start = frame.getHeight()/2 - 155, distance = 90, width = 260, height = 70;
		
		JButton play;
		play = new JButton("NEW GAME");
		play.setBounds(10,start,width, height);
		
		
		play.setBackground(Color.CYAN);
		play.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				frame.stopAnimation();
				frame.setCharacter(characters.getChoice());
				frame.getCardLayout().show(frame.getContentPane(), "Sokoban Game");
				frame.getBoard().requestFocusInWindow();
				frame.setCurrentLevel(1);
				frame.setInitialState();
				frame.setMenuVisible(frame.isVisible());
				frame.saveGraphicsFile(frame.saved);
			}
			
		});
		
		play.addMouseListener(this);
		
		JButton load;
		load = new JButton("LOAD GAME");
		load.setBounds(10,start + distance,width, height);
		
		
		load.setBackground(Color.PINK);
		load.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				frame.getCardLayout().show(frame.getContentPane(), "Sokoban Game");
				frame.getBoard().requestFocusInWindow();
				frame.loadGame();
				frame.setMenuVisible(frame.isVisible());				
			}			
		});
		
		load.addMouseListener(this);
		
		JButton character;
		character = new JButton("CHARACTER");
		character.setBounds(10,start + distance*2,width, height);
		character.setVisible(true);
		
		character.setBackground(Color.GREEN);
		character.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				 characters.getPopupMenu().show(character, width,0 );
				 frame.sendMouseEvent(2, false);
				 System.out.println(" personaggio " + characters.getChoice());
				 
				 //frame.setInitialState();
			}			
		});
		
		character.addMouseListener(this);
		
		add(play); 
		add(load); 
		add(character); 
		
		Buttons.add(play);
		Buttons.add(load);
		Buttons.add(character);
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		//characters.checkPopup(e);
	}
	

	@Override
	public void mousePressed(MouseEvent e) {}


	@Override
	public void mouseReleased(MouseEvent e) {}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub		
		Component c = e.getComponent();
		if (c==Buttons.get(0)) {
			frame.sendMouseEvent(0, true);
		}
		else if (c==Buttons.get(1)) {
			frame.sendMouseEvent(1, true);
		}
		else if (c==Buttons.get(2)) {
			frame.sendMouseEvent(2, true);
		}		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		Component c = e.getComponent();
		if (c==Buttons.get(0)) {frame.sendMouseEvent(0, false);}
		else if (c==Buttons.get(1)) {frame.sendMouseEvent(1, false); }
		else if (c==Buttons.get(2)) {frame.sendMouseEvent(2, false);}		
	}
	
	public CharacterChoice getCharacterChoice() {
		return characters;
	}
}

