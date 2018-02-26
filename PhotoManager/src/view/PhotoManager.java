package view;

import controller.DirectoryController;
import controller.PhotoController;
import controller.TagController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Directory;
import model.Photo;
import model.Tag;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/** Graphical User Interface */
public class PhotoManager extends Application implements EventHandler<MouseEvent> {

  /** Text Area */
  private static TextArea textArea = new TextArea();

  /** A list view of photos*/
  private static ListView<Photo> listViewOfPhoto = new ListView<>();

  /** A list view of tags*/
  private static ListView<Tag> listViewOfTags = new ListView<>();

  /** A list view of directories*/
  private static ListView<Directory> listViewOfDirectory = new ListView<>();

  /** Image view of the photo*/
  private static ImageView imageView = new ImageView();

  /** List of photos*/
  private static ObservableList<Photo> photos = FXCollections.observableArrayList();

  /** List of tags*/
  private static ObservableList<Tag> tags = FXCollections.observableArrayList();

  /** List of directories*/
  private static ObservableList<Directory> directories = FXCollections.observableArrayList();

  /** Photo Controller*/
  private static PhotoController photoController = PhotoController.getPhotoController();

  /** Tag Controller*/
  private static TagController tagController = TagController.getTagController();

  /** Directory Controller*/
  private static DirectoryController directoryController =
      DirectoryController.getDirectoryController();

  /** Open directory button*/
  private static Button openDirectory;

  /** Rotate button*/
  private static Button rotate;

  /** Import photo button*/
  private static Button importPhoto;

  /** Import directory button*/
  private static Button importDirectory;

  /** Global tag button*/
  private static Button globalTag;

  /** Global photo button*/
  private static Button globalPhoto;

  /** Global directory button*/
  private static Button globalDirectory;

  /** Rename button*/
  private static Button renamePhoto;

  /** Add tag button*/
  private static Button addTag;

  /** Name log button*/
  private static Button nameLog;

  /** Tag log button*/
  private static Button tagLog;

  /** Delete button*/
  private static Button delete;

  /** Move to button*/
  private static Button moveTo;

  /** Search photo by name button*/
  private static Button searchPhotoByName;

  /** Search photo by tag button*/
  private static Button searchPhotoByTag;

  /** Delete tag button*/
  private static Button deleteTag;

  /** Clear text button*/
  private static Button clearText;

  /** Labels the file path of image*/
  private static Label label;

  /**
   * Main method of this class.
   *
   * @param args arguments.
   */
  public static void main(String[] args) {

    // Load data upon start.
    LoadData.main(null);
    launch(args);
  }

  /** @return The TextArea of this program. */
  public static TextArea getTextArea() {
    return textArea;
  }

  /** @return The Photo ListView of this program. */
  public static ListView<Photo> getListViewOfPhoto() {
    return listViewOfPhoto;
  }

  /** @return The Tag ListView of this program. */
  public static ListView<Tag> getListViewOfTags() {
    return listViewOfTags;
  }

  /** @return The Directory ListView of this program. */
  public static ListView<Directory> getListViewOfDirectory() {
    return listViewOfDirectory;
  }

  /** @return The ImageView of this program. */
  public static ImageView getImageView() {
    return imageView;
  }

  /** @return The photo list to be displayed in Photo ListView. */
  public static ObservableList<Photo> getPhotos() {
    return photos;
  }

  /** @return The tag list to be displayed in Tag ListView. */
  public static ObservableList<Tag> getTags() {
    return tags;
  }

  /** @return The directory list to be displayed in Directory ListView. */
  public static ObservableList<Directory> getDirectories() {
    return directories;
  }

  /** @return The Photo controller of this program. */
  private static PhotoController getPhotoController() {
    return photoController;
  }

  /** @return The Tag controller of this program. */
  private static TagController getTagController() {
    return tagController;
  }

  /** @return The Directory controller of this program. */
  private static DirectoryController getDirectoryController() {
    return directoryController;
  }

  /** @return The importPhoto button of this program. */
  public static Button getImportPhoto() {
    return importPhoto;
  }

  /** @return The importDirectory button of this program. */
  public static Button getImportDirectory() {
    return importDirectory;
  }

  /** @return The GlobalTag button of this program. */
  public static Button getGlobalTag() {
    return globalTag;
  }

  /** @return The GlobalPhoto button of this program. */
  public static Button getGlobalPhoto() {
    return globalPhoto;
  }

  /** @return The GlobalDirectory button of this program. */
  public static Button getGlobalDirectory() {
    return globalDirectory;
  }

    /** @return Opens the Directory selected*/
  public static Button getOpenDirectory(){return openDirectory;}

  /** @return The Rotate button of this program. */
  public static Button getRotate() {
    return rotate;
  }

  /** @return The RenamePhoto button of this program. */
  public static Button getRenamePhoto() {
    return renamePhoto;
  }

  /** @return The AddTag button of this program. */
  private static Button getAddTag() {
    return addTag;
  }

  /** @return The NameLog button of this program. */
  public static Button getNameLog() {
    return nameLog;
  }

  /** @return The TagLog button of this program. */
  public static Button getTagLog() {
    return tagLog;
  }

  /** @return The delete button of this program. */
  private static Button getDelete() {
    return delete;
  }

  /** @return The MoveTo button of this program. */
  public static Button getMoveTo() {
    return moveTo;
  }

  /** @return The SearchPhotoByName button of this program. */
  public static Button getSearchPhotoByName() {
    return searchPhotoByName;
  }

  /** @return The SearchPhotoByTag button of this program. */
  public static Button getSearchPhotoByTag() {
    return searchPhotoByTag;
  }

  /** @return The DeleteTag button of this program. */
  public static Button getDeleteTag() {
    return deleteTag;
  }

  /** @return The ClearText button of this program. */
  private static Button getClearText() {
    return clearText;
  }

  /** @return The label displaying the absolute path of this program. */
  private static Label getLabel() {
    return label;
  }

  /**
   * The Graphical Implementation of this program.
   *
   * @param primaryStage The stage to display GUI on.
   */
  @Override
  public void start(Stage primaryStage) {

    // Creating a Group object
    Group root = new Group();

    // Retrieving the observable list object
    ObservableList<Node> list = root.getChildren();

    // Creating a Scene by passing the group object, height and width
    Scene scene = new Scene(root, 1024, 720);
    scene.setFill(Color.ANTIQUEWHITE);

    // Setting the title to Stage.
    primaryStage.setTitle("PhotoManager");

    // Adding the scene to Stage and Display
    primaryStage.setScene(scene);
    primaryStage.show();

    // Creating and Configuring hBoxMain
    HBox hBoxMain = new HBox();
    hBoxMain.setAlignment(Pos.CENTER);
    hBoxMain.setPrefSize(1024, 720);

    // Creating VBoxLeft under hBoxMain
    VBox vBoxLeft = new VBox();
    vBoxLeft.setAlignment(Pos.CENTER);
    vBoxLeft.setPrefSize(256, 720);
    vBoxLeft.setPadding(new Insets(10));

    // Configuring listViewOfTag under vBoxLeft
    getListViewOfTags().setPrefHeight(100);
    getListViewOfTags().setOrientation(Orientation.HORIZONTAL);
    getListViewOfTags().getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    getListViewOfTags().setOnMouseClicked(this);

    // Creating hBox1 under vBoxLeft
    HBox hBox1 = new HBox(20);
    hBox1.setPrefHeight(80);
    hBox1.setPadding(new Insets(10));

    // Creating globalPhoto under hBox1
    globalPhoto = new Button();
    getGlobalPhoto().setText("GPhoto");
    getGlobalPhoto().setFont(Font.font(10));
    getGlobalPhoto().setOnMouseClicked(photoController);
    getGlobalPhoto().setStyle("-fx-background-color: grey; -fx-text-fill: white;");

    // Creating globalTag under hBox1
    globalTag = new Button();
    getGlobalTag().setText("GTag");
    getGlobalTag().setFont(Font.font(10));
    getGlobalTag().setOnMouseClicked(tagController);
    getGlobalTag().setStyle("-fx-background-color: grey; -fx-text-fill: white;");

    // Creating globalDirectory under hBox1
    globalDirectory = new Button();
    getGlobalDirectory().setText("GDirectory");
    getGlobalDirectory().setFont(Font.font(10));
    getGlobalDirectory().setOnMouseClicked(directoryController);
    getGlobalDirectory().setStyle("-fx-background-color: grey; -fx-text-fill: white;");

    hBox1.getChildren().addAll(getGlobalPhoto(), getGlobalTag(), getGlobalDirectory());

    // Configuring listViewOfPhoto under vBoxLeft
    getListViewOfPhoto().setPrefHeight(300);
    getListViewOfPhoto()
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              getPhotoController().view(getListViewOfPhoto().getSelectionModel().getSelectedItem());
              if (getListViewOfPhoto().getSelectionModel().getSelectedItem() != null)
                getLabel()
                    .setText(
                        getPhotoController()
                            .getFilePath(
                                getListViewOfPhoto().getSelectionModel().getSelectedItem()));
            });
    getListViewOfPhoto().setOnMouseClicked(this);
    getListViewOfPhoto()
        .setOnDragOver(
            event -> {
              if (event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
              }
            });
    getListViewOfPhoto()
        .setOnDragDropped(
            event -> {
              List<File> files = event.getDragboard().getFiles();
              if (files.size() != 0) {
                for (File f : files) {
                  photoController.importPhotoFromFilepath(f.getPath());
                }
              }
              SaveData.main(null);
            });

    // Creating hBox2 under vBoxLeft
    HBox hBox2 = new HBox(50);
    hBox2.setPrefHeight(80);
    hBox2.setPadding(new Insets(10, 5, 10, 5));

    // Creating importPhoto under hBox2
    importPhoto = new Button();
    getImportPhoto().setText("ImpPhoto");
    getImportPhoto().setOnMouseClicked(photoController);
    getImportPhoto().setFont(Font.font(10));
    getImportPhoto().setStyle("-fx-background-color: grey; -fx-text-fill: white;");

    // Creating importDirectory under hBox2
    importDirectory = new Button();
    getImportDirectory().setFont(Font.font(10));
    getImportDirectory().setText("ImpDirectory");
    getImportDirectory().setOnMouseClicked(directoryController);
    getImportDirectory().setStyle("-fx-background-color: grey; -fx-text-fill: white;");

    // Configuring listViewOfDirectory under vBoxLeft
    getListViewOfDirectory().setPrefHeight(300);
    getListViewOfDirectory()
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (observable, oldValue, newValue) ->
                getDirectoryController()
                    .viewAllPhotos(getListViewOfDirectory().getSelectionModel().getSelectedItem()));
    getListViewOfDirectory().setOnMouseClicked(this);
    getListViewOfDirectory()
        .setOnDragOver(
            event -> {
              if (event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
              }
            });
    getListViewOfDirectory()
        .setOnDragDropped(
            event -> {
              List<File> files = event.getDragboard().getFiles();
              if (files.size() != 0) {
                for (File f : files) {
                  directoryController.importDirectory(f.getPath());
                }
              }
              SaveData.main(null);
            });

    hBox2.getChildren().addAll(getImportPhoto(), getImportDirectory());

    vBoxLeft
        .getChildren()
        .addAll(getListViewOfTags(), hBox1, getListViewOfPhoto(), hBox2, getListViewOfDirectory());

    // Creating line under hBoxMain
    Line line = new Line();
    line.setStartX(256);
    line.setStartY(0);
    line.setEndX(256);
    line.setEndY(720);

    // Creating vBoxRight under hBoxMain
    VBox vBoxRight = new VBox(10);
    vBoxRight.setAlignment(Pos.CENTER);
    vBoxRight.setPrefSize(768, 720);
    vBoxRight.setPadding(new Insets(10));

    // Configuring imageView under vBoxRight
    getImageView().setFitHeight(480);
    getImageView().setFitWidth(640);
    getImageView().setPickOnBounds(true);

    // Creating a label under gridPane
    label = new Label();

    // Configuring textArea under vBoxRight
    getTextArea().setPrefHeight(100);

    // Creating gridPane under vBoxRight
    GridPane gridPane = new GridPane();

    gridPane.setAlignment(Pos.CENTER);
    gridPane.setVgap(5);
    gridPane.getColumnConstraints().add(new ColumnConstraints(150));
    gridPane.getColumnConstraints().add(new ColumnConstraints(150));
    gridPane.getColumnConstraints().add(new ColumnConstraints(150));
    gridPane.getRowConstraints().add(new RowConstraints(30));
    gridPane.getRowConstraints().add(new RowConstraints(30));
    gridPane.getRowConstraints().add(new RowConstraints(30));

    //Creating OpenDirectory under gridPane
    openDirectory = new Button();
    getOpenDirectory().setText("OpenDirectory");
    gridPane.add(getOpenDirectory(),3,2);
    getOpenDirectory().setOnMouseClicked(directoryController);

    // Creating rotate under gridPane
    rotate = new Button();
    getRotate().setText("Rotate");
    gridPane.add(getRotate(), 2, 2);
    getRotate().setOnMouseClicked(photoController);

    // Creating nameLog under gridPane
    nameLog = new Button();
    getNameLog().setText("NameLog");
    gridPane.add(getNameLog(), 1, 2);
    getNameLog().setOnMouseClicked(photoController);

    // Creating addTag under gridPane
    addTag = new Button();
    getAddTag().setText("AddTag");
    gridPane.add(getAddTag(), 1, 0);
    getAddTag().setOnMouseClicked(this);

    // Creating delete under gridPane
    delete = new Button();
    getDelete().setText("Delete");
    gridPane.add(getDelete(), 0, 1);
    getDelete().setTextFill(Color.RED);
    getDelete().setOnMouseClicked(this);

    // Creating tagLog under gridPane
    tagLog = new Button();
    getTagLog().setText("TagLog");
    gridPane.add(getTagLog(), 1, 1);
    getTagLog().setOnMouseClicked(photoController);

    // rename a photo
    renamePhoto = new Button();
    getRenamePhoto().setText("RenamePhoto");
    gridPane.add(getRenamePhoto(), 2, 1);
    getRenamePhoto().setOnMouseClicked(photoController);

    // Creating searchPhotoByName under gridPane
    searchPhotoByName = new Button();
    getSearchPhotoByName().setText("SearchPhotoByName");
    gridPane.add(getSearchPhotoByName(), 3, 1);
    getSearchPhotoByName().setOnMouseClicked(directoryController);

    // Creating searchPhotoByTag under gridPane
    searchPhotoByTag = new Button();
    getSearchPhotoByTag().setText("SearchPhotoByTag");
    gridPane.add(getSearchPhotoByTag(), 3, 0);
    getSearchPhotoByTag().setOnMouseClicked(directoryController);

    // Creating moveTo under gridPane
    moveTo = new Button();
    getMoveTo().setText("MovePhoto");
    gridPane.add(getMoveTo(), 2, 0);
    getMoveTo().setOnMouseClicked(photoController);

    // Creating deleteTag under gridPane
    deleteTag = new Button("RemoveTag");
    gridPane.add(getDeleteTag(), 0, 2);
    getDeleteTag().setTextFill(Color.RED);
    getDeleteTag().setOnMouseClicked(photoController);

    // Creating preservedButton1 under gridPane
    clearText = new Button("ClearText");
    getClearText().setTextFill(Color.RED);
    gridPane.add(getClearText(), 0, 0);
    getClearText().setOnMouseClicked(this);

    vBoxRight.getChildren().addAll(getImageView(), getLabel(), getTextArea(), gridPane);

    hBoxMain.getChildren().addAll(vBoxLeft, line, vBoxRight);

    list.add(hBoxMain);
  }

  /**
   * Invoked when a specific event of the type for which this handler is registered happens.
   *
   * @param event the event which occurred
   */
  @Override
  public void handle(MouseEvent event) {

    if (event.getSource() == getDelete()) {
      if (getListViewOfPhoto().getSelectionModel().getSelectedItem() != null
          && getListViewOfDirectory().getSelectionModel().getSelectedItem() == null
          && getListViewOfTags().getSelectionModel().getSelectedItem() == null) {
        getPhotoController()
            .deletePhoto(getListViewOfPhoto().getSelectionModel().getSelectedItem());
        SaveData.main(null);
      } else if (getListViewOfDirectory().getSelectionModel().getSelectedItem() != null
          && getListViewOfPhoto().getSelectionModel().getSelectedItem() == null
          && getListViewOfTags().getSelectionModel().getSelectedItem() == null) {
        getDirectoryController()
            .removeDirectory(getListViewOfDirectory().getSelectionModel().getSelectedItem());
        SaveData.main(null);
      } else if (getListViewOfTags().getSelectionModel().getSelectedItem() != null
          && getListViewOfPhoto().getSelectionModel().getSelectedItem() == null
          && getListViewOfDirectory().getSelectionModel().getSelectedItem() == null) {
        getTagController().deleteTag(getListViewOfTags().getSelectionModel().getSelectedItem());
        SaveData.main(null);
      } else if (getListViewOfDirectory().getSelectionModel().getSelectedItem() == null
          && getListViewOfTags().getSelectionModel().getSelectedItem() == null
          && getListViewOfPhoto().getSelectionModel().getSelectedItem() == null) {
        getTextArea().setText(getTextArea().getText() + "\n" + "Please Select a Target.");
        SaveData.main(null);
      } else {
        getTextArea().setText(getTextArea().getText() + "\n" + "Please Only Select One Target.");
      }

    } else if (event.getSource() == getClearText()) {
      getTextArea().clear();

    } else if (event.getSource() == PhotoManager.getDelete()) {
      if (PhotoManager.getListViewOfPhoto().getSelectionModel().getSelectedItem() != null) {
        if (PhotoManager.getListViewOfTags().getSelectionModel().getSelectedItems().size() == 0) {
          getPhotoController()
              .deletePhoto(getListViewOfPhoto().getSelectionModel().getSelectedItem());
          SaveData.main(null);
        } else if (getListViewOfDirectory().getSelectionModel().getSelectedItem() != null
            && getListViewOfPhoto().getSelectionModel().getSelectedItem() == null
            && getListViewOfTags().getSelectionModel().getSelectedItem() == null) {
          getDirectoryController()
              .removeDirectory(getListViewOfDirectory().getSelectionModel().getSelectedItem());
          SaveData.main(null);
        } else if (getListViewOfTags().getSelectionModel().getSelectedItem() != null
            && getListViewOfPhoto().getSelectionModel().getSelectedItem() == null
            && getListViewOfDirectory().getSelectionModel().getSelectedItem() == null) {
          getTagController().deleteTag(getListViewOfTags().getSelectionModel().getSelectedItem());
          SaveData.main(null);
        } else if (getListViewOfDirectory().getSelectionModel().getSelectedItem() == null
            && getListViewOfTags().getSelectionModel().getSelectedItem() == null
            && getListViewOfPhoto().getSelectionModel().getSelectedItem() == null) {
          getTextArea().setText(getTextArea().getText() + "\n" + "Please Select a Target.");
          SaveData.main(null);
        } else {
          getTextArea().setText(getTextArea().getText() + "\n" + "Please Only Select One Target.");
        }
      }
    } else if (event.getSource() == getClearText()) {
      getTextArea().clear();

    } else if (event.getSource() == PhotoManager.getAddTag()) {
      if (PhotoManager.getListViewOfPhoto().getSelectionModel().getSelectedItem() != null) {
        if (PhotoManager.getListViewOfTags().getSelectionModel().getSelectedItems().size() == 0) {
          getPhotoController()
              .addTag(
                  PhotoManager.getListViewOfPhoto().getSelectionModel().getSelectedItem(),
                  getTextArea().getText());
        } else {
          ArrayList<Tag> selectedTags = new ArrayList<>();
          selectedTags.addAll(
              PhotoManager.getListViewOfTags().getSelectionModel().getSelectedItems());
          Photo photo = PhotoManager.getListViewOfPhoto().getSelectionModel().getSelectedItem();
          if (selectedTags.size() != 0) {
            for (Tag selectedItem : selectedTags) {
              getPhotoController().addTag(photo, selectedItem.getName());
            }
          }
        }
      } else {
        getTagController().addGlobalTag(getTextArea().getText());
      }
      SaveData.main(null);
    } else if (event.getSource() == PhotoManager.getListViewOfDirectory()
        && event.getClickCount() == 2
        && PhotoManager.getListViewOfDirectory().getSelectionModel().getSelectedItem() != null) {
      getDirectoryController()
          .viewContents(
              PhotoManager.getListViewOfDirectory().getSelectionModel().getSelectedItem());
    } else if (event.getSource() == PhotoManager.getListViewOfPhoto()
        && event.getClickCount() == 2
        && PhotoManager.getListViewOfPhoto().getSelectionModel().getSelectedItems() != null) {
      getPhotoController()
          .checkTags(PhotoManager.getListViewOfPhoto().getSelectionModel().getSelectedItem());
    } else if (event.getSource() == PhotoManager.getListViewOfTags()
        && event.getClickCount() == 2
        && PhotoManager.getListViewOfTags().getSelectionModel().getSelectedItems() != null) {
      getTagController()
          .viewPhotos(PhotoManager.getListViewOfTags().getSelectionModel().getSelectedItem());
    }
  }
}
