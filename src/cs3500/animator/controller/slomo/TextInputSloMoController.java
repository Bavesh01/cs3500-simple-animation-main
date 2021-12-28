package cs3500.animator.controller.slomo;

import cs3500.animator.controller.ISimpleAnimationController;
import cs3500.animator.controller.ViewListener;
import cs3500.animator.view.visual.InteractiveAnimationNotifier;
import java.io.BufferedReader;
import java.io.StringReader;

/**
 * For testing: allows us to test the slomo file by bypassing a file and using a string.
 */
public class TextInputSloMoController extends SloMoController implements
    ISimpleAnimationController, ISloMoController, ViewListener {

  /**
   * Creates a model based off of the read location, then initializes the view for animation.
   *
   * @param view      The view to display to.
   * @param frameRate The framerate at which the view should run.
   * @param rd        Where text should be input from.
   */
  public TextInputSloMoController(InteractiveAnimationNotifier view,
      int frameRate,
      Readable rd) {
    super(view, frameRate, rd);
  }

  /**
   * Gets the current frame rate set in the controller.
   * @return The current frame rate.
   */
  public int getFramerate() {
    return this.frameRate;
  }

  /**
   * Advances the model forward one frame in the controller, also doing whatever is required of
   * the controller within this function.
   */
  public void advanceModel() {
    super.advanceModel();
  }

  @Override
  protected BufferedReader getReader(String input) {
    return new BufferedReader(new StringReader(input));
  }
}
