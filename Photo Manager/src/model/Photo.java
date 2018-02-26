package model;

import controller.Observer;
import controller.PhotoController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/** This Photo class is use to receive and modify all the photos. */
public class Photo extends Observable implements Serializable {

  /** Create a local photo controller */
  private static Observer photoController = PhotoController.getPhotoController();
  /** Create a local data base from the shared data base by this program.*/
  private static DataBase dataBase = DataBase.getDataBase();
  /** Create photo name*/
  private String name;
  /** Create directory */
  private Directory dir;
  /** Create list of tags*/
  private ArrayList<Tag> tags;
  /** Create file path*/
  private String filePath;
  /** Create record of name history*/
  private HashMap<String, ArrayList<String>> nameHistory;
  /** Create record of tag history*/
  private HashMap<String, ArrayList<ArrayList<String>>> tagHistory;

  /**
   * Create an Photo image under the unique file path that has a name, date, dateTime and tags
   *
   * @param filePath : The string representation of the filepath you want to put this photo under.
   */
  public Photo(String filePath) {
    this.setFilePath(filePath);
    this.tags = new ArrayList<>();
    this.addToGlobalPhoto(this);
    nameHistory = new HashMap<>();
    tagHistory = new HashMap<>();
  }

  /**
   * Set the photoController, only used in unit tests.
   *
   * @param photoController The photoController to be set To.
   */
  public static void setPhotoController(PhotoController photoController) {
    Photo.photoController = photoController;
  }

  /**
   * Set the Data Base, only used in unit tests.
   *
   * @param dataBase The DataBase to be set to.
   */
  public static void setDataBase(DataBase dataBase) {
    Photo.dataBase = dataBase;
  }

  /**
   * Store the photo to the DataBase
   *
   * @param photo Photo to be add
   */
  public void addToGlobalPhoto(Photo photo) {
    dataBase.getGlobalPhotos().add(photo);
    Photo.photoController.updateView();
  }

  /**
   * Delete a photo from the DataBase
   *
   * @param photo Photo to be deleted
   */
  public void deleteFromGlobalPhoto(Photo photo) {
    dataBase.getGlobalPhotos().remove(photo);
    Photo.photoController.updateView();
  }

  /**
   * @return all the tags in this photo
   */
  public ArrayList<Tag> getTags() {
    return this.tags;
  }

  /**
   * Add Tag to this photo.
   *
   * @param tag Tag
   */
  public void addTag(Tag tag) {
    this.tags.add(tag);
  }

  /**
   * Remove this tag from this photo.
   *
   * @param tag Tag
   */
  public void deleteTag(Tag tag) {
    this.tags.remove(tag);
  }

  /**
   *  @return the name of the photo
   */
  public String getName() {
    return this.name;
  }

  /**
   * To indicate this Photo in String.
   *
   * @return Photo: name and tag representation of this photo
   */
  @Override
  public String toString() {
    if (this.getName() != null) {
      StringBuilder name = new StringBuilder();
      name.append(this.getName());
      if (!this.getTags().isEmpty()) {
        for (Tag i : this.getTags()) {
          name.append(i.toString());
        }
      }
      return name.toString();
    } else {
      return "null";
    }
  }

  /**
   * rename the Photo with the newName.
   *
   * @param newName String
   */
  public void rename(String newName) {
    this.name = newName;
  }

  /**
   * @return the directory path of the photo
   */
  public Directory getDirectory() {
    return this.dir;
  }

  /**
   * To Set the directory of this photo
   *
   * @param dir The parent directory of this photo.
   */
  public void setDirectory(Directory dir) {
    this.dir = dir;
  }

  /**
   *  @return the string of all names of the photo
   */
  public HashMap<String, ArrayList<String>> nameLog() {
    return this.nameHistory;
  }

  /**
   * @return the string of all tags of the photo
   */
  public HashMap<String, ArrayList<ArrayList<String>>> tagLog() {
    return this.tagHistory;
  }

  /**
   * @return the filePath of the Photo
   */
  public String getFilePath() {
    return filePath;
  }

  /**
   * Set the filePath of this photo
   *
   * @param filePath the filepath of this photo
   */
  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  /**
   * To detect if two Photos are equal.
   *
   * @param object An object to compare this photo to
   */
  @Override
  public boolean equals(Object object) {
    return object instanceof Photo && ((Photo) object).getFilePath().equals(this.getFilePath());
  }

  /**
   * To notify the observer.
   *
   * @param observer The Observer to notify changes to.
   */
  @Override
  public void notify(Observer observer) {
    observer.updateView();
  }
}
