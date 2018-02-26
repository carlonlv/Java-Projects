package farmyard;

import farm_simulation.FarmSimulator;
import javafx.scene.paint.Color;

public class Pig extends FarmCreature {

    /** This pig's hunger status. */
    private boolean hunger = true;

    /** Constructs a new Pig. */
    public Pig(int xCoordinate, int yCoordinate) {
        super("(8):",":(8)", xCoordinate, yCoordinate, Color.PINK.darker());
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
                    super.moveToTarget(1);
                } else {
                    FarmSimulator.getFarmProducts().remove(this.getMyTarget());
                    System.out.println("Pig" + FarmSimulator.getFarmCreatures().indexOf(this) + ": I got the food.");
                    this.setHunger(false);
                    this.setMyTarget(null);
                }
            } else {
                this.setMyTarget(null);
            }
        }   else {
            super.roam();
            this.digest();
            /*Only find a new target if I am hungry.*/
            if (isHungry()) {
                for (FarmProduct farmProduct : FarmSimulator.getFarmProducts()) {
                    if (farmProduct instanceof AnimalFood) {
                        this.setMyTarget(farmProduct);
                    }
                }
            }
        }
    }

    /**
    * Helps animal clear stomach
    */
    private void digest() {
        if (Math.random() < 0.04 && !isHungry()) {
            System.out.println("Pig" + FarmSimulator.getFarmCreatures().indexOf(this) + ": Pig stink.");
            FarmSimulator.getFarmProducts().add(new AnimalManure("*", super.getXCoordinate(), super.getYCoordinate()));
            this.setHunger(true);
            System.out.println("Pig" + FarmSimulator.getFarmCreatures().indexOf(this) + ": Now I'm hungry.");
        }
    }

    /**
     * @return true if this pig is hungry.
     */
    private boolean isHungry() {
        return hunger;
    }

    /**
     * @param hunger true if this pig is going to be hungry.
     */
    private void setHunger(boolean hunger) {
        this.hunger = hunger;
    }
}
