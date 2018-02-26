package test;

import model.DataBase;
import model.Directory;
import model.Photo;
import model.Tag;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectoryTest {

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
   * test if the string representation of directory is valid.
   */
  @Test
  void testToString() {
    dataBase.clearAllData();
    Directory directory1 = new Directory("FilePath1");
    directory1.setName("Directory1");
    assertEquals(directory1.toString(), "Directory Directory1");
  }

  /**
   * test if the equal method compared two directory properly.
   */
  @Test
  void testEquals() {
    dataBase.clearAllData();
    Directory directory1 = new Directory("FilePath1");
    Directory directory2 = new Directory("FilePath2");
    Directory directory3 = new Directory("FilePath1");
    assertTrue(directory1.equals(directory3));
    assertFalse(directory1.equals(directory2));
  }

  /**
   * test if a directory can be properly deleted from MonitoredDirectory.
   */
  @Test
  void testDeleteFromMonitoredDirectory() {
    dataBase.clearAllData();
    Directory directory1 = new Directory("FilePath1");
    assertTrue(dataBase.getMonitoredDirectory().contains(directory1));
    directory1.deleteFromMonitoredDirectory(directory1);
    assertFalse(dataBase.getMonitoredDirectory().contains(directory1));
  }

  /**
   * test if a photo can be properly added to directory.
   */
  @Test
  void testAddPhoto() {
    dataBase.clearAllData();
    Directory directory1 = new Directory("FilePath1");
    Photo photo1 = new Photo("FilePath3");
    Photo photo2 = new Photo("FilePath4");
    directory1.addPhoto(photo1);
    directory1.addPhoto(photo2);
    assertTrue(directory1.getPhotos().contains(photo1));
    assertTrue(directory1.getPhotos().contains(photo2));
  }

  /**
   * test if a subdirectory can be properly added to a directory.
   */
  @Test
  void testAddSubDirectory() {
    dataBase.clearAllData();
    Directory directory1 = new Directory("FilePath1");
    Directory directory2 = new Directory("FilePath2");
    directory1.addSubDirectory(directory2);
    assertTrue(directory1.getSubDirectories().contains(directory2));
  }

  /**
   * test if a photo can be properly deleted from a directory.
   */
  @Test
  void testDeletePhoto() {
    dataBase.clearAllData();
    Directory directory1 = new Directory("FilePath1");
    Photo photo1 = new Photo("FilePath3");
    Photo photo2 = new Photo("FilePath4");
    directory1.addPhoto(photo1);
    directory1.addPhoto(photo2);
    assertTrue(directory1.getPhotos().contains(photo1));
    assertTrue(directory1.getPhotos().contains(photo2));
    directory1.deletePhoto(photo1);
    assertFalse(directory1.getPhotos().contains(photo1));
    assertTrue(directory1.getPhotos().contains(photo2));
  }

  /**
   * test if a subdirectory can be properly deleted from a directory.
   */
  @Test
  void testDeleteSubDirectory() {
    dataBase.clearAllData();
    Directory directory1 = new Directory("FilePath1");
    Directory directory2 = new Directory("FilePath2");
    Directory directory3 = new Directory("FilePath3");
    directory1.addSubDirectory(directory2);
    directory1.addSubDirectory(directory3);
    directory1.deleteSubDirectory(directory2);
    assertFalse(directory1.getSubDirectories().contains(directory2));
    assertTrue(directory1.getSubDirectories().contains(directory3));
  }
}
