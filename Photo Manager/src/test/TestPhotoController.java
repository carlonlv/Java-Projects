package test;

import controller.PhotoController;

public class TestPhotoController extends PhotoController {

  private static TestPhotoController testPhotoController = new TestPhotoController();

  private TestPhotoController() {}

  static TestPhotoController getTestPhotoController() {
    return testPhotoController;
  }

  @Override
  public void updateView() {}
}
