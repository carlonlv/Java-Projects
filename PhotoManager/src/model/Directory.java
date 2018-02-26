package model;

import controller.DirectoryController;
import controller.Observer;

import java.io.Serializable;
import java.util.ArrayList;

/** This directory class is use to receive and modify Directory. */
public class Directory extends Observable implements Serializable {

  /** Create a local data base from the shared data base by this program.*/
  private static DataBase dataBase = DataBase.getDataBase();
  /** Create a local directory controller */
  private static Observer directoryController = DirectoryController.getDirectoryController();
  /** Create a list of photos*/
  private ArrayList<Photo> photos;
  /** Create a list of subdirectories */
  private ArrayList<Directory> subDirectories;
  /** Create a file path*/
  private String filePath;
  /** Create a directory name*/
  private String name;

  /**
   * Construct a new directory.
   * @param filePath the filePath of the Directory
   */
  public Directory(String filePath) {
    this.photos = new ArrayList<>();
    this.filePath = filePath;
    this.subDirectories = new ArrayList<>();
    this.addToMonitoredDirectory(this);
  }

  /**
   * Set the DataBase used to store this directory.
   * @param dataBase DataBase The DataBase to be stored
   */
  public static void setDataBase(DataBase dataBase) {
    Directory.dataBase = dataBase;
  }

  public static void setDirectoryController(Observer directoryController) {
    Directory.directoryController = directoryController;
  }

  /**
   * Add a new directory into the directory database
   *
   * @param directory Directory to be added
   */
  public void addToMonitoredDirectory(Directory directory) {
    dataBase.getMonitoredDirectory().add(directory);
    Directory.directoryController.updateView();
  }

  /**
   * Delete a directory from the existing directory dataBase
   *
   * @param directory Directory to be deleted
   */
  public void deleteFromMonitoredDirectory(Directory directory) {
    dataBase.getMonitoredDirectory().remove(directory);
    Directory.directoryController.updateView();
  }

  /**
   * Adds a new Photo to photos.
   *
   * @param newPhoto Photo.
   */
  public void addPhoto(Photo newPhoto) {
    this.getPhotos().add(newPhoto);
  }

  /**
   * Add a directory to subDirectory.
   *
   * @param directory the name of new directory.
   */
  public void addSubDirectory(Directory directory) {
    this.getSubDirectories().add(directory);
  }

  /**
   * Deletes badMemories from photos.
   *
   * @param badMemories Photo.
   */
  public void deletePhoto(Photo badMemories) {
    this.getPhotos().remove(badMemories);
  }

  /**
   * Deletes directory from subDirectory.
   *
   * @param directory Directory
   */
  public void deleteSubDirectory(Directory directory) {
    this.getSubDirectories().remove(directory);
  }

  /**
   * @return all photos in this Directory one level down.
   */
  public ArrayList<Photo> getPhotos() {
    return photos;
  }

  /**
   *
   * @return The ArrayList of all the SubDirectories under this Directory
   */
  public ArrayList<Directory> getSubDirectories() {
    return subDirectories;
  }

  /**
   *
   * @return The filePath of this Directory
   */
  public String getFilePath() {
    return filePath;
  }

  /** @return name of the Directory */
  private String getName() {
    return name;
  }

  /**
   * modify the name of the Directory with a new name
   *
   * @param name String
   */
  public void setName(String name) {
    this.name = name;
  }

  /** @return the string that contains Directory's name */
  @Override
  public String toString() {
    return "Directory " + this.getName();
  }

  /**
   * @param object An object to compare to.
   * @return true if the object equals to this directory else false
   */
  public boolean equals(Object object) {
    return object instanceof Directory
        && ((Directory) object).getFilePath().equals(this.getFilePath());
  }

  /**
   * @param observer The controller to notify the changes to.
   */
  @Override
  public void notify(Observer observer) {
    observer.updateView();
  }
}
