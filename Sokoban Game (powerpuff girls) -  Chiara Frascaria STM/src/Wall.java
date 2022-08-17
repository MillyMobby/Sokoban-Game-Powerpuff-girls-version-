import java.awt.Color;

public class Wall extends FieldObject {
	private Boolean movable;
	
	/**
	 * Constructor
	 * @param x
	 * 		abscissa
	 * @param y
	 * 		ordinate
	 */
	public Wall(int ascissa, int ordinata) {
		super(ascissa, ordinata);
		isEmpty = false;
		setColor();
		setDensity();
		movable = false;
	}
	
	@Override
	public void setDensity() {
		density = 100;
	}

	@Override
	public void setColor() {
		colore = Color.DARK_GRAY;
	}
	
}
