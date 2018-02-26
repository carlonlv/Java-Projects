package controller;

import model.DataBase;
import model.Photo;
import model.Tag;
import view.PhotoManager;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/** To actualize the modification in Tag */
public class TagController extends Observer implements EventHandler<MouseEvent> {

  /** Create a local data base from the shared data base by this program.*/
  private static DataBase dataBase = DataBase.getDataBase();
  /** Create a local photo controller from the shared photo controller by this program.*/
  private static PhotoController photoController = PhotoController.getPhotoController();
  /** Construct the local tag controller and also the shared tag controller.*/
  private static TagController tagController = new TagController();

  protected TagController() {}

  public static TagController getTagController() {
    return tagController;
  }

  /**
   * view photos which contains that tag
   *
   * @param tag Tag
   */
  public void viewPhotos(Tag tag) {
    if (PhotoManager.getTags().contains(tag)) {
      PhotoManager.getPhotos().clear();
      PhotoManager.getPhotos().addAll(tag.getPhotos());
      PhotoManager.getListViewOfPhoto().setItems(PhotoManager.getPhotos());
    }
  }

  /**
   * to delete tag
   *
   * @param tag Tag
   */
  public void deleteTag(Tag tag) {
    if (tag.getPhotos().size() != 0) {
      for (Photo i : tag.getPhotos()) {
        i.deleteTag(tag);
      }
    }
    tag.deleteFromGlobalTag(tag);
    updateView();
    photoController.updateView();
  }

  /**
   * add new tag to globalTag
   *
   * @param tagName String
   */
  public void addGlobalTag(String tagName) {
    if (dataBase.containsTag(tagName)) {
      PhotoManager.getTextArea()
          .setText(PhotoManager.getTextArea().getText() + "\n" + "Tag Already Exists.");
    } else {
      new Tag(tagName);
      PhotoManager.getTextArea()
          .setText(PhotoManager.getTextArea().getText() + "\n" + "Tag Added Successfully.");
    }
  }

  @Override
  public void updateView() {
    PhotoManager.getTags().clear();
    PhotoManager.getTags().addAll(dataBase.getGlobalTag());
    PhotoManager.getListViewOfTags().setItems(PhotoManager.getTags());
  }

  /**
   * Invoked when a specific event of the type for which this handler is registered happens.
   *
   * @param event the event which occurred
   */
  @Override
  public void handle(MouseEvent event) {
    if (event.getSource() == PhotoManager.getGlobalTag()) {
      updateView();
    }
  }
}
