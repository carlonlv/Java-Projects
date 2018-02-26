package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.DataBase;
import model.Photo;
import model.Tag;
import view.PhotoManager;
import view.SaveData;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/** To actualize the modification in Photo */
public class PhotoController extends Observer implements EventHandler<MouseEvent> {

  /** Create a local data base from the shared data base by this program.*/
  private static DataBase dataBase = DataBase.getDataBase();
  /** Construct the local photo controller and also the shared photo controller.*/
  private static PhotoController photoController = new PhotoController();
  /** Create a local directory controller from the shared photo controller by this program.*/
  private static DirectoryController directoryController =
      DirectoryController.getDirectoryController();

  /** To construct a photo controller. */
  protected PhotoController() {}

  /**
   * To get the photo controller that is used by this program by default.
   *
   * @return the photo controller
   */
  public static PhotoController getPhotoController() {
    return photoController;
  }

  /**
   * To import a photo from a given filePath
   *
   * @param filePath the filepath of a photo
   * @return the photo to be imported if succeed otherwise null
   */
  public Photo importPhotoFromFilepath(String filePath) {
    if (!dataBase.containsPhoto(filePath)) {
      File image = new File(filePath);
      if (image.getName().toLowerCase().endsWith(".jpg")
          || image.getName().toLowerCase().endsWith(".jpeg")
          || image.getName().toLowerCase().endsWith(".png")
          || image.getName().toLowerCase().endsWith(".gif")) {
        Photo newPhoto = new Photo(filePath);
        String nameWithoutExtension = image.getName().replaceFirst("[.][^.]+$", "");
        ArrayList<String> info = this.detectTagFromName(nameWithoutExtension);
        newPhoto.rename(info.get(0));
        if (info.size() >= 2) {
          for (String i : info.subList(1, info.size())) {
            this.addTag(newPhoto, i);
          }
        }
        this.rename(newPhoto, newPhoto.getName()); // Record the name of this photo.
        if (!dataBase.containsDirectory(image.getParent())) {
          newPhoto.setDirectory(
              directoryController.importDirectory(
                  image.getParent())); // Make the folder of this image a new directory.
        } else {
          newPhoto.setDirectory(dataBase.getDirectory(image.getParent()));
        }
        return newPhoto;
      } else {
        PhotoManager.getTextArea()
            .setText(PhotoManager.getTextArea().getText() + "\n" + "Not A Valid Image File.");
        return null;
      }
    } else {
      PhotoManager.getTextArea()
          .setText(PhotoManager.getTextArea().getText() + "\n" + "Photo Already Imported.");
      return dataBase.getPhoto(filePath);
    }
  }

  /**
   * To view photo in Image view.
   *
   * @param photo Photo to be viewed
   */
  public void view(Photo photo) {
    if (PhotoManager.getPhotos().contains(photo)) {
      File file = new File(photo.getFilePath());
      Image image = new Image(file.toURI().toString(), 640, 480, true, true);
      PhotoManager.getImageView().setImage(image);
    }
  }

  /**
   * To add tag by given tag name to given photo
   *
   * @param photo Photo to be added tag to
   * @param tagName the name of the tag
   */
  public void addTag(Photo photo, String tagName) {
    if (!tagName.contains(File.separator)) {
      if (dataBase.containsTag(tagName)) {
        if (!photo.getTags().contains(dataBase.getTag(tagName))) {
          photo.addTag(dataBase.getTag(tagName));
          rename(photo, photo.getName());
          dataBase.getTag(tagName).addPhoto(photo);
          PhotoManager.getTextArea()
                  .setText(PhotoManager.getTextArea().getText() + "\n" + "Tag Added Successfully.");
          if (!photo.tagLog().containsKey(LocalDate.now().toString())) {
            ArrayList<ArrayList<String>> temp = new ArrayList<>();
            ArrayList<String> tags = new ArrayList<>();
            if (photo.getTags().size() != 0) {
              for (Tag i : photo.getTags()) {
                tags.add(i.getName());
              }
            }
            temp.add(tags);
            photo.tagLog().put(LocalDate.now().toString(), temp);
          } else {
            ArrayList<ArrayList<String>> temp = photo.tagLog().get(LocalDate.now().toString());
            ArrayList<String> tags = new ArrayList<>();
            if (photo.getTags().size() != 0) {
              for (Tag i : photo.getTags()) {
                tags.add(i.getName());
              }
            }
            temp.add(tags);
            photo.tagLog().put(LocalDate.now().toString(), temp);
          }
          this.updateView();
          this.checkTags(photo);
        } else {
          PhotoManager.getTextArea()
                  .setText(PhotoManager.getTextArea().getText() + "\n" + "Tag Has Already Added.");
        }
      } else {
        Tag newTag = new Tag(tagName);
        photo.addTag(newTag);
        rename(photo, photo.getName());
        newTag.addPhoto(photo);
        PhotoManager.getTextArea()
                .setText(PhotoManager.getTextArea().getText() + "\n" + "Tag Added Successfully.");
        if (!photo.tagLog().containsKey(LocalDate.now().toString())) {
          ArrayList<ArrayList<String>> temp = new ArrayList<>();
          ArrayList<String> tags = new ArrayList<>();
          if (photo.getTags().size() != 0) {
            for (Tag i : photo.getTags()) {
              tags.add(i.getName());
            }
          }
          temp.add(tags);
          photo.tagLog().put(LocalDate.now().toString(), temp);
        } else {
          ArrayList<ArrayList<String>> temp = photo.tagLog().get(LocalDate.now().toString());
          ArrayList<String> tags = new ArrayList<>();
          if (photo.getTags().size() != 0) {
            for (Tag i : photo.getTags()) {
              tags.add(i.getName());
            }
          }
          temp.add(tags);
          photo.tagLog().put(LocalDate.now().toString(), temp);
        }
        this.updateView();
        this.checkTags(photo);
      }
    } else {
      PhotoManager.getTextArea()
              .setText(PhotoManager.getTextArea().getText() + "\n" + "Do not include file separator.");
    }
  }

  /**
   * Delete a given tag by its name in given photo
   *
   * @param photo the photo to be deleted tag from
   * @param tagName the name of the tag to be deleted
   */
  private void deleteTag(Photo photo, String tagName) {
    if (photo.getTags().contains(dataBase.getTag(tagName))) {
      photo.deleteTag(dataBase.getTag(tagName));
      dataBase.getTag(tagName).deletePhoto(photo);
      rename(photo, photo.getName());
      this.checkTags(photo);
      this.updateView();
      if (!photo.tagLog().containsKey(LocalDate.now().toString())) {
        ArrayList<ArrayList<String>> temp = new ArrayList<>();
        ArrayList<String> tags = new ArrayList<>();
        if (photo.getTags().size() != 0) {
          for (Tag i : photo.getTags()) {
            tags.add(i.getName());
          }
        }
        temp.add(tags);
        photo.tagLog().put(LocalDate.now().toString(), temp);
      } else {
        ArrayList<ArrayList<String>> temp = photo.tagLog().get(LocalDate.now().toString());
        ArrayList<String> tags = new ArrayList<>();
        if (photo.getTags().size() != 0) {
          for (Tag i : photo.getTags()) {
            tags.add(i.getName());
          }
        }
        temp.add(tags);
        photo.tagLog().put(LocalDate.now().toString(), temp);
      }
    } else {
      PhotoManager.getTextArea()
          .setText(PhotoManager.getTextArea().getText() + "\n" + "Photo Does Not Have This Tag.");
    }
  }

  /**
   * Rename photo by using given name and record it to the name log.
   *
   * @param photo Photo to be renamed.
   * @param newName The new name of the photo
   */
  private void rename(Photo photo, String newName) {
    if(!newName.contains(File.separator)) {
      File image = new File(photo.getFilePath());
      String oldName = photo.toString();
      photo.rename(newName);
      File newImage =
              new File(
                      image.getParent(), photo.toString() + photo.getFilePath().replaceAll(".*\\.", "."));
      boolean renameSuccess = image.renameTo(newImage);
      if (renameSuccess) {
        PhotoManager.getTextArea()
                .setText(PhotoManager.getTextArea().getText() + "\n" + "Photo renamed successfully");
        photo.setFilePath(newImage.getPath());
        if (!photo.nameLog().containsKey(LocalDate.now().toString())) {
          ArrayList<String> temp = new ArrayList<>();
          temp.add(newName);
          photo.nameLog().put(LocalDate.now().toString(), temp);
        } else {
          ArrayList<String> temp = photo.nameLog().get(LocalDate.now().toString());
          if (!(temp.get(temp.size() - 1).equals(newName))) {
            temp.add(newName);
          }
          photo.nameLog().put(LocalDate.now().toString(), temp);
        }
        this.updateView();
      } else {
        photo.rename(oldName);
        PhotoManager.getTextArea()
                .setText(
                        PhotoManager.getTextArea().getText() + "\n" + "Photo Failed To Move and Rename.");
      }
    } else{
      PhotoManager.getTextArea()
              .setText(
                      PhotoManager.getTextArea().getText() + "\n" + "Do not include file separator");

    }
  }

  /**
   * Check the past names (name history) of certain photo
   *
   * @param photo Photo to be checked
   */
  private void checkNameLog(Photo photo) {
    PhotoManager.getTextArea()
        .setText(PhotoManager.getTextArea().getText() + "\n" + photo.nameLog().toString());
  }

  /**
   * Check the tag history of given photo
   *
   * @param photo Photo to be checked
   */
  private void checkTagLog(Photo photo) {
    PhotoManager.getTextArea()
        .setText(PhotoManager.getTextArea().getText() + "\n" + photo.tagLog().toString());
  }

  /**
   * To show all tags in a given photo
   *
   * @param photo Photo to be checked
   */
  public void checkTags(Photo photo) {
    if (PhotoManager.getPhotos().contains(photo)) {
      PhotoManager.getTags().clear();
      PhotoManager.getTags().addAll(photo.getTags());
      PhotoManager.getListViewOfTags().setItems(PhotoManager.getTags());
    }
  }

  /**
   * Show the filepath of given photo
   *
   * @param photo Photo to be checked
   * @return the file path of the photo
   */
  public String getFilePath(Photo photo) {
    return photo.getFilePath();
  }

  /**
   * Move a given photo to a given directory
   *
   * @param photo Photo to be moved
   * @param directoryFilePath the destination
   */
  private void moveTo(Photo photo, String directoryFilePath) {
    File image = new File(photo.getFilePath());
    if (!dataBase.containsDirectory(directoryFilePath)) {
      directoryController.importDirectory(directoryFilePath);
    }
    File newImage = new File(directoryFilePath + "\\" + image.getName());
    if (image.renameTo(newImage)) {
      photo.getDirectory().deletePhoto(photo);
      PhotoManager.getTextArea()
          .setText(PhotoManager.getTextArea().getText() + "\n" + "Photo moved successfully");
      photo.setFilePath(newImage.getPath());
      photo.setDirectory(dataBase.getDirectory(directoryFilePath));
      dataBase.getDirectory(directoryFilePath).addPhoto(photo);
      directoryController.viewContents(dataBase.getDirectory(directoryFilePath));

    } else {
      PhotoManager.getTextArea()
          .setText(PhotoManager.getTextArea().getText() + "\n" + "Photo failed to move.");
    }
  }

  /**
   * To delete photo from its parent directory
   *
   * @param photo Photo to be deleted
   */
  public void deletePhoto(Photo photo) {
    directoryController.deletePhoto(photo.getDirectory(), photo);
  }

  /**
   * To detect the name of a photo and all tags which in that filename
   *
   * @param fileName The name of the file
   * @return The list that contains the actual name of the photo and the tags
   */
  private ArrayList<String> detectTagFromName(String fileName) {
    ArrayList<String> tags = new ArrayList<>();
    String[] tagNames = fileName.split("@");
    tags.addAll(Arrays.asList(tagNames));
    for (int i = 0; i < tags.size() - 1; i++) {
      String string = tags.get(i);
      tags.set(i, string.substring(0, string.length() - 1));
    }
    return tags;
  }

  /** Update the list view in PhotoManager.java */
  @Override
  public void updateView() {
    PhotoManager.getPhotos().clear();
    PhotoManager.getPhotos().addAll(dataBase.getGlobalPhotos());
    PhotoManager.getListViewOfPhoto().setItems(PhotoManager.getPhotos());
  }

  /**
   * Invoked when a specific event of the type for which this handler is registered happens.
   *
   * @param event the event which occurred
   */
  @Override
  public void handle(MouseEvent event) {
    if (event.getSource() == PhotoManager.getImportPhoto()) {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("view Pictures");
      fileChooser
          .getExtensionFilters()
          .addAll(
              new FileChooser.ExtensionFilter("All Images", "*.jpg", "*.png", "*.jpeg", "*.gif"),
              new FileChooser.ExtensionFilter("JPG", "*.jpg"),
              new FileChooser.ExtensionFilter("PNG", "*.png"),
              new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
              new FileChooser.ExtensionFilter("GIF", "*.gif"));
      File file = fileChooser.showOpenDialog(new Stage());
      if (file != null) {
        importPhotoFromFilepath(file.getPath());
        PhotoManager.getTextArea()
            .setText(PhotoManager.getTextArea().getText() + "\n" + "Imported Photo Successfully.");
        SaveData.main(null);
      } else {
        PhotoManager.getTextArea()
            .setText(PhotoManager.getTextArea().getText() + "\n" + "Import Photo Cancelled.");
      }

    } else if (event.getSource() == PhotoManager.getGlobalPhoto()) {
      updateView();
    } else if (event.getSource() == PhotoManager.getRotate()) {
      PhotoManager.getImageView().setRotate(PhotoManager.getImageView().getRotate() + 90);
    } else if (event.getSource() == PhotoManager.getRenamePhoto()) {
      if (PhotoManager.getListViewOfPhoto().getSelectionModel().getSelectedItem() != null) {
        rename(
            PhotoManager.getListViewOfPhoto().getSelectionModel().getSelectedItem(),
            PhotoManager.getTextArea().getText());
        SaveData.main(null);
      } else {
        PhotoManager.getTextArea()
            .setText(PhotoManager.getTextArea().getText() + "\n" + "Missing Photo Target.");
      }

    } else if (event.getSource() == PhotoManager.getNameLog()) {
      if (PhotoManager.getListViewOfPhoto().getSelectionModel().getSelectedItem() != null) {
        if (event.getClickCount() == 2) {
          Group root = new Group();
          Stage OldTags = new Stage();
          OldTags.setTitle("Older versions of tags");
          OldTags.setScene(new Scene(root, 300, 370));
          OldTags.show();

          Photo photo = PhotoManager.getListViewOfPhoto().getSelectionModel().getSelectedItem();
          ObservableList<String> names = FXCollections.observableArrayList();
          if (photo.nameLog().values().size() != 0) {
            for (ArrayList<String> i : photo.nameLog().values()) {
              names.addAll(i);
            }
          }
          Set<String> hs = new HashSet<>(names);
          names.clear();
          names.addAll(hs);

          ListView<String> viewOfPhotos = new ListView<>(names);
          viewOfPhotos.setOrientation(Orientation.VERTICAL);
          viewOfPhotos.setPrefWidth(300);
          viewOfPhotos.setPrefHeight(300);
          viewOfPhotos.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
          HBox hBox = new HBox(100);
          VBox vbox = new VBox(10);
          hBox.setMinHeight(40);
          hBox.setAlignment(Pos.BOTTOM_LEFT);
          hBox.setPadding(new Insets(20, 10, 20, 10));
          Button confirm = new Button();
          confirm.setText("Confirm");
          Button cancel = new Button();
          cancel.setText("Cancel");
          vbox.getChildren().addAll(viewOfPhotos, hBox);
          hBox.getChildren().addAll(confirm, cancel);
          root.getChildren().addAll(vbox);
          cancel.setOnMouseClicked(event12 -> OldTags.close());
          confirm.setOnMouseClicked(
              event1 -> {
                String nameChosen = viewOfPhotos.getSelectionModel().getSelectedItem();
                rename(photo, nameChosen);
                OldTags.close();
                SaveData.main(null);
              });

        } else if (event.getClickCount() == 1) {
          checkNameLog(PhotoManager.getListViewOfPhoto().getSelectionModel().getSelectedItem());
        }
      } else {
        PhotoManager.getTextArea()
            .setText(PhotoManager.getTextArea().getText() + "\n" + "Missing Photo Target.");
      }
    } else if (event.getSource() == PhotoManager.getTagLog()) {
      if (PhotoManager.getListViewOfPhoto().getSelectionModel().getSelectedItem() != null) {
        if (event.getClickCount() == 2) {
          Group root = new Group();
          Stage OldTags = new Stage();
          OldTags.setTitle("Older versions of tags");
          OldTags.setScene(new Scene(root, 300, 370));
          OldTags.show();

          Photo photo = PhotoManager.getListViewOfPhoto().getSelectionModel().getSelectedItem();
          ObservableList<ArrayList<String>> tags = FXCollections.observableArrayList();
          if (photo.tagLog().values().size() != 0) {
            for (ArrayList<ArrayList<String>> i : photo.tagLog().values()) {
              tags.addAll(i);
            }
          }
          Set<ArrayList<String>> hs = new HashSet<>(tags);
          tags.clear();
          tags.addAll(hs);

          ListView<ArrayList<String>> viewOfTags = new ListView<>(tags);
          viewOfTags.setOrientation(Orientation.VERTICAL);
          viewOfTags.setPrefWidth(300);
          viewOfTags.setPrefHeight(300);
          viewOfTags.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
          HBox hBox = new HBox(100);
          VBox vbox = new VBox(10);
          hBox.setMinHeight(40);
          hBox.setAlignment(Pos.BOTTOM_LEFT);
          hBox.setPadding(new Insets(20, 10, 20, 10));
          Button confirm = new Button();
          confirm.setText("Confirm");
          Button cancel = new Button();
          cancel.setText("Cancel");
          vbox.getChildren().addAll(viewOfTags, hBox);
          hBox.getChildren().addAll(confirm, cancel);
          root.getChildren().addAll(vbox);
          cancel.setOnMouseClicked(event12 -> OldTags.close());
          confirm.setOnMouseClicked(
              event1 -> {
                ArrayList<String> tagChosen = viewOfTags.getSelectionModel().getSelectedItem();
                photo.getTags().clear();
                for (String i : tagChosen) {
                  addTag(photo, i);
                }
                OldTags.close();
                SaveData.main(null);
              });
        } else if (event.getClickCount() == 1) {
          checkTagLog(PhotoManager.getListViewOfPhoto().getSelectionModel().getSelectedItem());
        }
      } else {
        PhotoManager.getTextArea()
            .setText(PhotoManager.getTextArea().getText() + "\n" + "Missing Photo Target.");
      }
    } else if (event.getSource() == PhotoManager.getMoveTo()) {
      if (PhotoManager.getListViewOfPhoto().getSelectionModel().getSelectedItem() != null) {
        if (PhotoManager.getListViewOfDirectory().getSelectionModel().getSelectedItem() != null) {
          String filepath =
              PhotoManager.getListViewOfDirectory()
                  .getSelectionModel()
                  .getSelectedItem()
                  .getFilePath();
          moveTo(PhotoManager.getListViewOfPhoto().getSelectionModel().getSelectedItem(), filepath);
          SaveData.main(null);
        } else {
          PhotoManager.getTextArea()
              .setText(
                  PhotoManager.getTextArea().getText()
                      + "\n"
                      + "Please select a directory destination, or import a destined directory first.");
        }
      } else {
        PhotoManager.getTextArea()
            .setText(PhotoManager.getTextArea().getText() + "\n" + "Missing Photo Target.");
      }
    } else if (event.getSource() == PhotoManager.getDeleteTag()) {
      if (PhotoManager.getListViewOfPhoto().getSelectionModel().getSelectedItem() != null) {
        if (PhotoManager.getListViewOfTags().getSelectionModel().getSelectedItems().size() == 0) {
          deleteTag(
              PhotoManager.getListViewOfPhoto().getSelectionModel().getSelectedItem(),
              PhotoManager.getTextArea().getText());
        } else {
          ArrayList<Tag> selectedTags = new ArrayList<>();
          selectedTags.addAll(
              PhotoManager.getListViewOfTags().getSelectionModel().getSelectedItems());
          Photo photo = PhotoManager.getListViewOfPhoto().getSelectionModel().getSelectedItem();
          if (selectedTags.size() != 0) {
            for (Tag selectedItem : selectedTags) {
              deleteTag(photo, selectedItem.getName());
            }
          }
        }
        SaveData.main(null);
      } else {
        PhotoManager.getTextArea()
            .setText(PhotoManager.getTextArea().getText() + "\n" + "Missing Photo Target.");
      }
    }
  }
}
