	import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JPanel;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
import javax.swing.JMenuItem;
import java.util.Collections;
import java.io.*;
	public class SokobanGameFrame extends KeyAnimationBoardFrame {
	private static final long serialVersionUID = 1L;
		private JPanel mainPanel = new JPanel();
		private CardLayout myLayout = new CardLayout();
		private AnimatedBG bg;  
		private BoardPanel board;
		private GameState currentState; 
		private Boolean gameStarted;
		private Levels levels;
		private ArrayList<Coordinate> cratesChronology,pusherChronology;
		private int currentLevel, moves, crateMoves, keepMoves, keepCrateMoves, pFromHere, cFromHere;
		private ImagePanel img;	
		private MainMenu menu = new MainMenu(this); // pannello iniziale con bottoni
		protected JMenuItem menuHome;
		public File saved = new File("saved.txt");
		File savedEmpty = new File("savedEmpty.txt");
		boolean beingLoaded = false;
		int points, character;
		BufferedImage characterPic ;
		/**
		 * Constructs a frame for the Sokoban game
		 * 
		 * @param title 
		 * 			the title of the board frame
		 * @param rows  
		 * 			the number of rows of the board
		 * @param cols  
		 * 			the number of columns of the board
		 * @param size  
		 * 			the size of each block on the board (in pixels)
		 */
		public SokobanGameFrame(int rows, int cols, int pixels) {
			super("Sokoban Game (Powerpuff Girls' Version)", rows, cols, pixels);
			setLayout(new CardLayout());
			setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					if (gameStarted == true) {saveGraphicsFile(saved);}
					close();
					System.exit(0);
				}
			});
			setCharacter(1);
			setResizable(false);
			setMenuVisible(true);
			setBoard();
			gameStarted = false;
			levels = new Levels();
			pusherChronology = new ArrayList<>();
			cratesChronology = new ArrayList<>();
			currentLevel = 1;
			moves = crateMoves = keepMoves = keepCrateMoves = pFromHere = cFromHere = 0;
			img = new ImagePanel(this);
			bg = new AnimatedBG(this,menu,img);
			setCardLayout();
			addmenuHome();
		}
		
		public ImagePanel getImagePanel() {return img;}
		
		public void sendMouseEvent (int button, boolean bool) {
			if (button==0) img.setMouseOnNewGame(bool);
			else if (button==1) img.setMouseOnLoadGame(bool);
			else if (button==2) img.setMouseOnExit(bool);
			img.activateTimer();
		}
		
		private void addmenuHome() {
			menuHome = new JMenuItem("Home ");
			menuBar.add(menuHome,0);
			menuHome.setMaximumSize(new Dimension(100, 30));
			
			menuHome.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setMenuVisible(false);
					setTitle("Sokoban Game");
					System.out.println("saving");
					pusherChronology.clear(); cratesChronology.clear();
					saveGraphicsFile(saved);
					myLayout.show(mainPanel, "initial");
					repaint();
					gameStarted = false;	
				}
			});
		}
		
		private void setCardLayout() {
			mainPanel.setLayout(myLayout);
			mainPanel.paintComponents(getGraphics());				     
			mainPanel.add(bg, "initial");			
			mainPanel.add(board, "Sokoban Game");		
		}
		
		/**
		 * returns the card layout of the frame
		 */
		public CardLayout getCardLayout() {
			return myLayout;
		}
		
		/**
		 * Sets a board panel of the frame
		 */
		private void setBoard() {
			board = getBoardPanel();
			board.setBackground(Color.PINK);
			loadGraphics(readImage(new File("sokobanBG.png")));
		}
		
		/**
		 * returns the boardPanel of the frame
		 */
		public BoardPanel getBoard() { 
			return board;
		}
		
		/**
		 * returns the panel with the buttons 
		 */
		public MainMenu getInitialPanel() {
			return menu;
		}
				
		/**
		 * Sets the initial state of the game on the frame
		 */
		public void setInitialState() {
			beingLoaded = false;
			currentState = new GameState(board.getRows(), board.getColumns());
			if (beingLoaded == false ) {
				if (currentLevel == 1) {currentState.initialize(levels.getLevel1());}		
				else if (currentLevel == 2) {currentState.initialize(levels.getLevel2());}
			}
			currentState.buildWorld();
			showCurrentState();
			gameStarted = true;
		}
				
		/**
		 * initialize the panel showing the initial panel
		 */
		public void initializePanel() {
			setMenuVisible(false); 
			setContentPane(mainPanel);			
			myLayout.show(mainPanel, "initial");
		}
		
		/**
		 * Shows the current state of the game on the frame
		 */
		private void showCurrentState() {
			for (int i = 0; i < board.getRows(); i++) {
				for (int j = 0; j < board.getColumns(); j++) {
					FieldObject object = currentState.getObject(i, j);
					if (object != null) {
						drawObject(object);
					}				
				}
			}
		}
		
		public void setCharacter(int c) {
			character = c;
			if (character==1) {characterPic = readImage(new File("BUBBLES.png"));}
			if (character==2) {characterPic = readImage(new File("BLOSSOM.png"));}
			if (character==3) {characterPic = readImage(new File("BUTTERCUP.png"));}
		}
		
		public Image getCharacter() {
			return characterPic;
		}
		/**
		 * Draws a shape or loads an image according to the properties of the given object
		 * @param object
		 * 		an object of the GameState's matrix
		 */
		public void drawObject(FieldObject object) {
			if (object instanceof Wall) {
				BufferedImage image = readImage(new File("Wall.png"));
				board.drawImage(object.getX(), object.getY(), image);
			} 
			else if (object instanceof Pusher && object.getClass() != Crate.class) {
				Image im = getCharacter();
				board.drawImage(object.getX(), object.getY(), im);
				repaint();
			} 
			else if (object instanceof Crate) {
				board.drawRectangle(object.getX(), object.getY(), object.getColor(), object.getDensity());
			} 
			else if ((object instanceof EmptyPlace) && (((EmptyPlace) object).getIsStorage()== true)) {
				BufferedImage image = readImage(new File("STORAGE.png"));
				board.drawImage(object.getX(), object.getY(), image);
			}
			else if ((object.getClass() == EmptyPlace.class) && (((EmptyPlace) object).getIsStorage()== false)) {
				board.drawRectangle(object.getX(), object.getY(), object.getColor(), object.getDensity());
			}			
		}

		/**
		 * Processes the key pressed:
		 * 
		 * 		- Keyboard arrows move the pusher
		 * 		
		 * 
		 * @param e 
		 * 		the KeyEvent of the key pressed
		 */
		@Override
		protected void processKey(KeyEvent e) {		
			int keyEventCode = e.getKeyCode();
			
				if (keyEventCode >= 37 && keyEventCode <= 40) {
					if (gameStarted == true) {setPusherDirection(keyEventCode);}
					
					if (gameStarted == false && isAnimationEnabled()==true) {
							gameStarted = true;	//System.out.println("ho interrotto l'animazione ");							
							exitingAnimation();
							setPusherDirection(keyEventCode);
					}			
				} 		
		}
		
		public void exitingAnimation() {
			stopAnimation();	
			System.out.println("elimino Pcronologia da 0 a" + pFromHere +  " mosse =  "+ moves + "list size "+ pusherChronology.size());
			System.out.println("elimino Ccronologia da 0 a" + cFromHere +  " mosseCasse "+ crateMoves + "crarelist size " + cratesChronology.size());
			pusherChronology.subList(0, pFromHere).clear();
			cratesChronology.subList(0,cFromHere).clear();
			Collections.reverse(pusherChronology);
			Collections.reverse(cratesChronology);
			moves = -pusherChronology.size();
			crateMoves = -cratesChronology.size();			
		}

		/**
		 * Changes the direction of the pusher according to the key pressed
		 * 
		 * @param keyCode 
		 * 			the keyCode of the key pressed
		 */
		private void setPusherDirection(int keyEventCode) {
			
				switch (keyEventCode) {
				case 37:	// VK_LEFT				
					currentState.getPusher().TakeDirection(1);
					if (currentState.getPusher().getHeadedTo() == currentState.getPusher().getDirections(0)) {
						movePusher();					
						System.out.println("PUSHER è IN "+ currentState.getPusher().getX()+ " ," + currentState.getPusher().getY());									
					}
					break;
					
				case 38:	// VK_UP				
					currentState.getPusher().TakeDirection(2);
					if (currentState.getPusher().getHeadedTo() == currentState.getPusher().getDirections(1)) {
						movePusher();					
						System.out.println("PUSHER è IN "+ currentState.getPusher().getX()+ " ," + currentState.getPusher().getY());						
					}
					break;
					
				case 39:	// VK_RIGHT				
					currentState.getPusher().TakeDirection(3);
					if (currentState.getPusher().getHeadedTo() == currentState.getPusher().getDirections(2)) {
						movePusher();					
						System.out.println("PUSHER è IN "+ currentState.getPusher().getX()+ " ," + currentState.getPusher().getY());						
					}
					break;
					
				case 40:	// VK_DOWN				
					currentState.getPusher().TakeDirection(4);
					if (currentState.getPusher().getHeadedTo() == currentState.getPusher().getDirections(3)) {
						movePusher();					
						System.out.println("PUSHER è IN "+ currentState.getPusher().getX()+ " ," + currentState.getPusher().getY());
					}
					break;
				}
				
				if (gameStarted == true) {
					System.out.println("aggiungo a pusher chronology ");
					pusherChronology.add(new Coordinate(currentState.getPusher().getX(), currentState.getPusher().getY()));
				}
		}
		
		/**
		 * Performs the next step of the pusher
		 * 
		 */
		private void walk() {		
			Pusher p = currentState.getPusher();				
			board.clear(p.getX(), p.getY());		
			currentState.setNextPusherState(p.getNextX(), p.getNextY());		
		}

		protected void animateInit() {
			if (beingLoaded == true) showMessageDialog("cannot replay on saved ", "ciao");
			else {
				keepMoves = moves = -pusherChronology.size();
				keepCrateMoves = crateMoves = -cratesChronology.size();
				Collections.reverse(pusherChronology);
				Collections.reverse(cratesChronology);
				setAnimationDelay(1000);
				currentState.reset();
				board.clear();							
				loadGraphics(readImage(new File("sokobanBG.png")));
				loadGame();
				repaint();
				gameStarted = false;
			}
		}
		
		@Override
		protected void animateNext() {	
			if (gameStarted == false && pusherChronology.size() != 0 && moves!=0 && crateMoves!=0 && beingLoaded == false) {
				
				System.out.println("mosse =" + moves);
				System.out.println("mosse casse =" + crateMoves);
				Coordinate it = pusherChronology.get(((-1)*moves)-1);
				pFromHere = ((-1)*moves)-1;
				
						if (gameStarted == false && cratesChronology.size()!=0 && it.getX() == cratesChronology.get((-1)*crateMoves-1).getX() && it.getY() == cratesChronology.get((-1)*crateMoves-1).getY()) {
							
							crateMoves = crateMoves + 2;								
							Coordinate c = cratesChronology.get((-1)*crateMoves);
							cFromHere = (-1)*crateMoves-1;
							currentState.setNextCrateState(c.getX(), c.getY(),beingLoaded);				
						}
				
				cFromHere = (-1)*crateMoves;
				moves++;
				currentState.setNextPusherState(it.getX(), it.getY());
				currentState.createEmpty(board);
				currentState.createWalls(board);						
				showCurrentState();
				repaint();
			} 
				
			else if ((moves == 0 && crateMoves == 0 && gameStarted == false && beingLoaded == false)) {
				int input = showInputDialogInt("Type to proceed: \n1 = Play From Start \n2 = Stay Here",  1);
				if (input == 1) { 						
					animateFinal();
					moves = 0;
				 	crateMoves = 0;
				 	pusherChronology.clear();
				 	cratesChronology.clear();
					board.clear();
					currentState.reset();
					currentState = new GameState(board.getRows(), board.getColumns());
					setInitialState();
					loadGraphics(readImage(new File("sokobanBG.png")));
					showCurrentState();
					repaint();		
				}
				
				else if (input == 2) { 
					stopAnimation();
					gameStarted = true;
					moves = points+keepMoves;
				 	crateMoves = keepCrateMoves;
				 	Collections.reverse(pusherChronology);
					Collections.reverse(cratesChronology);
				}
			}		
		}	
		
		@Override
		protected void animateFinal() {
			stopAnimation();
		}
		
		/**
		 * Performs and displays the next step of the pusher according to the objects that surround it
		 *
		 */
		private void movePusher() {
			Pusher p = currentState.getPusher();
			FieldObject object = currentState.getObject(p.getNextX(),p.getNextY());
			
			if (object.getClass() == EmptyPlace.class) {			
				
						walk();	
						currentState.createEmpty(board);		
						showCurrentState();
						board.repaint();
						moves--;
			}
			
			else if (object.getClass() == Crate.class) {   
				
					Crate nextObject = (Crate) object;
					moveCrate(nextObject);					
					currentState.checkWinState();
					
					if (currentState.getIsGameCompleted() == true) {
							gameStarted = false;
							showMessageDialog("Finish: You Earned " + (currentState.getPoints()+(moves)) + " Points", "Congratulations!");
							int input = showInputDialogInt("Type To Proceed: \n1 = Play Level 1 ;\n2 = Play Level 2 ;  \n3/Cancel = Stay Here",  2);
							
							if (input == 2) { 						
								board.clear();
								currentState.reset();
								currentState = new GameState(board.getRows(), board.getColumns());
								currentLevel = 2;
								setInitialState();
								loadGraphics(readImage(new File("sokobanBG.png")));
								showCurrentState();
								repaint();
								pusherChronology.clear();
								cratesChronology.clear();								
								moves = crateMoves = keepMoves = keepCrateMoves = pFromHere = cFromHere = 0;
								saveGraphicsFile(saved);
							}	
							else if (input == 1) {
								board.clear();							
								currentState.reset();
								currentState = new GameState(board.getRows(), board.getColumns());
								currentLevel = 1;								
								setInitialState();setBoard();
								showCurrentState();
								repaint();
								pusherChronology.clear();
								cratesChronology.clear();	
								moves = crateMoves = keepMoves = keepCrateMoves = pFromHere = cFromHere = 0;
								saveGraphicsFile(saved);
							}
					 }					
				}	
		}
		
		/**
		 * Performs and displays the next step of a crate according to the objects that surround it
		 *
		 */
		private void moveCrate(Crate nextObject) {		
			Crate c = nextObject;
			Coordinate direzione = currentState.getPusher().getHeadedTo();
			nextObject.setHeadedTo(direzione);
						
			if (currentState.getObject(nextObject.getNextX(), nextObject.getNextY()).getClass() == EmptyPlace.class) 
			{
				nextObject.move();	
				if (gameStarted == true ) {
					
					cratesChronology.add(new Coordinate(nextObject.getX(), nextObject.getY()));
					cratesChronology.add(new Coordinate(nextObject.getNextX(),nextObject.getNextY()));
				}
				
				currentState.setNextCrateState(nextObject.getNextX(), nextObject.getNextY(), beingLoaded);
				board.clear(c.getX(), c.getY());
				walk();
				moves--;
				crateMoves = crateMoves - 2;
				currentState.createEmpty(board);
				showCurrentState();
				board.repaint();		
			}			
		}
		
		@Override
		public void saveGraphicsFile(File file) {
			if (isAnimationEnabled()) { pauseAnimation(); }
			if (gameStarted == true) {
				PrintWriter writer, other;
				try {
					writer = new PrintWriter(saved);
					other = new PrintWriter(savedEmpty);
					writer.write(currentLevel + "\n" + moves + "\n");
					ArrayList<Coordinate> crateCoords = currentState.getCratesCoords();
					ArrayList<Coordinate> emptyCoords = currentState.getEmptyCoords();
					crateCoords.add(new Coordinate(currentState.getPusher().getX(),currentState.getPusher().getY()) );
					
					CoordinateIO.append(writer, crateCoords);	writer.close();	
					CoordinateIO.append(other, emptyCoords);	other.close();													
				} 
				catch (FileNotFoundException e1) {e1.printStackTrace();}			
			}
			else if (gameStarted == false) System.out.println("non puoi salvare da qui");			
		}

		public void loadGame() {
			board.clear();
			if (saved.length() == 0 || savedEmpty.length()==0) {setInitialState();}
			
			ArrayList<Coordinate> list = new ArrayList<Coordinate>();
			ArrayList<Coordinate> otherlist = new ArrayList<Coordinate>();
		    list = CoordinateIO.read(saved);
		    otherlist = CoordinateIO.read(savedEmpty);
		    points = 0;
		      
			FileInputStream fis;
			try {				
				fis = new FileInputStream(saved);				
				Scanner sc=new Scanner(fis);  
				currentLevel = sc.nextInt();				
				sc.nextLine();
				points = sc.nextInt();
				sc.close();   
			} 
			catch (FileNotFoundException e) {e.printStackTrace();}       
			   
			System.out.println("livello caricato = " + currentLevel );
		    System.out.println(" mosse caricate = " + moves);  
		      currentState = new GameState(board.getRows(), board.getColumns());
				
					if (currentLevel == 1) {currentState.initialize(levels.getLevel1());}		
					else if (currentLevel == 2) {currentState.initialize(levels.getLevel2());}
				
				setMenuVisible(true);
				setBoard();
				currentState.updateCrates(list);
				currentState.updateEmpty(otherlist);
				setCharacter(menu.getCharacterChoice().getChoice());
				currentState.buildWorld();
				for (int i = 0; i< list.size()-1; i++) {
					currentState.makeGreen(list.get(i).getX(), list.get(i).getY());
				}
				currentState.createEmpty(board);
				showCurrentState();
				currentState.setPoints(points);
				gameStarted = true;	
		}
		
		public void setCurrentLevel(int i) {currentLevel = i;}
		
		void Music() {
			class Music {
				File song;				
				Music() { song = new File("music.wav"); }
				
				public void run() throws UnsupportedAudioFileException, IOException, LineUnavailableException{

					AudioInputStream audioStream = AudioSystem.getAudioInputStream(song);
					Clip clip = AudioSystem.getClip();
					clip.open(audioStream);
					clip.start();	
					clip.loop(5);
				}				 
			}			
		Music music = new Music();
			try { music.run();} 
			catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
			}
		}
		
		public static void main(String[] args) {
			int rows = 19;
			int cols = 17;
			int size = 25;
			SokobanGameFrame frame = new SokobanGameFrame (rows, cols, size);
			
			frame.initializePanel();
			frame.start();	
			frame.Music();			
		}

		public AnimatedBG getPanel() { return bg; }		
	}