package test;

import model.DataBase;
import model.Directory;
import model.Photo;
import model.Tag;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TagTest {

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
   * test if a Tag can properly gets its name.
   */
  @Test
  void testGetName() {
    dataBase.clearAllData();
    Tag tag1 = new Tag("Tag1");
    assertEquals(tag1.getName(), "Tag1");
  }

  /**
   * test if a Tag can properly convert to a string.
   */
  @Test
  void testToString() {
    dataBase.clearAllData();
    Tag tag1 = new Tag("Tag1");
    assertEquals(tag1.toString(), " @Tag1");
  }

  /**
   * test if equals method can properly compare two tags.
   */
  @Test
  void testEquals() {
    dataBase.clearAllData();
    Tag tag1 = new Tag("Tag1");
    Tag tag2 = new Tag("Tag2");
    Tag tag3 = new Tag("Tag1");
    assertTrue(tag1.equals(tag3));
    assertFalse(tag1.equals(tag2));
  }

  /**
   * test if a Tag can properly be deleted from to global tags.
   */
  @Test
  void testDeleteFromGlobalTag() {
    dataBase.clearAllData();
    Tag tag1 = new Tag("Tag1");
    assertTrue(dataBase.getGlobalTag().contains(tag1));
    tag1.deleteFromGlobalTag(tag1);
    assertFalse(dataBase.getGlobalTag().contains(tag1));
  }

  /**
   * test if a Tag can properly add a photo..
   */
  @Test
  void testAddPhoto() {
    dataBase.clearAllData();
    Tag tag1 = new Tag("Tag1");
    Photo photo1 = new Photo("Photo1");
    tag1.addPhoto(photo1);
    assertTrue(tag1.getPhotos().contains(photo1));
  }

  /**
   * test if a Tag can properly delete a photo..
   */
  @Test
  void testDeletePhoto() {
    dataBase.clearAllData();
    Tag tag1 = new Tag("Tag1");
    Photo photo1 = new Photo("Photo1");
    Photo photo2 = new Photo("Photo2");
    tag1.addPhoto(photo1);
    tag1.addPhoto(photo2);
    tag1.deletePhoto(photo1);
    assertTrue(tag1.getPhotos().contains(photo2));
    assertFalse(tag1.getPhotos().contains(photo1));
  }
}
