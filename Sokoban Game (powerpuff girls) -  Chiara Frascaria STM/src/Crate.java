import java.awt.Color;

public class Crate extends Pusher {
	
	private Boolean isInStorage;
	
	/**
	 * Constructor
	 * @param x
	 * 		abscissa
	 * @param y
	 * 		ordinate
	 */
	public Crate (int ascissa, int ordinata) {
		super(ascissa, ordinata);		
		isInStorage = false;
	}
	
	public void setDensity() {
		density = 70;
	}
	
	public void setColor() {		
		colore = Color.YELLOW;
	}
	
	/**
	 * Sets the color of the crate to green for when the crate is moved in a storage position
	 */
	public void setColorInStorage () {
		colore = Color.GREEN;
	} 

	/**
	 * Sets the state of the crate
	 */
	public void setIsInStorage(Boolean state) {
		isInStorage = state;
	}
	
	/**
	 Returns if the crate is in storage or not
	 */
	public boolean getIsInStorage() {
		return isInStorage;
	}
	
}
