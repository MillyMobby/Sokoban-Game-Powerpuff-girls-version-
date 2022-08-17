import java.awt.Color;
import java.util.ArrayList;

public class Pusher extends FieldObject {
	protected static final int LEFT = 1;
	protected static final int UP = 2;
	protected static final int RIGHT = 3;
	protected static final int DOWN = 4;
	protected static ArrayList<Coordinate> directions;		
	protected Coordinate position;
	protected Coordinate headedTo;
	
	/**
	 * Constructor
	 * @param x
	 * 		abscissa
	 * @param y
	 * 		ordinate
	 */
	public Pusher(int ascissa, int ordinata) {
		super(ascissa, ordinata);
		setColor();
		setDensity();
		isEmpty = false;
		setDirections();
		setPosition(ascissa, ordinata);
		headedTo = new Coordinate(0,-1);
	}

	/**
	 * creates an array list of coordinates containing all the possible directions to take
	 */
	public static void setDirections() {
		directions = new ArrayList<Coordinate>(3);		
			directions.add(0, new Coordinate(0,-1)); 	//left
			directions.add(1, new Coordinate(-1,0));	//up
			directions.add(2, new Coordinate(0,1));		//right
			directions.add(3, new Coordinate(1,0));		//down		
	}
	
	/**
	 * Gets the direction from the array list at index i
	 * @param i
	 * 		index
	 */
	public Coordinate getDirections (int i ) {
		return directions.get(i);
	}
	
	/**
	 * sets the position of the pusher
	 * @param x
	 * 		abscissa
	 * @param y
	 * 		ordinate
	 */
	public void setPosition(int x, int y) {
		position = new Coordinate(x,y);
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Returns the position of the pusher
	 */
	public Coordinate getPosition() {
		return position;
	}
	
	/**
	 * Returns the next x coordinate in front of the pusher according to his direction
	 */
	public int getNextX() {
		return getX() + headedTo.getX();
	}

	/**
	 * Returns the next y coordinate in front of the pusher according to his direction
	 */
	public int getNextY() {
		return getY() + headedTo.getY();
	}
	
	/*public int getPreviousX () {
		return getX() - headedTo.getX();
	}
	
	public int getPreviousY() {
		return getY() - headedTo.getY();
	}*/
	
	public void setDensity() {
		// TODO Auto-generated method stub
		density = 200;
	}

	public void setColor() {
		// TODO Auto-generated method stub
		colore = Color.BLACK;
	}
	
	/**
	 * Sets the direction of the pusher
	 */
	public void setHeadedTo(Coordinate direction) {
		headedTo = direction;
	}

	/**
	 * Returns the direction of the pusher
	 */
	public Coordinate getHeadedTo() {
		return headedTo;
	}
	
	/**
	 * changes the direction of the pusher according to the given value 
	 * @param command
	 * 			the input
	 */
	public void TakeDirection(int command) {
		switch (command) {
		case LEFT:			
			setHeadedTo(directions.get(0));//(0,-1)			
			break;
			
		case UP:
			setHeadedTo(directions.get(1));	//(-1,0)
			break;
			
		case RIGHT:
			setHeadedTo(directions.get(2));  //(0,1)
			break;
			
		case DOWN:
			setHeadedTo(directions.get(3));   //(1,0)
			break;			
		}
	}

	/**
	 * Moves the pusher
	 */
	public void move() {
		if (headedTo == directions.get(0)) { //left			
			position = new Coordinate(getNextX(),y);
			x = getNextX();
		}
		else if (headedTo == directions.get(1)) { //up			
			position = new Coordinate (x,getNextY()); 
			y = getNextY();
		}
		else if (headedTo == directions.get(2)) { //right			
			position = new Coordinate(getNextX(),y);
			x = getNextX();
		}
		else if (headedTo == directions.get(3)) { //down			
			position = new Coordinate (x,getNextY()); 
			y = getNextY();
		}
	}

	/**
	 * Returns the next coordinates in front of the pusher according to his direction
	 */
	public Coordinate getNextPosition() {
		Coordinate a = new Coordinate(getNextX(), getNextY());
		return a;
	}
}
