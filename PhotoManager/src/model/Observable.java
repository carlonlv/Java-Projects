package model;

import controller.Observer;

public abstract class Observable {

  /**
   * @param observer The Observer to notify changes to.
   */
  public abstract void notify(Observer observer);
}
