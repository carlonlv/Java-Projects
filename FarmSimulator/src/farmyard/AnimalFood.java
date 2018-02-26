package farmyard;

import farm_simulation.FarmSimulator;
import javafx.scene.paint.Color;

/** This is a piece of animal food */
public class AnimalFood extends FarmProduct {

    /**
    * Constructs a new food at the specified cursor location (x, y).
    *
    * @param xCoordinate the x co-ordinate of the food's cursor location.
    * @param yCoordinate the y co-ordinate of the food's cursor location.
    */
    public AnimalFood(int xCoordinate, int yCoordinate) {
        super("%", xCoordinate, yCoordinate, Color.GRAY.darker());
    }

    /**
    * Causes animal food blown up, down, left or right by wind.
    */
    public void blownByWind() {
      /* how animal food respond to horizontal wind*/
      if (Wind.getBlowingRight() == 1){
          if (super.getXCoordinate() <= Math.floorDiv(1024, FarmSimulator.getCharWidth()) - 1) {
              super.setLocation(super.getXCoordinate() + 1, super.getYCoordinate());
          }
      } else if (Wind.getBlowingRight() == -1 ) {
          if (super.getXCoordinate() >= 2) {
              super.setLocation(super.getXCoordinate() - 1, super.getYCoordinate());
          }
      }
      /* how animal food respond to vertical wind*/
      if (Wind.getBlowingUp() == 1){
          if (super.getYCoordinate() >= 2) {
              /*The y value gets smaller when going upwards the canvas, so have to set y coordinate smaller*/
              super.setLocation(super.getXCoordinate(), super.getYCoordinate() - 1);
          }
      } else if (Wind.getBlowingUp() == -1 ) {
          if (super.getYCoordinate() <= Math.floorDiv(720, FarmSimulator.getCharHeight()) - 1) {
              /*The y value gets bigger when going downwards the canvas, so have to set y coordinate bigger*/
              super.setLocation(super.getXCoordinate(), super.getYCoordinate() + 1);
          }
      }
    }
}
