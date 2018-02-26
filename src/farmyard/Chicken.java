package farmyard;

import farm_simulation.FarmSimulator;
import javafx.scene.paint.Color;

/** This is a chicken.*/
public class Chicken extends FarmCreature{

    /* This shows this chicken is hungry or not.*/
    private boolean hunger = true;

    /**
     * Constructs a new chicken at the specified cursor location (x, y).
     *
     * @param xCoordinate the x co-ordinate of the manure's cursor location.
     * @param yCoordinate the y co-ordinate of the manure's cursor location.
     */
    public Chicken(int xCoordinate, int yCoordinate) {
        super("/'/>", "<\\'\\" ,xCoordinate, yCoordinate, Color.RED.darker());
    }

    /**
     * Overrides the move method at FarmCreature.java
     */
    @Override
    public void move() {
        if (this.getMyTarget() != null) {
            /*This while loop checks if another chicken or pig has eaten the food already! */
            if (FarmSimulator.getFarmProducts().contains(this.getMyTarget())) {
                /*This if condition checks if this chicken has arrived at the food's coordinate. */
                if ((super.getXCoordinate() != this.getMyTarget().getXCoordinate()) || (super.getYCoordinate() != this.getMyTarget().getYCoordinate())) {
                    super.moveToTarget(1);
                } else {
                    /* Eats the food by removing the food globally*/
                    FarmSimulator.getFarmProducts().remove(this.getMyTarget());
                    System.out.println("Chicken" + FarmSimulator.getFarmCreatures().indexOf(this) + ": I got the food.");
                    this.setHunger(false);
                    this.setMyTarget(null);
                }
            } else {
                this.setMyTarget(null);
            }
        } else {
            super.roam();
            this.layEgg();
            this.digest();
            /*Only find a new target if I am hungry. */
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
   * Causes this chicken to lay an egg.
   */
    private void layEgg() {
        if (Math.random() < 0.05 && !isHungry()) {
            System.out.println("Chicken" + FarmSimulator.getFarmCreatures().indexOf(this) + ": I just laid an egg at " + super.getXCoordinate() + " " + super.getYCoordinate());
            FarmSimulator.getFarmProducts().add(new Egg(super.getXCoordinate(), super.getYCoordinate()));
        }
    }

    /**
    *  Causes this chicken to digest and leave a pile of manure. The chicken becomes hungry after the digestion.
    */
    private void digest() {
        if (Math.random() < 0.03 && !isHungry()) {
            System.out.println("Chicken" + FarmSimulator.getFarmCreatures().indexOf(this) + ": Chicken stink.");
            FarmSimulator.getFarmProducts().add(new AnimalManure(".", super.getXCoordinate(), super.getYCoordinate()));
            this.setHunger(true);
            System.out.println("Chicken" + FarmSimulator.getFarmCreatures().indexOf(this) + ": Now I'm hungry.");
        }
    }

    /**
     * @return if the chicken is currently hungry.
     */
    private boolean isHungry() {
        return hunger;
    }

    /**
     * @param hunger true if the chicken becomes hungry false if the chicken does not.
     */
    private void setHunger(boolean hunger) {
        this.hunger = hunger;
    }
}
