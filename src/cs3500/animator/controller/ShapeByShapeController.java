package cs3500.animator.controller;

import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.IView;
import model.ISimpleAnimationModel;
import model.SimpleAnimationModel.Builder;
import model.utils.Pair;

/**
 * A controller for views that progress through the model shape-by-shape (such as a textual view).
 * If the view needs gathers all the information about a given shape before progressing to the
 * next, it should go through this controller.
 */
public class ShapeByShapeController implements ISimpleAnimationController {
  private final ISimpleAnimationModel model;
  private final IView view;

  /**
   * Creates a model based off of the read location, then initializes the view for animation.
   *
   * @param view The view to display to.
   * @param frameRate The framerate at which the view should run.
   * @param rd Where text should be input from.
   */
  public ShapeByShapeController(IView view, int frameRate, Readable rd) {
    this.model = AnimationReader.parseFile(rd, new Builder());
    this.view = view;

    Pair<Integer, Integer> bounds = this.model.getBoundaries();
    this.view.initialize(bounds.getValue0(), bounds.getValue1(), frameRate);
  }

  @Override
  public void startAnimation() {
    view.render(this.model);
  }
}
