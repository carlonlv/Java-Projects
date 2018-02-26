package test;

import controller.TagController;

public class TestTagController extends TagController {
  private static TestTagController testTagController = new TestTagController();

  private TestTagController() {}

  static TestTagController getTestTagController() {
    return testTagController;
  }

  @Override
  public void updateView() {}
}
