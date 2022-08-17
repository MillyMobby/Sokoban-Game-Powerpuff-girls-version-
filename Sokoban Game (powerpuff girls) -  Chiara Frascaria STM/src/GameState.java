import java.awt.Color;
import java.io.File;
import java.util.ArrayList;



public class GameState {
	private int rows;
	private int cols;
	private FieldObject objects[][];
	private ArrayList<Wall> walls;
	private ArrayList<Crate> crates;
	private ArrayList<EmptyPlace> storage;
	public ArrayList<EmptyPlace> empty;
	public ArrayList<Coordinate> crateCoords;
	private Pusher pusher;
	private Coordinate pusherLocation;
	private int storageAchieved;
	private int points;
	private boolean gameCompleted;
	
	/**
	 * Constructor
	 * @param rows
	 *      the rows of the matrix Objects
	 * @param cols
	 *      the columns of the matrix Objects
	 */
	
	public GameState(int righe, int colonne) {
			rows = righe;
			cols = colonne;
			objects = new FieldObject[rows][cols];
			walls = new ArrayList<>();
	        crates = new ArrayList<>();
	        storage = new ArrayList<>();
	        empty = new ArrayList<>();
	        crateCoords = new ArrayList<>();
	        pusherLocation = new Coordinate(1,1);
	        storageAchieved = 0;
	        points = 0;
	        gameCompleted = false;
	}
	
	/**
	 * Returns the class object of type pusher
	 */
	public Pusher getPusher() {
		return pusher;
	}
	
	/**
	 * sets the pusher's location
	 * @param x
	 * 		abscissa
	 * @param y
	 * 		ordinate
	 */
	public void setPusherLocation (int x, int y) {
		pusherLocation = new Coordinate (x,y);
	}
	
	/**
	 * Returns how many crates have been stored in the right place
	 */
	public int getStorageAchieved() {
		return storageAchieved;
	} 
	
	/**
	 * sets the player's score
	 * @param score
	 */
	public void setPoints (int score) {
		points = score;
		
	}
	
	/**
	 * Returns the score according to the game state
	 */
	public int getPoints() {
		return points;
	}
	
	/**
	 * Checks whether the game has been completed or not
	 */
	public boolean getIsGameCompleted() {
		return gameCompleted;
	}
	
	/**
	 * Gets the board object in a given position of the matrix Objects
	 * 
	 * @param x 
	 * 		abscissa
	 * @param y 
	 * 		ordinate
	 * 
	 * @return the object
	 */
	public FieldObject getObject(int x, int y) {
		return objects[x][y];
	}
	
	/**
	 * Gets the matrix boardObjects
	 */
	/*public FieldObject[][] getObjects() {
		FieldObject[][] insideMat = new FieldObject[rows][cols];
				
		for (int i =0; i <rows; i++) {		
			for(int j =0; j<cols; j++) {			
				insideMat[i][j] = objects[i][j];
			}
		}
		return insideMat;
	}
	
	public void setObjects (FieldObject[][] matrix) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				
				objects[i][j] = matrix[i][j];
			}
		}
		
	}*/
	
	/**
	 * Sets to type Empty the given position in the matrix Objects
	 * 
	 * @param x 
	 * 		abscissa
	 * @param y 
	 * 		ordinate
	 */
	public void freeCell(int x, int y) {
		objects[x][y] = new EmptyPlace(x,y,false);
	}
	
	/**
	 * Resets the matrix Objects
	 */
	public void reset() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				
				objects[i][j] = null;
			}
		}
	}
	
	/**
	 * Creates and draws the wall objects in the matrix according to the wall's array list and the board panel given in input
	 * 
	 * @param g
	 *       a JPanel for 2D graphics
	 */
	public void createWalls(BoardPanel g) {
		for (int i = 0; i < walls.size(); i++) {
               Wall object = walls.get(i);         
    			objects[object.getX()][object.getY()] = object;
    			g.drawOval(object.getX(), object.getY(), object.getColor(), object.getDensity());
    		}
    			
		}
	
	public ArrayList<Coordinate> getCratesCoords() {

		
		int c=0;
		for (int i = 0; i < rows ; i++) {
			for (int j = 0; j < cols ; j++) {
				if (objects[i][j] != null) {
					if (objects[i][j].getClass() == Crate.class) {
						
						crateCoords.add(c, new Coordinate(i, j));
						c++;
					}
				}
			}
		}
		return crateCoords;
	}
	
public ArrayList<Coordinate> getEmptyCoords() {
	ArrayList<Coordinate> emptyCoords = new ArrayList<>();
		
		int c=0;
		for (int i = 0; i < rows ; i++) {
			for (int j = 0; j < cols ; j++) {
				if (objects[i][j] != null) {
					if (objects[i][j].getClass() == EmptyPlace.class) {
						
						emptyCoords.add(c, new Coordinate(i, j));
						c++;
					}
				}
			}
		}
		return emptyCoords;
	}
	
public void updateEmpty(ArrayList<Coordinate> emptyCoords) {	
	empty.clear();	
	for (int i = 0; i< emptyCoords.size(); i++) {
		empty.add(new EmptyPlace(emptyCoords.get(i).getX(), emptyCoords.get(i).getY(), false));		
	}	
}
	
	
	public void updateCrates(ArrayList<Coordinate> crateCoords) {
		
		crates.clear();
		
		for (int i = 0; i< crateCoords.size()-1; i++) {
			crates.add(new Crate(crateCoords.get(i).getX(), crateCoords.get(i).getY()));
			System.out.println("crate added");
		}
		
		setPusherLocation(crateCoords.get(crateCoords.size()-1).getX(),crateCoords.get(crateCoords.size()-1).getY());
		pusher = new Pusher(pusherLocation.getX(), pusherLocation.getY());
	}

	/**
	 * Returns the crates' array list
	 */
	public ArrayList<Crate> getCrates() {
		return crates;
	}

	/**
	 * Returns the storages' array list
	 */
	public ArrayList<EmptyPlace> getStorages() {
		return storage;
	}
	
	/**
	 * Creates and draws the objects of type empty 
	 */
	public void createEmpty (BoardPanel g) {
		for (int i = 0; i < storage.size(); i++) {

            EmptyPlace object = storage.get(i);
            
           if (objects[object.getX()][object.getY()].getClass()!= Pusher.class && objects[object.getX()][object.getY()].getClass()!= Crate.class) {
    			objects[object.getX()][object.getY()] = object;
    			g.drawOval(object.getX(), object.getY(), object.getColor(), object.getDensity());
    			}
    			
		}
	}
	
	/**
	 * Returns the empty's array list
	 */
	public ArrayList<EmptyPlace> getEmpties() {
		return empty;
	}
	
	/**
	 * builds the Objects' matrix by reading an array list containing all the objects
	 */
	public void buildWorld () {

        ArrayList<FieldObject> world = new ArrayList<>();

        world.addAll(walls);
        world.addAll(storage);
        world.addAll(crates);
        world.addAll(empty);
        world.add(new Pusher (pusherLocation.getX(), pusherLocation.getY()));

        for (int i = 0; i < world.size(); i++) {

            FieldObject object = world.get(i);
            if (object instanceof Wall) {
            	objects[object.getX()][object.getY()] = object;
    			//g.drawRectangle(object.getX(), object.getY(), object.getColor(), object.getDensity());
    		
            } else if (object.getClass() == Pusher.class) {
            	objects[object.getX()][object.getY()] = object;
                //g.drawOval(object.getX(), object.getY(), object.getColor(), object.getDensity());
            
            } else if (object instanceof Crate) {
            	objects[object.getX()][object.getY()] = object;
    			//g.drawRectangle(object.getX(), object.getY(), object.getColor(), object.getDensity());
    		
            } else if ((object instanceof EmptyPlace) && (((EmptyPlace) object).getIsStorage()== true)) {
    			objects[object.getX()][object.getY()] = object;
    			//g.drawOval(object.getX(), object.getY(), object.getColor(), object.getDensity());
    		
            } else if ((object.getClass() == EmptyPlace.class) && (((EmptyPlace) object).getIsStorage()== false)) {
    			objects[object.getX()][object.getY()] = object;
    			//g.drawOval(object.getX(), object.getY(), object.getColor(), object.getDensity());
    		}
        }
    }
		
	/**
	 * Puts the pusher in the matrix Objects
	 */
	public void createPusher() {		
		pusher = new Pusher(pusherLocation.getX(),pusherLocation.getY());
		objects[pusherLocation.getX()][pusherLocation.getY()] = new Pusher(pusherLocation.getX(), pusherLocation.getY());		
	}
	
	/**
	 * Reads all the objects of the game board from a string, obtains their coordinates and puts them in the respective array lists
	 * @param level
	 * 		a string with an objects' configuration that represents a game level
	 */
	public void initialize(String level) {
        String righe[] = level.split("\n");
        System.out.println("ROWS " + rows);
        System.out.println("COLS "+ cols);
        
        for (int i = 0; i< righe.length; i++) {
        	for (int j = 0; j < righe[i].length(); j++) {
        		char item = righe[i].charAt(j);
	        	switch (item) {
	        	case '#':
	        		     walls.add(new Wall (i+1, j+1));
		        		 //System.out.println("muro in posizione "+ (i+1) + ", " + (j+1));	        		 
	        		 break;
	
	            case '$': 
		        		 crates.add(new Crate(i+1, j+1));
		        		 System.out.println("cassa in posizione "+ (i+1) + ", " + (j+1));
	        		 break;
	
	            case '.':
		        		 storage.add(new EmptyPlace(i+1, j+1, true));
		        		 //System.out.println("storage in posizione "+ (i+1) + ", " + (j+1));
	        		 break;
	
	        	case '@':	        		  		        		  
	        		      pusher = new Pusher (i+1,j+1);
		        		  pusherLocation = new Coordinate(i+1, j+1);	        		  
		        		  //System.out.println("player in posizione "+ (i+1) + ", " + (j+1));	        		  
	        		  break;
	
	        	case '-':	        				        		 
		        		 empty.add(new EmptyPlace(i+1, j+1, false));
		        		 // System.out.println("vuoto generico in posizione "+ (i+1) + ", " + (j+1));	        			
	        		 break; 
	
	            default:
	        		break;	        	
	           }
        	}
       	}        
    }		

	/**
	 * Returns next object in the matrix that lays on the pusher's current direction
	 */
	public FieldObject controlPusherNext(Pusher pusher) {
		return objects[pusher.getNextX()][pusher.getNextY()];
	}
	
	/**
	 * Returns next object in the matrix that lays on the crate's current direction
	 */
	public FieldObject controlCrateNext(Crate crate) {
		return objects[crate.getNextX()][crate.getNextY()];
	}
	
	/**
	 * Sets the next state of the pusher in the matrix Objects
	 * 
	 * @param newX 
	 * 		new abscissa
	 * @param newY 
	 * 		new ordinate
	 */
	public void setNextPusherState(int newX, int newY) {		
		Pusher player = new Pusher (pusher.getX(), pusher.getY());	
		freeCell(player.getX(), player.getY());
			if (objects[newX][newY] != null && objects[newX][newY].getClass() != Wall.class)
			    pusher.setPosition(newX, newY);
			    objects[newX][newY] = pusher;					
	}
	
	/**
	 * Sets the next state of a crate in the matrix Objects
	 * 
	 * @param newX 
	 * 		new abscissa
	 * @param newY 
	 * 		new ordinate
	 */
	public void setNextCrateState(int newX, int newY, boolean loaded) {		
		Crate crate = new Crate (newX, newY);		
		if (loaded ==false) {freeCell(pusher.getNextX(), pusher.getNextY());}
		
			if (objects[newX][newY].getClass()== EmptyPlace.class && ((EmptyPlace)objects[newX][newY]).getIsStorage() == true) {
				crate.setColorInStorage();
			}	
			
		crate.setPosition(newX, newY);		
		objects[newX][newY] = crate;	
	}
	
	public void makeGreen(int x, int y) {
		Crate crate = new Crate (x, y);	
		for(int i=0; i<storage.size();i++) {
			if (storage.get(i).getPosition().getX() == x && storage.get(i).getPosition().getY() == y) {
				crate.setColorInStorage();
				crate.setPosition(x, y);
				objects[x][y] = crate;	
			}
		}
		
	}
	
	/**
	 * Checks if the player has completed the game
	 */
	public void checkWinState (){
		int count = 0;
		for (int i = 0; i < storage.size(); i++) {
            EmptyPlace object = storage.get(i);
            
	           if (objects[object.getX()][object.getY()].getColor() == Color.GREEN) {
	        	   count++;
	        	   //System.out.println("ORA LE CASSE A POSTO SONO "+ count + " SU " + storage.size());
	        	   }
	    }	
		
	  if (count == storage.size()) {
		setPoints((count*50));
		gameCompleted = true;
	  }
	}
	
	public void loadState(ArrayList<Coordinate> list) {
		
		updateCrates(list);
	}
	
}
