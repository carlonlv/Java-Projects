package test;

import model.DataBase;
import model.Directory;
import model.Photo;
import model.Tag;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhotoTest {

  private static DataBase dataBase = new DataBase();

  /**
   * Set up before testing program.
   */
  @BeforeAll
  static void setUp() {
    Photo.setPhotoController(TestPhotoController.getTestPhotoController());
    Photo.setDataBase(dataBase);
    Tag.setTagController(TestTagController.getTestTagController());
    Tag.setDataBase(dataBase);
    Directory.setDirectoryController(TestDirectoryController.getTestDirectoryController());
    Directory.setDataBase(dataBase);
  }

  /**
   * test if equals method compared two photos properly.
   */
  @Test
  void testEquals() {
    dataBase.clearAllData();
    Photo photo1 = new Photo("FilePath1");
    Photo photo2 = new Photo("FilePath2");
    Photo photo3 = new Photo("FilePath1");
    assertTrue(photo1.equals(photo3));
    assertFalse(photo1.equals(photo2));
  }

  /**
   * test if photo constructor properly constructs a photo.
   */
  @Test
  void testPhotoConstructor() {
    dataBase.clearAllData();
    Photo photo1 = new Photo("FilePath1");
    assertTrue(dataBase.getGlobalPhotos().contains(photo1));
  }

  /**
   * test if a photo can be properly deleted from global photos.
   */
  @Test
  void testDeleteFromGlobalPhoto() {
    dataBase.clearAllData();
    Photo photo1 = new Photo("FilePath1");
    Photo photo2 = new Photo("FilePath2");
    photo2.deleteFromGlobalPhoto(photo1);
    assertFalse(dataBase.getGlobalPhotos().contains(photo1));
  }

  /**
   * test if a tag can be properly added to a photo.
   */
  @Test
  void testAddTag() {
    dataBase.clearAllData();
    Photo photo1 = new Photo("FilePath1");
    Tag tag1 = new Tag("Tag1");
    Tag tag2 = new Tag("Tag2");
    photo1.addTag(tag1);
    photo1.addTag(tag2);
    assertTrue(photo1.getTags().contains(tag1) && photo1.getTags().contains(tag2));
  }

  /**
   * test if a photo can properly delete a tag.
   */
  @Test
  void testDeleteTag() {
    dataBase.clearAllData();
    Photo photo1 = new Photo("FilePath1");
    Tag tag1 = new Tag("Tag1");
    Tag tag2 = new Tag("Tag2");
    photo1.addTag(tag1);
    photo1.addTag(tag2);
    photo1.deleteTag(tag1);
    photo1.deleteTag(tag2);
    assertFalse(photo1.getTags().contains(tag1) && photo1.getTags().contains(tag2));
  }

  /**
   * test if a photo can properly rename.
   */
  @Test
  void testRename() {
    dataBase.clearAllData();
    Photo photo1 = new Photo("FilePath1");
    photo1.rename("Photo1");
    assertEquals(photo1.getName(), "Photo1");
  }

  /**
   * test if a photo can properly convert to a string.
   */
  @Test
  void testToString() {
    dataBase.clearAllData();
    Photo photo1 = new Photo("FilePath1");
    photo1.rename("Photo1");
    Tag tag1 = new Tag("Tag1");
    Tag tag2 = new Tag("Tag2");
    photo1.addTag(tag1);
    photo1.addTag(tag2);
    assertEquals(photo1.toString(), "Photo1 @Tag1 @Tag2");
  }

  /**
   * test if a photo can properly get its file path.
   */
  @Test
  void testGetFilePath() {
    dataBase.clearAllData();
    Photo photo1 = new Photo("FilePath1");
    assertEquals(photo1.getFilePath(), "FilePath1");
  }

  /**
   * test if a photo can properly set its file path.
   */
  @Test
  void testSetFilePath() {
    dataBase.clearAllData();
    Photo photo1 = new Photo("FilePath1");
    photo1.setFilePath("FilePath2");
    assertEquals(photo1.getFilePath(), "FilePath2");
  }
}
