package view;

import model.Directory;
import model.Observable;
import model.Photo;
import model.Tag;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * Reload the saved data from the save file.
 */
public class LoadData {

    /**
     * Load Data from save file.
     *
     * @param args arguments
     */
    public static void main(String[] args) {

    ArrayList<Observable> loadData = new ArrayList<>();
        try {
            FileInputStream file = new FileInputStream("save");
            ObjectInputStream input = new ObjectInputStream(file);
            loadData = (ArrayList<Observable>) input.readObject();
            input.close();
            file.close();
            PhotoManager.getTextArea()
                    .setText(PhotoManager.getTextArea().getText() + "\n" + "Data Loaded.");
        } catch (IOException e) {
            PhotoManager.getTextArea()
                    .setText(PhotoManager.getTextArea().getText() + "\n" + "Load Save Failed.");
        } catch (ClassNotFoundException e) {
            PhotoManager.getTextArea()
                    .setText(PhotoManager.getTextArea().getText() + "\n" + "Class Not Found.");
        }

        for (Observable i : loadData) {
            if (i instanceof Photo) {
                ((Photo) i).addToGlobalPhoto((Photo) i);
            }
            if (i instanceof Directory) {
                ((Directory) i).addToMonitoredDirectory((Directory) i);
            }
            if (i instanceof Tag) {
                ((Tag) i).addToGlobalTag((Tag) i);
            }
        }
    }
}
