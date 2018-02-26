package model;

import controller.Observer;
import controller.TagController;

import java.io.Serializable;
import java.util.ArrayList;

/** This class is to modify and receive all the tags. */
public class Tag extends Observable implements Serializable {

  /** Create a local data base from the shared data base by this program.*/
  private static DataBase dataBase = DataBase.getDataBase();
  /** Create a local tag controller */
  private static Observer tagController = TagController.getTagController();
  /** Create tag name */
  private String name;
  /** Create list of photos */
  private ArrayList<Photo> photos;

  /**
   * A new Tag is created by Photo.addTag therefore, by creating a new Tag, the initial photo should
   * add itself to Tag.
   *
   * @param name The name of the Photo.
   */
  public Tag(String name) {
    this.name = name;
    this.photos = new ArrayList<>();
    this.addToGlobalTag(this);
  }

  /**
   * Set the tagController, only used in unit tests.
   *
   * @param tagController The photoController to be set To.
   */
  public static void setTagController(Observer tagController) {
    Tag.tagController = tagController;
  }

  /**
   * Set the Data Base, only used in unit tests.
   *
   * @param dataBase The DataBase to be set to.
   */
  public static void setDataBase(DataBase dataBase) {
    Tag.dataBase = dataBase;
  }

  /**
   * Add a tag to the DataBase.
   *
   * @param tag a tag to be added to DataBase
   */
  public void addToGlobalTag(Tag tag) {
    dataBase.getGlobalTag().add(tag);
    Tag.tagController.updateView();
  }

  /**
   * Delete a tag from the DataBase
   *
   * @param tag a tag to be deleted from DataBase
   */
  public void deleteFromGlobalTag(Tag tag) {
    dataBase.getGlobalTag().remove(tag);
    Tag.tagController.updateView();
  }

  /**
   * Add photo to this tag.
   *
   * @param photo Photo
   */
  public void addPhoto(Photo photo) {
    this.getPhotos().add(photo);
  }

  /**
   * Delete photo from this tag
   *
   * @param photo Photo
   */
  public void deletePhoto(Photo photo) {
    this.getPhotos().remove(photo);
  }

  /**
   * @return the name of this Tag
   */
  public String getName() {
    return name;
  }

  /**
   * Get Photo from this tag
   *
   * @return photos All Photos that contains this tag
   */
  public ArrayList<Photo> getPhotos() {
    return photos;
  }

  /**
   * @return the string representation of this tag
   */
  @Override
  public String toString() {
    return " @" + this.getName();
  }

  /**
   * Check if an object is the same as this tag.
   *
   * @param object object to be compared to.
   * @return  true if the object is the same as this tag, false otherwise
   */
  @Override
  public boolean equals(Object object) {
    return object instanceof Tag && ((Tag) object).getName().equalsIgnoreCase(this.getName());
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
