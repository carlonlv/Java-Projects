package farmyard;

import farm_simulation.FarmSimulator;
import javafx.scene.paint.Color;
import java.util.ArrayList;

/** A Human */
public class Human extends FarmCreature {

    /** The basket the human used to hold eggs in.*/
    private static ArrayList<Egg> myBasket = new ArrayList<>();

    /**
     * Constructs a new Human.
     */
    public Human(int xCoordinate, int yCoordinate) {
        super("(>_>)", "(<_<)", xCoordinate, yCoordinate, Color.SANDYBROWN.darker());
    }

    /**
     * Causes human to drop down 4 piece s of food all around.
     */
    private void dropFood() {
        if (Math.random() < 0.10) {
            FarmSimulator.getFarmProducts().add(new AnimalFood(super.getXCoordinate() - 1, super.getYCoordinate() - 1));
            FarmSimulator.getFarmProducts().add(new AnimalFood(super.getXCoordinate() - 1, super.getYCoordinate() + 1));
            FarmSimulator.getFarmProducts().add(new AnimalFood(super.getXCoordinate() + 1, super.getYCoordinate() - 1));
            FarmSimulator.getFarmProducts().add(new AnimalFood(super.getXCoordinate() + 1, super.getYCoordinate() + 1));
        }
    }

    /**
     * Causes this item to take its turn in the farm-pen simulation.
     */
    @Override
    public void move() {
        if (this.getMyTarget() != null) {
            if (FarmSimulator.getFarmProducts().contains(this.getMyTarget())) {
                if ((super.getXCoordinate() != this.getMyTarget().getXCoordinate()) || (super.getYCoordinate() != this.getMyTarget().getYCoordinate())) {
                    super.moveToTarget(1);
                } else {
                    if (this.getMyTarget() instanceof Egg)
                        getMyBasket().add((Egg) this.getMyTarget());
                    FarmSimulator.getFarmProducts().remove(this.getMyTarget());
                    this.setMyTarget(null);
                }
            } else {
                this.setMyTarget(null);
            }
        } else {
            super.roam();
            this.dropFood();
            for (FarmProduct farmProduct : FarmSimulator.getFarmProducts()) {
                if ((farmProduct instanceof AnimalManure) || (farmProduct instanceof Egg)) {
                    super.setMyTarget(farmProduct);
                }
            }
        }

    }

    /**
     * @return the egg basket of this human.
     */
    public static ArrayList<Egg> getMyBasket() {
        return myBasket;
    }
}
