package farm_simulation;

import farmyard.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Random;

/** Our take on the "classical" game Farm Ville */
public class FarmSimulator extends Application {

    /* The width of a character. */
    private static final int charWidth = 6;
    /* The height of a character. */
    private static final int charHeight = 10;
    /* The list that stores all farm creatures in this farm simulator*/
    private static ArrayList<FarmCreature> farmCreatures = new ArrayList<>();
    /* The list that stores all farm products in this farm simulator.*/
    private static ArrayList<FarmProduct> farmProducts = new ArrayList<>();

    /**
     * @param args main function arguments.
     */
    public static void main(String[] args) {
    launch(args);
  }

    /**
     * @return the width of a character.
     */
    public static int getCharWidth() {
        return charWidth;
    }

    /**
     * @return the height of a character.
     */
    public static int getCharHeight() {
        return charHeight;
    }

    /**
     * @return the list that stores all farm creatures.
     */
    public static ArrayList<FarmCreature> getFarmCreatures() {
        return farmCreatures;
    }

    /**
     * @return the list that stores all farm products.
     */
    public static ArrayList<FarmProduct> getFarmProducts() {
        return farmProducts;
    }

    /**
     * Generates chickens and pigs of certain number and one human at random locations.
     *
     * @param numOfChickens the number of chickens you want to put on the farm.
     * @param numOfPigs  the number of pigs you want to put on the farm.
     */
    private static void addAnimals(int numOfChickens, int numOfPigs) {
        Random random = new Random();
        while (numOfChickens != 0){
            getFarmCreatures().add(new Chicken(random.nextInt(1024 / getCharWidth() - 1) + 1,
                    random.nextInt(720 / getCharHeight() - 1) + 1));
            numOfChickens--;
        }
        while (numOfPigs != 0) {
            getFarmCreatures().add(new Pig(random.nextInt(1024 / getCharWidth() - 1) + 1, random.nextInt(720 / getCharHeight() - 1) + 1));
            numOfPigs--;
        }
        getFarmCreatures().add(new Human(random.nextInt(1024 / getCharWidth() - 1) + 1, random.nextInt(720 / getCharHeight() - 1) + 1));
        /*Each farm now generates a mouse*/
        getFarmCreatures().add((new Mouse(random.nextInt(1024 / getCharWidth() - 1) + 1, random.nextInt(720 / getCharHeight() - 1) + 1)));
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("FarmVille");

        Group root = new Group();
        Scene theScene = new Scene(root);
        primaryStage.setScene(theScene);
        Canvas canvas = new Canvas(1024, 720);
        root.getChildren().add(canvas);

        Random random = new Random();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        /*Add at least one chicken and one pig and no more than 8 chickens and 4 pigs*/
        addAnimals(random.nextInt(7) + 1, random.nextInt(3) + 1);
        drawShapes(gc);

        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        //final long timeStart = System.currentTimeMillis();

        KeyFrame kf =
            new KeyFrame(
                Duration.seconds(0.5),
                    ae -> {
                        //double t = (System.currentTimeMillis() - timeStart) / 1000.0;

                        for (FarmCreature farmCreature : getFarmCreatures()) {
                            farmCreature.move();
                        }
                        Wind.windGenerator();
                        for (FarmProduct farmProduct : getFarmProducts()){
                            if (farmProduct instanceof AnimalFood) {
                                ((AnimalFood) farmProduct).blownByWind();
                            }
                        }

                        // Clear the canvas
                        gc.clearRect(0, 0, 1024, 720);
                        drawShapes(gc);
                    }
            );

        gameLoop.getKeyFrames().add(kf);
        gameLoop.play();
        primaryStage.show();
    }

    /**
     * Draw all the creatures and products they produce on the graphics context.
     * Indicate the number of eggs the human picks up and the current wind condition.
     *
     * @param gc the graphics context that we draw our farm on.
     */
    private void drawShapes(GraphicsContext gc) {
        /* Tell all the farmyard creatures to draw themselves.*/
        for (FarmCreature farmCreature : getFarmCreatures()) {
            farmCreature.draw(gc);
        }
        /* Tell all the farmyard products to draw themselves.*/
        for (FarmProduct farmProduct : getFarmProducts()) {
            farmProduct.draw(gc);
        }

        String horizontalWindCondition;
        if (Wind.getBlowingRight() == 1) {
            horizontalWindCondition = "blowing right";
        } else if (Wind.getBlowingRight() == -1 ) {
            horizontalWindCondition = "blowing left";
        } else {
            horizontalWindCondition = "not blowing horizontally";
        }

        String verticalWindCondition;
        if (Wind.getBlowingUp() == 1) {
            verticalWindCondition = "blowing up";
        } else if (Wind.getBlowingUp() == -1 ) {
            verticalWindCondition = "blowing down";
        } else {
            verticalWindCondition = "not blowing vertically";
        }


        gc.fillText("Wind condition: The wind is " + horizontalWindCondition + " and " + verticalWindCondition + ".", 2 * getCharWidth(), 4* getCharHeight());
        gc.fillText("Eggs: " + Human.getMyBasket().size(), 2 * getCharWidth(), 2 * getCharHeight());
    }
}
