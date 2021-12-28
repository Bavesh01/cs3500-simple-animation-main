package cs3500.animator.controller;

import cs3500.animator.view.IView;
import model.ISimpleAnimationModel;

public class ReadModelController extends FrameByFrameController {

  /**
   * TESTING CONTROLLER
   * Creates a model based off of the read location, then initializes the view for animation.
   *
   * @param view      The view to display to.
   * @param frameRate The framerate at which the view should run.
   * @param rd        Where text should be input from.
   */
  public ReadModelController(IView view, int frameRate, Readable rd) {
    super(view, frameRate, rd);
  }

  public ISimpleAnimationModel getModel(){
    return model.getCopy();
  }
}
