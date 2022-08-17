import java.awt.Color;

public abstract class FieldObject {
	protected int x;
	protected int y;	
	protected int density;
	protected Color colore;
	protected Boolean isEmpty;
	
	/**
	 * Constructor
	 * @param x
	 * 		abscissa
	 * @param y
	 * 		ordinate
	 */
	public FieldObject (int ascissa, int ordinata) {
		setPosition(ascissa, ordinata);
	}
	
	/**
	 * Sets the position's coordinates
	 * @param x
	 * 		abscissa
	 * @param y
	 * 		ordinate
	 */
	public void setPosition(int ascissa, int ordinata) {
		x = ascissa;
		y = ordinata;
	}
	
	/**
	 *Returns the position of the object
	 */
	public Coordinate getPosition() {
		return (new Coordinate(x, y));
	}
	
	/**
	 *Returns the coordinate x of the position of the object
	 */
	public int getX() {
		return x;
	}
	
	/**
	 *Returns the coordinate y of the position of the object
	 */
	public int getY() {
		return y;
	}
	
	/**
	 *sets the density of the object (abstract method)
	 */
	public abstract void setDensity();
	
	/**
	 *Returns the density of the object 
	 */
	public int getDensity() {
		return density;
	}
	
	/**
	 *sets the color of the object (abstract method)
	 */
	public abstract void setColor();
	
	/**
	 *Returns the color of the object 
	 */
	public Color getColor() {
		return colore;
	}
	
	/**
	 *Sets the object as empty
	 */
	public void setIsEmpty(Boolean status) {
		isEmpty = status;
	}
}
