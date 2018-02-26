package test;

import controller.DirectoryController;

public class TestDirectoryController extends DirectoryController {
  private static TestDirectoryController testDirectoryController = new TestDirectoryController();

  private TestDirectoryController() {}

  static TestDirectoryController getTestDirectoryController() {
    return testDirectoryController;
  }

  @Override
  public void updateView() {}
}
