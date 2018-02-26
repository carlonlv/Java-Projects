package farmyard;

import javafx.scene.paint.Color;

/** This is a pile of animal manure */
public class AnimalManure extends FarmProduct {

    /**
     * Constructs a new manure at the specified cursor location (x, y).
     *
     * @param appearance the String representation of the manure.
     * @param xCoordinate the x co-ordinate of the manure's cursor location.
     * @param yCoordinate the y co-ordinate of the manure's cursor location.
     */
    public AnimalManure(String appearance, int xCoordinate, int yCoordinate) {
        super(appearance, xCoordinate, yCoordinate, Color.BLACK.darker());
    }
}
