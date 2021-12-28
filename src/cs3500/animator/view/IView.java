package cs3500.animator.view;

import model.ISimpleAnimationModel;

/**
 * Represents a view used for visualizing a model comprised of various shapes.
 */
public interface IView {

  /**
   * Outputs the view's interpretation of the model in whatever format the view calls for.
   * @param model the model to interpret.
   */
  void render(ISimpleAnimationModel model);

  /**
   * Sets up the view with given width and height.
   * @param width The width of the view.
   * @param height THe height of the view.
   */
  void initialize(int width, int height, int frameRate);

  /**
   * Displays the string representation of a model.
   * @param model The model to display.
   * @return The string representation of a model.
   */
  String toString(ISimpleAnimationModel model);
}
