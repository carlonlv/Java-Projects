package farmyard;
import farm_simulation.FarmSimulator;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class FarmProduct {
    /** How this FarmProduct appears on the screen. */
    private String appearance;
    /** This FarmProduct's x coordinate. */
    private int xCoordinate;
    /** This FarmProduct's y coordinate. */
    private int yCoordinate;
    /** The colour of this FarmProduct. */
    private Color colour;

    /**
     * @return the appearance of this farm product.
     */
    private String getAppearance() {
        return appearance;
    }

    /**
     * @param appearance the appearance the product becomes.
     */
    private void setAppearance(String appearance) {
        this.appearance = appearance;
    }

    /**
     * @return the x coordinate of this product.
     */
    int getXCoordinate() {
        return xCoordinate;
    }

    /**
     * @return the y coordinate of this product.
     */
    int getYCoordinate() {
        return yCoordinate;
    }

    /**
     * @return the current colour of this product.
     */
    private Color getColour() {
        return colour;
    }

    /**
     * @param colour the colour of this product it becomes.
     */
    private void setColour(Color colour) {
        this.colour = colour;
    }

    /**
     * Set this product's location.
     *
     * @param xCoordinate the first coordinate.
     * @param yCoordinate the second coordinate.
     */
    void setLocation(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    /**
     * Draws this farm product.
     * @param g the graphics context in which to draw this item.
     */
    public void draw(GraphicsContext g) {
        g.setFill(this.getColour());
        g.fillText(this.getAppearance(), this.getXCoordinate() * FarmSimulator.getCharWidth(), this.getYCoordinate() * FarmSimulator.getCharHeight());
    }

    /**
     * Constructs a new product at the specified cursor location (x, y).
     *
     * @param appearance the String representation of the food.
     * @param xCoordinate the x co-ordinate of the food's cursor location.
     * @param yCoordinate the y co-ordinate of the food's cursor location.
     */
    public FarmProduct(String appearance, int xCoordinate, int yCoordinate, Color colour){
        this.setAppearance(appearance);
        this.setColour(colour);
        this.setLocation(xCoordinate, yCoordinate);
    }
}
