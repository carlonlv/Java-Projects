package farmyard;

import farm_simulation.FarmSimulator;
import javafx.scene.paint.Color;

public class Mouse extends FarmCreature{

    /** Constructs a new Pig. */
    public Mouse(int xCoordinate, int yCoordinate) {
        super("~(\u03B5:>","<:3)~", xCoordinate, yCoordinate, Color.GREEN.darker());
    }

    /**
     * Causes this item to take its turn in the farm-pen simulation.
     * */
    @Override
    public void move() {
        if (this.getMyTarget() != null) {
            /*This while loop checks if another chicken or pig has eaten the food already!*/
            if (FarmSimulator.getFarmProducts().contains(this.getMyTarget())) {
                if ((super.getXCoordinate() != this.getMyTarget().getXCoordinate()) || (super.getYCoordinate() != this.getMyTarget().getYCoordinate())) {
                    /*The mouse run with speed of 2 towards the target.*/
                    super.moveToTarget(2);
                } else {
                    FarmSimulator.getFarmProducts().remove(this.getMyTarget());
                    System.out.println("Mouse" + FarmSimulator.getFarmCreatures().indexOf(this) + ": I stole the food.");
                    this.setMyTarget(null);
                }
            } else {
                this.setMyTarget(null);
            }
        }   else {
            /*The roam speed of the mouse is the same as pigs and chickens.*/
            super.roam();
            for (FarmProduct farmProduct : FarmSimulator.getFarmProducts()) {
                if (farmProduct instanceof AnimalFood) {
                    this.setMyTarget(farmProduct);
                }
            }
        }
    }
}
