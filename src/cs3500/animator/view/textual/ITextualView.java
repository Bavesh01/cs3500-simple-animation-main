package cs3500.animator.view.textual;

import cs3500.animator.view.IView;

/**
 * An overarching interface for implementing views.
 */
public interface ITextualView extends IView {

  /**
   * returns the view as a String.
   * @return the state of the model as a string.
   */
  String toString();
}
