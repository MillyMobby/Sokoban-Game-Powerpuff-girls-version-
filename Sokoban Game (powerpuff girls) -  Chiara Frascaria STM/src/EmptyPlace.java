import java.awt.Color;

public class EmptyPlace extends FieldObject {
	
	private Boolean isStorage;
	
	/**
	 * Constructor
	 * @param x
	 * 		abscissa
	 * @param y
	 * 		ordinate
	 */
	public EmptyPlace(int ascissa, int ordinata, boolean isStorage) {
		super(ascissa, ordinata);
		setIsStorage(isStorage);
		setColor(isStorage);
		setDensity();
		isEmpty = true;
	}
	
	/**
	 * Sets type of the empty object
	 * @param status
	 * 		true = storage, false = generic empty
	 */
	public void setIsStorage (boolean status) {
		isStorage = status;
	}
	
	/**
	 * Gets the type of the empty object
	 */
	public Boolean getIsStorage () {
		return isStorage;
	}
	
	@Override
	public void setColor() {
		colore = Color.MAGENTA;

	}
	
	/**
	 * Sets the color for the type storage
	 */
	public void setColor(boolean isStorage) {
		if (isStorage == false) {
			colore = Color.PINK;
		}
		colore = Color.MAGENTA;
	}
	@Override
	public void setDensity() {
		if (isStorage == true) {density = 25;}		
	}
	
}
