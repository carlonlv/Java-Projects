package farmyard;

import farm_simulation.FarmSimulator;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/*A farm creature which can be a human, a chicken or a pig.*/
public abstract class FarmCreature {

    /**
     * How this lovely creature currently appears on the screen.
     */
    private String currentAppearance;
    /**
     * How this lovely creature currently appears on the screen reversed.
     */
    private String reversedAppearance;
    /**
     * Stores creature's right appears on the screen.
     */
    private String rightAppearance;
    /**
     * Stores creature's left appears on the screen.
     */
    private String leftAppearance;
    /**
     * Indicates whether this creature is moving right.
     */
    private boolean goingRight;
    /**
     * This creature's x coordinate.
     */
    private int xCoordinate;
    /**
     * This creature's y coordinate.
     */
    private int yCoordinate;
    /**
     * This creature's colour.
     */
    private Color colour;
    /**
     * This Mouse's target.
     */
    private FarmProduct myTarget;

    /**
     * @return the current appearance of this creature.
     */
    private String getCurrentAppearance() {
        return currentAppearance;
    }

    /**
     * @return the current reversed appearance of this creature.
     */
    private String getReversedAppearance() {
        return reversedAppearance;
    }

    /**
     * @param appearance the appearance the creature becomes.
     */
    private void setCurrentAppearance(String appearance) {
        this.currentAppearance = appearance;
    }

    /**
     * @param reversedAppearance the reversed appearance the creature gets.
     */
    private void setReversedAppearance(String reversedAppearance) {
        this.reversedAppearance = reversedAppearance;
    }

    /**
     * @return the target of this creature.
     */
    FarmProduct getMyTarget() {
        return myTarget;
    }

    /**
     * @param myTarget the next target of this creature.
     */
    void setMyTarget(FarmProduct myTarget) {
        this.myTarget = myTarget;
    }

    /**
     * Causes the creature to roam, move and other behaviors.
     */
    public abstract void move();

    /**
     * @return the right appearance.
     */
    private String getRightAppearance() {
        return rightAppearance;
    }

    /**
     * @param rightAppearance the right appearance.
     */
    private void setRightAppearance(String rightAppearance) {
        this.rightAppearance = rightAppearance;
    }

    /**
     * @return the left appearance.
     */
    private String getLeftAppearance() {
        return leftAppearance;
    }

    /**
     * @param leftAppearance the left appearance.
     */
    private void setLeftAppearance(String leftAppearance) {
        this.leftAppearance = leftAppearance;
    }

    /**
     * @return true if this creature is currently going right.
     */
    private boolean isGoingRight() {
        return goingRight;
    }

    /**
     * @param goingRight true if the creature is going right.
     */
    private void setGoingRight(boolean goingRight) {
        this.goingRight = goingRight;
    }

    /**
     * @return the x coordinate of this creature.
     */
    int getXCoordinate() {
        return xCoordinate;
    }

    /**
     * @return the y coordinate of this creature.
     */
    int getYCoordinate() {
        return yCoordinate;
    }

    /**
     * @return the current colour of this creature.
     */
    private Color getColour() {
        return colour;
    }

    /**
     * @param colour the colour of this creature it becomes.
     */
    private void setColour(Color colour) {
        this.colour = colour;
    }

    /**
     * Set this creature's location.
     *
     * @param xCoordinate the first coordinate.
     * @param yCoordinate the second coordinate.
     */
    private void setLocation(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    /**
     * Constructs a new creature which can be human, chicken or pig.
     */
    public FarmCreature(String RightAppearance, String LeftAppearance, int xCoordinate, int yCoordinate, Color colour) {
        //Right appearance is the current appearance by default.
        this.setRightAppearance(RightAppearance);
        this.setCurrentAppearance(this.getRightAppearance());
        this.setLeftAppearance(LeftAppearance);
        this.setReversedAppearance(this.getLeftAppearance());
        this.setColour(colour);
        this.setLocation(xCoordinate, yCoordinate);
        this.setGoingRight(true);
    }

    /**
     * Draws this farm creature.
     *
     * @param g the graphics context in which to draw this item.
     */
    public void draw(GraphicsContext g) {
        g.setFill(this.getColour());
        g.fillText(this.getCurrentAppearance(), this.getXCoordinate() * FarmSimulator.getCharWidth(), this.getYCoordinate() * FarmSimulator.getCharHeight());
    }

    /**
     * Causes this farm creature to move left.
     */
    private void moveLeft(int speed) {
        this.setCurrentAppearance(this.getLeftAppearance());
        this.setReversedAppearance(this.getRightAppearance());
        this.setGoingRight(false);
        this.setLocation(this.getXCoordinate() - speed, this.getYCoordinate());
    }

    /**
     * Causes this farm creature to move right.
     */
    private void moveRight(int speed) {
        this.setCurrentAppearance(this.getRightAppearance());
        this.setReversedAppearance(this.getLeftAppearance());
        this.setGoingRight(true);
        this.setLocation(this.getXCoordinate() + speed, this.getYCoordinate());
    }

    /**
     * Turns this creature around, causing it to reverse direction.
     */
    private void turnAround() {
        this.setGoingRight(!goingRight);
        String localString = this.getCurrentAppearance();
        this.setCurrentAppearance(this.getReversedAppearance());
        this.setReversedAppearance(localString);
    }

    /**
     * Causes this farm creature to roam around randomly.
     */
    void roam() {
        double d = Math.random();
        if (d < 0.1) {
            this.turnAround();
        }
        if (this.isGoingRight()) {
            d = Math.random();
            if (d < 0.2) {
                this.setLocation(this.getXCoordinate(), this.getYCoordinate() + 1);
            } else if (d < 0.4) {
                this.setLocation(this.getXCoordinate(), this.getYCoordinate() - 1);
            }
            this.setLocation(this.getXCoordinate() + 1, this.getYCoordinate());
        } else {
            d = Math.random();
            if (d < 0.2) {
                this.setLocation(this.getXCoordinate(), this.getYCoordinate() + 1);
            } else if (d < 0.4) {
                this.setLocation(this.getXCoordinate(), this.getYCoordinate() - 1);
            }
            this.setLocation(this.getXCoordinate() - 1, this.getYCoordinate());
        }

        if (this.getXCoordinate() <= 1) {
            this.moveRight(1);
        } else if (this.getXCoordinate() >= Math.floorDiv(1024, FarmSimulator.getCharWidth()) - 1) {
            this.moveLeft(1);
        }
        if (this.getYCoordinate() <= 1) {
            this.setLocation(this.getXCoordinate(), this.getYCoordinate() + 1);
        } else if (this.getYCoordinate() >= Math.floorDiv(720, FarmSimulator.getCharHeight()) - 1) {
            this.setLocation(this.getXCoordinate(), this.getYCoordinate() - 1);
        }
    }

    void moveToTarget(int speed) {
        if (this.getXCoordinate() < this.getMyTarget().getXCoordinate()) {
            this.moveRight(speed);
        } else if (this.getXCoordinate() > this.getMyTarget().getXCoordinate()) {
            this.moveLeft(speed);
        }
        if (this.getYCoordinate() < this.getMyTarget().getYCoordinate()) {
            this.setLocation(this.getXCoordinate(), this.getYCoordinate() + speed);
        } else if (this.getYCoordinate() > this.getMyTarget().getYCoordinate()) {
            this.setLocation(this.getXCoordinate(), this.getYCoordinate() - speed);
        }
    }
}