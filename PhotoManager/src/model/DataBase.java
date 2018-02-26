package model;

import java.util.ArrayList;

/**
 * This DataBase class is use to store all the Directory, Photo and Tag data from later
 * modification.
 */
public class DataBase {

  /** Create a local data base from the shared data base by this program.*/
  private static DataBase dataBase = new DataBase();
  /** Create a list of monitored directories*/
  private ArrayList<Directory> monitoredDirectory = new ArrayList<>();
  /** Create a list of global photos*/
  private ArrayList<Photo> globalPhotos = new ArrayList<>();
  /** Create a list of global tags*/
  private ArrayList<Tag> globalTag = new ArrayList<>();

  public DataBase() {}

  public static DataBase getDataBase() {
    return dataBase;
  }

  /** @return all the Directories from monitoredDirectory */
  public ArrayList<Directory> getMonitoredDirectory() {
    return monitoredDirectory;
  }

  /** @return Photos from the globalPhotos database */
  public ArrayList<Photo> getGlobalPhotos() {
    return globalPhotos;
  }

  /** @return Tags from the globalTag database */
  public ArrayList<Tag> getGlobalTag() {
    return globalTag;
  }

  /** @return Directory from the globalDirectory database */
  public Directory getDirectory(String filePath) {
    Directory directory = null;
    for (Directory i : getMonitoredDirectory()) {
      if (i.getFilePath().equals(filePath)) {
        directory = i;
      }
    }
    return directory;
  }

  /**
   * To determine if the filepath contains certain directory.
   *
   * @param filePath String filePath of the Directory
   * @return true if a directory with the filePath exists in record else false
   */
  public boolean containsDirectory(String filePath) {
    if (getMonitoredDirectory().isEmpty()) {
      return false;
    } else {
      boolean search = false;
      for (Directory i : getMonitoredDirectory()) {
        if (i.getFilePath().equals(filePath)) {
          search = true;
        }
      }
      return search;
    }
  }

  /**
   * To determine if the GlobalPhoto contains certain Photo.
   *
   * @param filePath String filePath of the Photo
   * @return true if a photo with the filePath exists in record else false
   */
  public boolean containsPhoto(String filePath) {
    if (getGlobalPhotos().isEmpty()) {
      return false;
    } else {
      boolean search = false;
      for (Photo i : getGlobalPhotos()) {
        if (i.getFilePath().equals(filePath)) {
          search = true;
        }
      }
      return search;
    }
  }

  /**
   * To search certain Photo which saved in certain directory.
   *
   * @param filePath String filePath of the photo
   * @return the Photo to be searched otherwise null
   */
  public Photo getPhoto(String filePath) {
    Photo photo = null;
    for (Photo i : getGlobalPhotos()) {
      if (i.getFilePath().equals(filePath)) {
        photo = i;
      }
    }
    return photo;
  }

  /**
   * To search certain Tag which is saved in the dataBase.
   *
   * @param name String name of the tag
   * @return tag to be searched otherwise null
   */
  public Tag getTag(String name) {
    Tag tag = null;
    for (Tag i : getGlobalTag()) {
      if (i.getName().equals(name)) tag = i;
    }
    return tag;
  }

  /**
   * To determine if the GlobalTags contains certain tag.
   *
   * @param name String the name of the Tag
   * @return true if a tag with the name exists in record else false
   */
  public boolean containsTag(String name) {
    if (getGlobalTag().isEmpty()) {
      return false;
    } else {
      boolean search = false;
      for (Tag i : getGlobalTag()) {
        if (i.getName().equals(name)) {
          search = true;
        }
      }
      return search;
    }
  }

  /** Clear all Photos, Tags and Directories. */
  public void clearAllData() {
    getGlobalPhotos().clear();
    getMonitoredDirectory().clear();
    getGlobalTag().clear();
  }
}
