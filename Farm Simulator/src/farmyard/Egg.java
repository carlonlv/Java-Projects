package farmyard;

import javafx.scene.paint.Color;

/** An egg that a farmer collects. */
public class Egg extends FarmProduct{

    /**
     * Constructs a new egg at the specified cursor location (x, y).
     *
     * @param xCoordinate the x co-ordinate of the egg's cursor location.
     * @param yCoordinate the y co-ordinate of the egg's cursor location.
     */
    public Egg(int xCoordinate, int yCoordinate) {
         super("o", xCoordinate, yCoordinate, Color.ROSYBROWN.darker());
    }
}
