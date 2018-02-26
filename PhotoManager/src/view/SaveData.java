package view;

import model.DataBase;
import model.Observable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/** to Save Data */
public class SaveData {

  /**
   * To Save Data to an export file.
   *
   * @param args arguments
   */
  public static void main(String[] args) {
    DataBase dataBase = DataBase.getDataBase();
    ArrayList<Observable> saveData = new ArrayList<>();
    saveData.addAll(dataBase.getGlobalPhotos());
    saveData.addAll(dataBase.getGlobalTag());
    saveData.addAll(dataBase.getMonitoredDirectory());

    try {
      FileOutputStream file = new FileOutputStream("save");
      ObjectOutputStream out = new ObjectOutputStream(file);
      out.writeObject(saveData);
      out.close();
      file.close();
      PhotoManager.getTextArea()
          .setText(PhotoManager.getTextArea().getText() + "\n" + "Data Saved.");
    } catch (IOException e) {
      PhotoManager.getTextArea()
          .setText(PhotoManager.getTextArea().getText() + "\n" + "Serialization Failed.");
    }
  }
}
