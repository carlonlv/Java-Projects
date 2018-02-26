package controller;

import model.DataBase;
import model.Directory;
import model.Photo;
import model.Tag;
import view.PhotoManager;
import view.SaveData;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/** To actualize the modification in Directory */
public class DirectoryController extends Observer implements EventHandler<MouseEvent> {

  /** Create a local data base from the shared data base by this program.*/
  private static DataBase dataBase = DataBase.getDataBase();
  /** Construct the local directory controller and also the shared directory controller.*/
  private static DirectoryController directoryController = new DirectoryController();
  /** Create a local photo controller from the shared photo controller by this program.*/
  private static PhotoController photoController = PhotoController.getPhotoController();
  /** Create a local tag controller from the shared tag controller by this program.*/
  private static TagController tagController = TagController.getTagController();

  /**
   * To construct a Directory controller.
   */
  protected DirectoryController() {}

  /**
   * Get the directory controller.
   *
   * @return The directory controller used by this program.
   */
  public static DirectoryController getDirectoryController() {
    return directoryController;
  }

  /**
   * To import a directory from a filePath
   *
   * @param filePath The filePath of this directory
   * @return a new directory if succeed otherwise null
   */
  public Directory importDirectory(String filePath) {
    if (!dataBase.containsDirectory(filePath)) {
      File f = new File(filePath);
      if (f.isDirectory()) {
        Directory newDirectory = new Directory(filePath);
        newDirectory.setName(f.getName());
        /*Import all the image files and directories under this directory.*/
        File[] fileList =
            f.listFiles(
                file ->
                    file.getName().toLowerCase().endsWith(".jpg")
                        || file.getName().toLowerCase().endsWith(".png")
                        || file.isDirectory()
                        || file.getName().toLowerCase().endsWith(".jpeg")
                        || file.getName().toLowerCase().endsWith(".gif"));
        if (fileList != null) {
          for (File i : fileList) {
            if (i.isDirectory()) {
              newDirectory.addSubDirectory(importDirectory(i.getPath()));
            } else {
              Photo photo = dataBase.getPhoto(i.getPath());
              if (photo == null) {
                newDirectory.addPhoto(photoController.importPhotoFromFilepath(i.getPath()));
              } else {
                if (!newDirectory.getPhotos().contains(photo)) {
                  newDirectory.addPhoto(photo);
                } else {
                  PhotoManager.getTextArea()
                          .setText(
                                  PhotoManager.getTextArea().getText() + "\n" + "Photo Already Imported.");
                }
              }
            }
          }
        }
        return newDirectory;
      } else {
        PhotoManager.getTextArea()
            .setText(PhotoManager.getTextArea().getText() + "\n" + "Not a valid directory.");
        return null;
      }
    } else {
      PhotoManager.getTextArea()
          .setText(PhotoManager.getTextArea().getText() + "\n" + "Directory Already Imported.");
      return dataBase.getDirectory(filePath);
    }
  }

  /**
   * Delete a certain photo from the directory.
   *
   * @param directory The directory to delete photo from.
   * @param photo The photo to be deleted.
   */
  void deletePhoto(Directory directory, Photo photo) {
    if (directory.getPhotos().contains(photo)) {
      File image = new File(photo.getFilePath());
      boolean success = image.delete();
      if (success) {
        directory.deletePhoto(photo);
        for (int i = (photo.getTags().size() - 1); i >= 0; i--) {
          photo.getTags().get(i).deletePhoto(photo);
          photo.getTags().remove(i);
        }
        photo.deleteFromGlobalPhoto(photo);
        PhotoManager.getTextArea()
            .setText(PhotoManager.getTextArea().getText() + "\n" + "Photo successfully deleted.");
        //        this.photoController.updateView();
        tagController.updateView();
      } else {
        PhotoManager.getTextArea()
            .setText(PhotoManager.getTextArea().getText() + "\n" + "Failed to delete photo.");
      }
    }
  }

  /**
   * To delete a directory and all the subdirectories and photos under this directory.
   *
   * @param directory Directory to be deleted
   */
  public void removeDirectory(Directory directory) {
    File index = new File(directory.getFilePath());
    if (this.viewAllPhotos(directory).size() != 0) {
      for (Photo i : this.viewAllPhotos(directory)) {
        photoController.deletePhoto(i);
      }
    }
    File[] entries = index.listFiles();
    if (entries != null) {
      for (File s : entries) {
        this.removeDirectory(dataBase.getDirectory(s.getPath()));
      }
    }
    if (index.delete()) {
      if (dataBase.getDirectory(index.getParent()) != null) {
        dataBase.getDirectory(index.getParent()).deleteSubDirectory(directory);
      }
      PhotoManager.getTextArea()
          .setText(PhotoManager.getTextArea().getText() + "\n" + "Directory deleted.");
      directory.deleteFromMonitoredDirectory(directory);
    } else {
      PhotoManager.getTextArea()
          .setText(PhotoManager.getTextArea().getText() + "\n" + "Failed to delete directory.");
    }
  }

  /**
   * To view all the photos under in certain directory
   *
   * @param directory Directory to be viewed
   * @return an ArrayList of photo that is under the directory
   */
  public ArrayList<Photo> viewAllPhotos(Directory directory) {
    ArrayList<Photo> allPhoto = new ArrayList<>();
    if (PhotoManager.getDirectories().contains(directory)) {
      allPhoto.addAll(directory.getPhotos());
      if (!directory.getSubDirectories().isEmpty()) {
        for (Directory i : directory.getSubDirectories()) {
          allPhoto.addAll(this.viewAllPhotos(i));
        }
      }
      PhotoManager.getPhotos().clear();
      PhotoManager.getPhotos().addAll(allPhoto);
      PhotoManager.getListViewOfPhoto().setItems(PhotoManager.getPhotos());
    }
    return allPhoto;
  }

  /**
   * To view directories and photos from one level down the directory
   *
   * @param directory Directory to be viewed
   */
  public void viewContents(Directory directory) {
    PhotoManager.getPhotos().clear();
    PhotoManager.getPhotos().addAll(directory.getPhotos());
    PhotoManager.getListViewOfPhoto().setItems(PhotoManager.getPhotos());
    PhotoManager.getDirectories().clear();
    PhotoManager.getDirectories().addAll(directory.getSubDirectories());
    PhotoManager.getListViewOfDirectory().setItems(PhotoManager.getDirectories());
  }

  /**
   * To search the photo with a given name under a directory
   *
   * @param directory Directory to be searched under.
   * @param name The name of the photo
   */
  private void searchPhotoByNames(Directory directory, String name) {
    ArrayList<Photo> photosByName = new ArrayList<>();
    for (Photo i : this.viewAllPhotos(directory)) {
      if (i.getName().equals(name)) {
        photosByName.add(i);
      }
    }
    PhotoManager.getPhotos().clear();
    PhotoManager.getPhotos().addAll(photosByName);
    PhotoManager.getListViewOfPhoto().setItems(PhotoManager.getPhotos());
  }

  /**
   * To filter the photos that contains a given tag name under a directory
   *
   * @param directory Directory to be searched under
   * @param tagName the name of the tag
   */
  private void searchPhotoByTags(Directory directory, String tagName) {
    ArrayList<Photo> photosByTag = new ArrayList<>();
    for (Photo i : this.viewAllPhotos(directory)) {
      for (Tag x : i.getTags()) {
        if (x.getName().equals(tagName)) {
          photosByTag.add(i);
        }
      }
    }
    PhotoManager.getPhotos().clear();
    PhotoManager.getPhotos().addAll(photosByTag);
    PhotoManager.getListViewOfPhoto().setItems(PhotoManager.getPhotos());
  }

  /**
   * Update the list view in PhotoManager.java
   */
  @Override
  public void updateView() {
    PhotoManager.getDirectories().clear();
    PhotoManager.getDirectories().addAll(dataBase.getMonitoredDirectory());
    PhotoManager.getListViewOfDirectory().setItems(PhotoManager.getDirectories());
  }

  /**
   * Invoked when a specific event of the type for which this handler is registered happens.
   *
   * @param event the event which occurred
   */
  @Override
  public void handle(MouseEvent event) {
    if (event.getSource() == PhotoManager.getImportDirectory()) {
      DirectoryChooser directoryChooser = new DirectoryChooser();
      directoryChooser.setTitle("view Directory");
      File file = directoryChooser.showDialog(new Stage());
      if (file != null && file.isDirectory()) {
        importDirectory(file.getPath());
        PhotoManager.getTextArea()
            .setText(
                PhotoManager.getTextArea().getText() + "\n" + "Imported Directory Successfully.");
        SaveData.main(null);
      } else {
        PhotoManager.getTextArea()
            .setText(PhotoManager.getTextArea().getText() + "\n" + "Import Directory Cancelled.");
      }

    } else if (event.getSource() == PhotoManager.getGlobalDirectory()) {
      updateView();

    }else if(event.getSource() == PhotoManager.getOpenDirectory()){
      if(PhotoManager.getListViewOfDirectory().getSelectionModel().getSelectedItem() != null) {
        String filePath = PhotoManager.getListViewOfDirectory().getSelectionModel().getSelectedItem().getFilePath();
        String os = System.getProperty("os.name").toLowerCase();
        try {
          if (os.contains("nux") ||
                  System.getProperty("os.name").toLowerCase().contains("nix")) {
            Runtime.getRuntime().exec("nautilus " + filePath);
          } else{
            Desktop.getDesktop().open(new File(filePath));
          }
        } catch (IOException e) {
          PhotoManager.getTextArea().setText(PhotoManager.getTextArea().getText() + "\n" + "Failed to Open Directory.");
        }
      } else {
        PhotoManager.getTextArea()
                .setText(PhotoManager.getTextArea().getText() + "\n" + "Pleas select a directory.");
      }

    } else if (event.getSource() == PhotoManager.getSearchPhotoByName()) {
      if (PhotoManager.getListViewOfDirectory().getSelectionModel().getSelectedItem() != null) {
        searchPhotoByNames(
            PhotoManager.getListViewOfDirectory().getSelectionModel().getSelectedItem(),
            PhotoManager.getTextArea().getText());
      } else {
        PhotoManager.getTextArea()
            .setText(PhotoManager.getTextArea().getText() + "\n" + "Missing Directory Target.");
      }

    } else if (event.getSource() == PhotoManager.getSearchPhotoByTag()) {
      if (PhotoManager.getListViewOfDirectory().getSelectionModel().getSelectedItem() != null) {
        searchPhotoByTags(
            PhotoManager.getListViewOfDirectory().getSelectionModel().getSelectedItem(),
            PhotoManager.getTextArea().getText());
      } else {
        PhotoManager.getTextArea()
            .setText(PhotoManager.getTextArea().getText() + "\n" + "Missing Directory Target.");
      }
    }
  }
}
